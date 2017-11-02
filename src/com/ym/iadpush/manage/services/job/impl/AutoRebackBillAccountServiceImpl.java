package com.ym.iadpush.manage.services.job.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi2.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEcoCplifeBillBatchqueryRequest;
import com.alipay.api.response.AlipayEcoCplifeBillBatchqueryResponse;
import com.ym.common.utils.DateUtils;
import com.ym.iadpush.manage.entity.BillAccount;
import com.ym.iadpush.manage.entity.Housing;
import com.ym.iadpush.manage.entity.common.CommonParm;
import com.ym.iadpush.manage.services.job.AutoRebackBillAccountService;
import com.ym.iadpush.manage.services.stock.IStockService;

@Service("autoRebackBillAccountServiceImpl")
public class AutoRebackBillAccountServiceImpl implements AutoRebackBillAccountService {

    private @Autowired
    IStockService stockServiceImpl;

    public void run() {

        //System.out.println("date time >>:" + DateUtils.getDatetime());

        try {
            List<Housing> housings = stockServiceImpl.getAllHousings();

            for (Housing housing : housings) {
                if (!StringUtils.isBlank(housing.getCommunity_id())) {
                    //System.out.println("housename:" + housing.getName());
                    autoRebackBillAccountFromAliPay(housing.getId());
                }
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

    }

    /**
     * @throws AlipayApiException
     * @Author lixingbiao 2017-6-7 下午10:08:59
     */
    private void autoRebackBillAccountFromAliPay(int houseingId) throws AlipayApiException {

        Housing housing = stockServiceImpl.getHousingById(houseingId);

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();

        String token = housing.getToken();
        String community_id = housing.getCommunity_id();

        DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", community_id);
        bizContent.put("bill_status", "FINISH_PAYMENT");

        AlipayEcoCplifeBillBatchqueryRequest billBatchUploadRequest = new AlipayEcoCplifeBillBatchqueryRequest();
        billBatchUploadRequest.setBizContent(bizContent.toString());
        billBatchUploadRequest.putOtherTextParam("app_auth_token", token);

        AlipayEcoCplifeBillBatchqueryResponse response = alipayClient.execute(billBatchUploadRequest);

        JSONObject jsonObject = JSONObject.fromObject(response.getBody());

        JSONObject bill_batchquery = (JSONObject) jsonObject.get("alipay_eco_cplife_bill_batchquery_response");

        //System.out.println("code>>:" + response.getCode());

        if (response.getCode().equals("10000")) {
            int total_bill_count = bill_batchquery.getInt("total_bill_count");

            //System.out.println("total_bill_count>>>:" + total_bill_count);

            Object bill_result_set = bill_batchquery.get("bill_result_set");
            if (bill_result_set != null) {
                JSONArray array = JSONArray.fromObject(bill_result_set);
                if (array.size() > 0) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String bill_entry_id = object.get("bill_entry_id").toString();
                        String status = object.get("status").toString();

                        // 更新物业系统账单付款状态
                        if (status.equals("FINISH_PAYMENT")) {
                            BillAccount billAccount = stockServiceImpl.getBillAccountByBill_entry_id(bill_entry_id);
                            if (billAccount != null && billAccount.getPayStatus() != 1) {
                                billAccount.setPayType("支付宝");
                                billAccount.setPayStatus(1);
                                billAccount.setPayDate(DateUtils.getDate());
                                billAccount.setAccpetMoney(billAccount.getBill_entry_amount()+"");
                                stockServiceImpl.updateBillAccount(billAccount, bill_entry_id);
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("last>>>>>>>>>");
    }
}
