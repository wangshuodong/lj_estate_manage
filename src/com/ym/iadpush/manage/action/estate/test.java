/**
 * 
 */
package com.ym.iadpush.manage.action.estate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.CplifeRoomDetail;
import com.alipay.api.request.AlipayEbppInvoiceTitleListGetRequest;
import com.alipay.api.request.AlipayEcoCplifeBasicserviceInitializeRequest;
import com.alipay.api.request.AlipayEcoCplifeBasicserviceModifyRequest;
import com.alipay.api.request.AlipayEcoCplifeBillBatchqueryRequest;
import com.alipay.api.request.AlipayEcoCplifeBillDeleteRequest;
import com.alipay.api.request.AlipayEcoCplifeCommunityDetailsQueryRequest;
import com.alipay.api.request.AlipayEcoCplifeRoominfoDeleteRequest;
import com.alipay.api.request.AlipayEcoCplifeRoominfoQueryRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayEbppInvoiceTitleListGetResponse;
import com.alipay.api.response.AlipayEcoCplifeBasicserviceInitializeResponse;
import com.alipay.api.response.AlipayEcoCplifeBasicserviceModifyResponse;
import com.alipay.api.response.AlipayEcoCplifeBillBatchqueryResponse;
import com.alipay.api.response.AlipayEcoCplifeBillDeleteResponse;
import com.alipay.api.response.AlipayEcoCplifeCommunityDetailsQueryResponse;
import com.alipay.api.response.AlipayEcoCplifeRoominfoDeleteResponse;
import com.alipay.api.response.AlipayEcoCplifeRoominfoQueryResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.ym.iadpush.manage.entity.common.CommonParm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class test {
    /**
     * 删除物业小区房屋信息
     * @Author lixingbiao 2017年10月16日 上午9:57:24
     * @param out_room_id
     */
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

    public JSONObject quyerBillAccount() throws AlipayApiException {
        test test = new test();

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();
        
        System.out.println(app_id);
        System.out.println(private_key);
        System.out.println(alipay_public_key);

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String token = "201709BBdb06c71a5bd0432193e9a992ac3f7X20";
        String community_id = "A2UGDIPJI3301";

        DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", community_id);
        bizContent.put("bill_status", "WAIT_PAYMENT");
        bizContent.put("cost_type", "物业管理费");

        AlipayEcoCplifeBillBatchqueryRequest billBatchUploadRequest = new AlipayEcoCplifeBillBatchqueryRequest();
        billBatchUploadRequest.setBizContent(bizContent.toString());
        billBatchUploadRequest.putOtherTextParam("app_auth_token", token);

        AlipayEcoCplifeBillBatchqueryResponse response = alipayClient.execute(billBatchUploadRequest);
        System.out.println(response.getBody());
        JSONObject obj = JSONObject.fromObject(response.getBody());
        return obj;

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

        String token = "201709BBdb06c71a5bd0432193e9a992ac3f7X20";
        String community_id = "A2UGDIPJI3301";

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
    
    /**
     * 初始化小区物业基础服务
     * @Author lixingbiao 2017年10月16日 上午10:23:47
     * @param token
     * @param community_id
     */
    public void BasicserviceInitialize(String token, String community_id) {

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
        bizContent.put("community_id", community_id);
        bizContent.put("service_type", "PROPERTY_PAY_BILL_MODE");
        bizContent.put("external_invoke_address", "https://www.alipayjf.com/aaalipay_web_return.do");
        bizContent.put("status", "ONLINE");

        AlipayEcoCplifeBasicserviceInitializeRequest initializeRequest = new AlipayEcoCplifeBasicserviceInitializeRequest();
        initializeRequest.putOtherTextParam("app_auth_token", token);
        initializeRequest.setBizContent(bizContent.toString());

        AlipayEcoCplifeBasicserviceInitializeResponse response = null;
        try {
            response = (AlipayEcoCplifeBasicserviceInitializeResponse) alipayClient.execute(initializeRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println(response.getBody());
    }

    public void BasicserviceModify(String token, String community_id) throws AlipayApiException {
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
        bizContent.put("community_id", community_id);
        bizContent.put("service_type", "PROPERTY_PAY_BILL_MODE");
        bizContent.put("external_invoke_address", "https://www.alipayjf.com/alipay_web_return.do");
        bizContent.put("status", "ONLINE");

        AlipayEcoCplifeBasicserviceModifyRequest initializeRequest = new AlipayEcoCplifeBasicserviceModifyRequest();
        initializeRequest.putOtherTextParam("app_auth_token", token);
        initializeRequest.setBizContent(bizContent.toString());

        AlipayEcoCplifeBasicserviceModifyResponse response = null;
        try {
            response = (AlipayEcoCplifeBasicserviceModifyResponse) alipayClient.execute(initializeRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println(response.getBody());
    }
    
    /**
     * 查询单个物业小区信息
     * @Author lixingbiao 2017年10月16日 上午10:15:13
     * @param request
     * @param model
     * @return
     */
    public void communityDetailsQuery(String token, String community_id) {
        //String id = request.getParameter("id");
        //Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(Integer.parseInt(id)));

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
        bizContent.put("community_id", community_id);

        AlipayEcoCplifeCommunityDetailsQueryRequest requestAlipayEcoCplifeCommunityCreateRequest = new AlipayEcoCplifeCommunityDetailsQueryRequest();
        requestAlipayEcoCplifeCommunityCreateRequest.putOtherTextParam("app_auth_token", token);
        requestAlipayEcoCplifeCommunityCreateRequest.setBizContent(bizContent.toString());

        AlipayEcoCplifeCommunityDetailsQueryResponse response = null;
        try {
            response = (AlipayEcoCplifeCommunityDetailsQueryResponse) alipayClient
                    .execute(requestAlipayEcoCplifeCommunityCreateRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println(response.getBody());

    }
    
    /**
     * 获取指定用户所有的有效抬头列表
     * @throws AlipayApiException 
     * @Author 2017年10月30日 下午1:23:53
     */
    public void alipayEbppInvoiceTitleListGetRequest() throws AlipayApiException {
        String access_token = null;
        //AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
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
        AlipayEbppInvoiceTitleListGetRequest request = new AlipayEbppInvoiceTitleListGetRequest();
        request.setBizContent("{" +
        "\"user_id\":\"2088000000000000\"" +
        "  }");//请开发者实际测试时，保证其值和发起授权用户的user_id一致，此处仅示例。
        AlipayEbppInvoiceTitleListGetResponse response = alipayClient.execute(request,access_token);//access_token即用户信息授权时获取后授权码，注意用户信息授权时scope=auth_invoice_info
        if(response.isSuccess()){
        System.out.println("调用成功");
        } else {
        System.out.println("调用失败");
        }

    }

    public static void main(String args[]) throws AlipayApiException {

        test test = new test();
        //test.BasicserviceModify("201708BB85188ec4dcbc4be69df6ef6b5fa2fX42", "ABZ1CMW4Q5001");
        //test.BasicserviceInitialize("201708BB85188ec4dcbc4be69df6ef6b5fa2fX42", "ABZ1CMW4Q5001");
        test.communityDetailsQuery("201709BBdb06c71a5bd0432193e9a992ac3f7X20", "AW09TS6823301");
        
        //test.faceToFacePay("0.01", "287055853787821550" + "");
        // List<CplifeRoomDetail> list = test.quyerRoomInfo();
        // for(CplifeRoomDetail tmp:list){
        // test.deleteRoomInfo(tmp.getOutRoomId());
        //
        // }
        //JSONObject obj = test.quyerBillAccount();
        //obj = obj.getJSONObject("alipay_eco_cplife_bill_batchquery_response");
        //JSONArray array = obj.getJSONArray("bill_result_set");
        // test.deleteBillAccountById("201707050000006");

    }
}
