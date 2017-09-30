/**
 * 
 */
package com.ym.iadpush.manage.action.estate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.CplifeRoomDetail;
import com.alipay.api.request.AlipayEcoCplifeBillBatchqueryRequest;
import com.alipay.api.request.AlipayEcoCplifeBillDeleteRequest;
import com.alipay.api.request.AlipayEcoCplifeRoominfoDeleteRequest;
import com.alipay.api.request.AlipayEcoCplifeRoominfoQueryRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayEcoCplifeBillBatchqueryResponse;
import com.alipay.api.response.AlipayEcoCplifeBillDeleteResponse;
import com.alipay.api.response.AlipayEcoCplifeRoominfoDeleteResponse;
import com.alipay.api.response.AlipayEcoCplifeRoominfoQueryResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.ym.iadpush.manage.entity.common.CommonParm;

public class test {
    public void deleteRoomInfo(String out_room_id) {

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();

        String token = "201706BBccdcbfbf36024ba3a16c8fc6b780bX55";
        bizContent.put("community_id", "AB5QCID0T4113");
        bizContent.put("batch_id", System.currentTimeMillis());

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(out_room_id);

        bizContent.put("out_room_id_set", jsonArray);

        AlipayEcoCplifeRoominfoDeleteRequest deleteRequest = new AlipayEcoCplifeRoominfoDeleteRequest();
        deleteRequest.putOtherTextParam("app_auth_token", token);
        deleteRequest.setBizContent(bizContent.toString());

        try {
            AlipayEcoCplifeRoominfoDeleteResponse response = alipayClient.execute(deleteRequest);

            System.out.println(response.getBody());

        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public List<CplifeRoomDetail> quyerRoomInfo() {

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        JSONObject bizContent = new JSONObject();

        String token = "201706BBccdcbfbf36024ba3a16c8fc6b780bX55";
        bizContent.put("community_id", "AB5QCID0T4113");

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        AlipayEcoCplifeRoominfoQueryRequest request = new AlipayEcoCplifeRoominfoQueryRequest();
        request.setBizContent(bizContent.toString());
        request.putOtherTextParam("app_auth_token", token);

        AlipayEcoCplifeRoominfoQueryResponse response = null;

        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {

            e.printStackTrace();
        }

        System.out.println(response.getRoomInfo());
        System.out.println(response.getTotalRoomNumber());
        if (response.isSuccess()) {
            // System.out.println("调用成功");
        } else {
            // System.out.println("调用失败");
        }
        return response.getRoomInfo();
    }

    public void quyerBillAccount() throws AlipayApiException {
        test test = new test();

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String token = "201706BBb518ec56f22444fa9d0dacf960988D53";
        String community_id = "ALBFPLEUB1304";

        DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", community_id);
        bizContent.put("bill_status", "WAIT_PAYMENT");

        AlipayEcoCplifeBillBatchqueryRequest billBatchUploadRequest = new AlipayEcoCplifeBillBatchqueryRequest();
        billBatchUploadRequest.setBizContent(bizContent.toString());
        billBatchUploadRequest.putOtherTextParam("app_auth_token", token);

        AlipayEcoCplifeBillBatchqueryResponse response = alipayClient.execute(billBatchUploadRequest);

        System.out.println(response.getBody());

    }

    public int deleteBillAccountById(String bill_entry_id) throws AlipayApiException {
        int status = 0;

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();

        String token = "201706BBb518ec56f22444fa9d0dacf960988D53";
        String community_id = "ALBFPLEUB1304";

        DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", community_id);

        JSONArray bill_set = new JSONArray();

        for (int i = 0; i < 1; i++) {

            bill_set.add(bill_entry_id);
        }

        bizContent.put("bill_entry_id_list", bill_set);

        AlipayEcoCplifeBillDeleteRequest billDeleteRequest = new AlipayEcoCplifeBillDeleteRequest();
        billDeleteRequest.setBizContent(bizContent.toString());
        billDeleteRequest.putOtherTextParam("app_auth_token", token);

        AlipayEcoCplifeBillDeleteResponse response = alipayClient.execute(billDeleteRequest);

        System.out.println(response.getBody());

        if (response.getCode().equals("10000")) {
            status = 2;
        }

        System.out.println("status  last >>" + status);

        return status;
    }

    public void faceToFacePay(String money, String authCode) {
        CommonParm commonParm = new CommonParm();
        String app_id = commonParm.getApp_id().trim();
        String private_key = commonParm.getPrivate_key().trim();
        String alipay_public_key = commonParm.getAlipay_public_key().trim();
        String serverUrl = commonParm.getServerUrl().trim();
        String format = commonParm.getFormat().trim();
        String charset = commonParm.getCharset().trim();
        String sign_type = commonParm.getSign_type().trim();
        System.out.println(app_id);
        System.out.println(private_key);
        System.out.println(alipay_public_key);
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);
        AlipayTradePayRequest request = new AlipayTradePayRequest(); // 创建API对应的request类
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        bizContent.put("scene", "bar_code");
        bizContent.put("auth_code", authCode);
        bizContent.put("subject", "测试");
        bizContent.put("store_id", "早早科技");
        bizContent.put("timeout_express", "2m");
        bizContent.put("total_amount", money);
//        bizContent.put("app_auth_token", "201708BB85188ec4dcbc4be69df6ef6b5fa2fX42");
//        bizContent.put("seller_id", "2088721660806424");
        request.setBizContent(bizContent.toString());
        System.out.println(request.getTextParams());
        AlipayTradePayResponse response;
        try {
            response = alipayClient.execute(request,"","201708BB85188ec4dcbc4be69df6ef6b5fa2fX42");
            System.out.print(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws AlipayApiException {

        test test = new test();
        test.faceToFacePay("0.01", "287055853787821550"
                + "");
        // List<CplifeRoomDetail> list = test.quyerRoomInfo();
        // for(CplifeRoomDetail tmp:list){
        // test.deleteRoomInfo(tmp.getOutRoomId());
        //
        // }
        // test.quyerBillAccount();
        // test.deleteBillAccountById("201707050000006");

    }
}
