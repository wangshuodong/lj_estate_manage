package com.ym.iadpush.manage.action.estate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayEcoCplifeBasicserviceInitializeRequest;
import com.alipay.api.request.AlipayEcoCplifeBasicserviceModifyRequest;
import com.alipay.api.request.AlipayEcoCplifeBillBatchUploadRequest;
import com.alipay.api.request.AlipayEcoCplifeBillBatchqueryRequest;
import com.alipay.api.request.AlipayEcoCplifeBillDeleteRequest;
import com.alipay.api.request.AlipayEcoCplifeCommunityCreateRequest;
import com.alipay.api.request.AlipayEcoCplifeCommunityDetailsQueryRequest;
import com.alipay.api.request.AlipayEcoCplifeRoominfoDeleteRequest;
import com.alipay.api.request.AlipayEcoCplifeRoominfoQueryRequest;
import com.alipay.api.request.AlipayEcoCplifeRoominfoUploadRequest;
import com.alipay.api.response.AlipayEcoCplifeBasicserviceInitializeResponse;
import com.alipay.api.response.AlipayEcoCplifeBasicserviceModifyResponse;
import com.alipay.api.response.AlipayEcoCplifeBillBatchUploadResponse;
import com.alipay.api.response.AlipayEcoCplifeBillBatchqueryResponse;
import com.alipay.api.response.AlipayEcoCplifeBillDeleteResponse;
import com.alipay.api.response.AlipayEcoCplifeCommunityCreateResponse;
import com.alipay.api.response.AlipayEcoCplifeCommunityDetailsQueryResponse;
import com.alipay.api.response.AlipayEcoCplifeRoominfoDeleteResponse;
import com.alipay.api.response.AlipayEcoCplifeRoominfoQueryResponse;
import com.alipay.api.response.AlipayEcoCplifeRoominfoUploadResponse;
import com.ym.common.base.BaseAction;
import com.ym.common.utils.DateUtils;
import com.ym.iadpush.common.utils.PrintMessage;
import com.ym.iadpush.manage.entity.BillAccount;
import com.ym.iadpush.manage.entity.Building;
import com.ym.iadpush.manage.entity.Housing;
import com.ym.iadpush.manage.entity.Location;
import com.ym.iadpush.manage.entity.PrintInfo;
import com.ym.iadpush.manage.entity.Proprietor;
import com.ym.iadpush.manage.entity.RoomInfo;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.common.CommonParm;
import com.ym.iadpush.manage.services.print.PrintInfoService;
import com.ym.iadpush.manage.services.stock.IStockService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class EstateAction extends BaseAction {
    
    private static Log log = LogFactory.getLog(EstateAction.class);

    @Autowired
    private IStockService stockServiceImpl;
    @Autowired
    private PrintInfoService printInfoService;

    @RequestMapping(value = "/queryBillAccountTemp.html", method = RequestMethod.GET)
    public Object queryBillAccountTemp(HttpServletRequest request, ModelMap model) {

        String housingId = request.getParameter("housingId");
        String delBill = request.getParameter("delBill");

        Housing housing = stockServiceImpl.getHousingById(new Integer(housingId));

        System.out.println("housing >>:" + housing);
        System.out.println("date time  >>:" + DateUtils.getDatetime());

        if (housing == null) {
            return "";
        }

        System.out.println("name >>:" + housing.getName());

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();

        String token = housing.getToken();

        DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", housing.getCommunity_id());
        bizContent.put("bill_status", "WAIT_PAYMENT");
        bizContent.put("page_num", 1);
        bizContent.put("page_size", 5);

        if (delBill.equals("true")) {
            bizContent.put("page_size", 200);
        }

        System.out.println("bizContent>>>:" + bizContent);

        try {

            AlipayEcoCplifeBillBatchqueryRequest billBatchUploadRequest = new AlipayEcoCplifeBillBatchqueryRequest();
            billBatchUploadRequest.setBizContent(bizContent.toString());
            billBatchUploadRequest.putOtherTextParam("app_auth_token", token);

            AlipayEcoCplifeBillBatchqueryResponse response = alipayClient.execute(billBatchUploadRequest);

            System.out.println(response.getBody());

            if (delBill.equals("true")) {

                JSONObject jsonObject = JSONObject.fromObject(response.getBody());

                JSONObject bill_batchquery = (JSONObject) jsonObject.get("alipay_eco_cplife_bill_batchquery_response");

                if (response.getCode().equals("10000")) {
                    Object bill_result_set = bill_batchquery.get("bill_result_set");
                    if (bill_result_set != null) {
                        JSONArray array = JSONArray.fromObject(bill_result_set);
                        if (array.size() > 0) {
                            for (int i = 0; i < array.size(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String bill_entry_id = object.get("bill_entry_id").toString();
                                System.out.println("bill_entry_id>>:" + bill_entry_id);
                                if (delBill.equals("true")) {
                                    deleteBillAccountByBill_entry_id(bill_entry_id);
                                }
                            }
                        }
                    }
                }
            }

        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }

    @RequestMapping(value = "/queryRoomInfoTemp.html", method = RequestMethod.GET)
    public Object queryRoomInfoTemp(HttpServletRequest request, ModelMap model) {

        String housingId = request.getParameter("housingId");
        String delRoom = request.getParameter("delRoom");

        Housing housing = stockServiceImpl.getHousingById(new Integer(housingId));

        System.out.println("housing >>:" + housing);
        System.out.println("date time  >>:" + DateUtils.getDatetime());

        if (housing == null) {
            return "";
        }

        System.out.println("name >>:" + housing.getName());

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String token = housing.getToken();
        JSONObject bizContent = new JSONObject();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        bizContent.put("community_id", housing.getCommunity_id());
        bizContent.put("page_num", 1);
        bizContent.put("page_size", 5);

        if (delRoom.equals("true")) {
            bizContent.put("page_size", 200);
        }

        System.out.println("bizContent>>>:" + bizContent);

        AlipayEcoCplifeRoominfoQueryRequest queryRequest = new AlipayEcoCplifeRoominfoQueryRequest();
        queryRequest.setBizContent(bizContent.toString());
        queryRequest.putOtherTextParam("app_auth_token", token);

        AlipayEcoCplifeRoominfoQueryResponse response = null;

        try {
            response = alipayClient.execute(queryRequest);

            System.out.println("body>>>:" + response.getBody());

            if (delRoom.equals("true")) {

                JSONObject jsonObject = JSONObject.fromObject(response.getBody());

                JSONObject bill_batchquery = (JSONObject) jsonObject.get("alipay_eco_cplife_roominfo_query_response");

                if (response.getCode().equals("10000")) {
                    Object bill_result_set = bill_batchquery.get("room_info");
                    if (bill_result_set != null) {
                        JSONArray array = JSONArray.fromObject(bill_result_set);
                        if (array.size() > 0) {
                            for (int i = 0; i < array.size(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String out_room_id = object.get("out_room_id").toString();
                                System.out.println("out_room_id>>:" + out_room_id);
                                if (delRoom.equals("true")) {
                                    deleteRoomInfo(out_room_id, housing);
                                }
                            }
                        }
                    }
                }
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println("response>>>:" + response);

        return response;

    }

    @ResponseBody
    @RequestMapping(value = { "/rebackBillAccountFromAlipay.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap rebackBillAccountFromAlipay(HttpServletRequest request, ModelMap model) throws AlipayApiException {
        String id = request.getParameter("id");

        Housing housing = this.stockServiceImpl.getHousingById(new Integer(id));

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

        AlipayEcoCplifeBillBatchqueryResponse response = (AlipayEcoCplifeBillBatchqueryResponse) alipayClient
                .execute(billBatchUploadRequest);

        System.out.println(response.getBody());

        JSONObject jsonObject = JSONObject.fromObject(response.getBody());

        JSONObject bill_batchquery = (JSONObject) jsonObject.get("alipay_eco_cplife_bill_batchquery_response");

        int total_bill_count = bill_batchquery.getInt("total_bill_count");

        if (response.getCode().equals("10000")) {
            Object bill_result_set = bill_batchquery.get("bill_result_set");
            if (bill_result_set != null) {
                JSONArray array = JSONArray.fromObject(bill_result_set);
                if (array.size() > 0) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String bill_entry_id = object.get("bill_entry_id").toString();
                        String status = object.get("status").toString();

                        if (status.equals("FINISH_PAYMENT")) {
                            BillAccount billAccount = this.stockServiceImpl
                                    .getBillAccountByBill_entry_id(bill_entry_id);
                            if ((billAccount != null) && (billAccount.getPayStatus() != 1)) {
                                billAccount.setPayType("支付宝");
                                billAccount.setPayStatus(1);
                                billAccount.setPayDate(DateUtils.getDate());
                                this.stockServiceImpl.updateBillAccount(billAccount, bill_entry_id);
                            }
                        }
                    }
                }
            }
        }

        model.put("status", Boolean.valueOf(true));
        model.put("msg", "账单回传完毕,共回传已付款账单数:" + total_bill_count);

        return model;
    }

    @ResponseBody
    @RequestMapping(value = { "/billAccountBachUpload.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap billAccountBachUpload(HttpServletRequest request, ModelMap model) throws AlipayApiException {
        SysUsers user = getUser();

        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("sendStatus", "0");
        paramMap.put("departmentCode", user.getDepartmentCode());

        System.out.println("departmentCode   >>:" + user.getDepartmentCode());

        List<BillAccount> list = stockServiceImpl.getBillAccountsBySendStatus(paramMap);

        System.out.println("本次导入账单数量   >>:" + list.size());

        if (list.size() == 0) {
            model.put("status", Boolean.valueOf(true));
            model.put("msg", "没有可上传的账单!");
        }

        int count = 0;

        int indexNumber = 0;
        for (BillAccount billAccount : list) {
            indexNumber++;
            Proprietor proprietor = this.stockServiceImpl.getProprietorById(Integer.valueOf(billAccount
                    .getProprietorId()));

            if (proprietor == null) {
                continue;
            }
            Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(proprietor.getHousingId()));

            CommonParm commonParm = new CommonParm();

            String app_id = commonParm.getApp_id();
            String private_key = commonParm.getPrivate_key();
            String alipay_public_key = commonParm.getAlipay_public_key();

            String serverUrl = commonParm.getServerUrl();
            String format = commonParm.getFormat();
            String charset = commonParm.getCharset();
            String sign_type = commonParm.getSign_type();
            String token = housing.getToken();

            AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                    alipay_public_key, sign_type);

            String batch_id = billAccount.getBill_entry_id();

            JSONObject bizContent = new JSONObject();
            bizContent.put("batch_id", batch_id);
            bizContent.put("community_id", housing.getCommunity_id());

            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < 1; i++) {
                JSONObject room_info_set = new JSONObject();

                String proprietorName = billAccount.getProprietorName();
                int proprietorNameLen = proprietorName.length();

                room_info_set.put("bill_entry_id", billAccount.getBill_entry_id());
                room_info_set.put("out_room_id", billAccount.getOut_room_id());
                room_info_set.put("cost_type", billAccount.getCost_type());
                room_info_set.put("bill_entry_amount", String.valueOf(billAccount.getBill_entry_amount()));
                room_info_set.put("acct_period", billAccount.getAcct_period());
                room_info_set.put("release_day", billAccount.getRelease_day());
                room_info_set.put("deadline", billAccount.getDeadline());
                room_info_set.put("remark_str", "*" + proprietorName.substring(1, proprietorNameLen));
                jsonArray.add(room_info_set);
            }

            bizContent.put("bill_set", jsonArray);

            AlipayEcoCplifeBillBatchUploadRequest billBatchUploadRequest = new AlipayEcoCplifeBillBatchUploadRequest();
            billBatchUploadRequest.putOtherTextParam("app_auth_token", token);
            billBatchUploadRequest.setBizContent(bizContent.toString());

            AlipayEcoCplifeBillBatchUploadResponse response = (AlipayEcoCplifeBillBatchUploadResponse) alipayClient
                    .execute(billBatchUploadRequest);

            System.out.println("账单ID>>:" + billAccount.getBill_entry_id());
            System.out.println("code>>:" + response.getCode());
            System.out.println("indexNumber>>:" + indexNumber);

            if (response.getCode().equals("10000")) {
                count++;
                System.out.println("count>>:" + count);
                System.out.println("账单ID>>:" + billAccount.getBill_entry_id());
                billAccount.setSendStatus(1);
                this.stockServiceImpl.updateBillAccount(billAccount, billAccount.getBill_entry_id());
            }

        }

        model.put("status", Boolean.valueOf(true));
        model.put("msg", "账单批量上传完成,成功导入账单数：" + count);

        return model;
    }

    @ResponseBody
    @RequestMapping(value = { "/billAccountUpload.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap billAccountUpload(HttpServletRequest request, ModelMap model) throws AlipayApiException {
        String id = request.getParameter("id");

        BillAccount billAccount = this.stockServiceImpl.getBillAccountById(new Integer(id));

        Proprietor proprietor = this.stockServiceImpl.getProprietorById(Integer.valueOf(billAccount.getProprietorId()));

        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(proprietor.getHousingId()));

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String token = housing.getToken();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        String batch_id = billAccount.getBill_entry_id();

        JSONObject bizContent = new JSONObject();
        bizContent.put("batch_id", batch_id);
        bizContent.put("community_id", housing.getCommunity_id());

        System.out.println(housing.getCommunity_id());

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < 1; i++) {
            JSONObject room_info_set = new JSONObject();
            room_info_set.put("bill_entry_id", billAccount.getBill_entry_id());
            room_info_set.put("out_room_id", billAccount.getOut_room_id());
            room_info_set.put("cost_type", billAccount.getCost_type());
            room_info_set.put("bill_entry_amount", String.valueOf(billAccount.getBill_entry_amount()));
            room_info_set.put("acct_period", billAccount.getAcct_period());
            room_info_set.put("release_day", billAccount.getRelease_day());
            room_info_set.put("deadline", billAccount.getDeadline());
            jsonArray.add(room_info_set);
        }

        bizContent.put("bill_set", jsonArray);

        AlipayEcoCplifeBillBatchUploadRequest billBatchUploadRequest = new AlipayEcoCplifeBillBatchUploadRequest();
        billBatchUploadRequest.setBizContent(bizContent.toString());
        billBatchUploadRequest.putOtherTextParam("app_auth_token", token);

        AlipayEcoCplifeBillBatchUploadResponse response = (AlipayEcoCplifeBillBatchUploadResponse) alipayClient
                .execute(billBatchUploadRequest);

        if (response.getCode().equals("10000")) {
            model.put("status", Boolean.valueOf(true));
            model.put("msg", "账单上传成功,batch_id:" + batch_id);
            billAccount.setSendStatus(1);
            this.stockServiceImpl.updateBillAccount(billAccount, billAccount.getBill_entry_id());
        } else {
            model.put("status", Boolean.valueOf(true));
            model.put("msg", "账单上传失败！" + response.getSubMsg());
        }

        return model;
    }

    @ResponseBody
    @RequestMapping(value = { "/deleteBillAccountFromAlipay.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap deleteBillAccountFromAlipay(HttpServletRequest request, ModelMap model) throws AlipayApiException {
        
        String billAccountId = request.getParameter("billAccountId");
        String buildingId = request.getParameter("buildingId");
        String locationId = request.getParameter("locationId");
        String houseingId = request.getParameter("houseingId");

        System.out.println("houseingId>>>:" + houseingId);
        System.out.println("billAccountId>>>:" + billAccountId);
        
        int status = 0;

        if ((houseingId != null) && (!houseingId.equals(""))) {
            
            status = deleteBillAccountByHousing(houseingId);

            if (status == 0) {
                model.put("msg", "账单线下删除无响应，请联系管理员。");
            }

            if (status == 7) {
                model.put("msg", "账单线下删除完成。");
            }

            if (status == 8) {
                model.put("msg", "账单线下删除完成！并从支付宝下架了此账单。");
            }
        }

        if ((billAccountId != null) && (!billAccountId.equals(""))) {
            
            status = deleteBillAccountById(billAccountId);

            if (status == 0) {
                model.put("msg", "账单线下删除无响应，请联系管理员。");
            }

            if (status == 1) {
                model.put("msg", "账单线下删除完成。");
            }

            if (status == 2) {
                model.put("msg", "账单线下删除完成！并从支付宝下架了此账单。");
            }
        }

        if ((buildingId != null) && (!buildingId.equals(""))) {
            status = deleteBillAccountByBuilding(buildingId);

            if (status == 0) {
                model.put("msg", "账单线下删除无响应，请联系管理员。");
            }

            if (status == 3) {
                model.put("msg", "当前楼栋账单线下删除完成。");
            }

            if (status == 4) {
                model.put("msg", "当前楼栋账单线下删除完成！并从支付宝下架了账单。");
            }

        }

        if ((locationId != null) && (!locationId.equals(""))) {
            status = deleteBillAccountByLocationId(locationId);

            if (status == 0) {
                model.put("msg", "账单线下删除无响应，请联系管理员。");
            }

            if (status == 5) {
                model.put("msg", "当前单元账单线下删除完成。");
            }

            if (status == 6) {
                model.put("msg", "当前单元账单线下删除完成！并从支付宝下架了账单。");
            }
        }

        System.out.println("status>>:" + status);

        return model;
    }

    private int deleteBillAccountByHousing(String houseingId) throws AlipayApiException {
        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        int status = 0;

        Housing housing = this.stockServiceImpl.getHousingById(new Integer(houseingId));

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();

        String token = housing.getToken() == null ? "" : housing.getToken();
        String community_id = housing.getCommunity_id();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("currPage", Integer.valueOf(0));
        paramMap.put("pageSize", Integer.valueOf(100000));
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("payStatus", "0");
        paramMap.put("housingId", houseingId);

        List<BillAccount> billAccounts = this.stockServiceImpl.getBillAccounts(paramMap);

        System.out.println("billAccounts>>:" + billAccounts.size());

        int count = 0;

        for (BillAccount billAccount : billAccounts) {
            String bill_entry_id = billAccount.getBill_entry_id();

            billAccount.setDeleteStatus(1);
            this.stockServiceImpl.updateBillAccount(billAccount, billAccount.getBill_entry_id());
            status = 7;

            if (billAccount.getSendStatus() == 1) {
                count++;
                DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format,
                        charset, alipay_public_key, sign_type);

                JSONArray bill_set = new JSONArray();
                bill_set.add(bill_entry_id);

                JSONObject bizContent = new JSONObject();
                bizContent.put("community_id", community_id);
                bizContent.put("bill_entry_id_list", bill_set);

                AlipayEcoCplifeBillDeleteRequest billDeleteRequest = new AlipayEcoCplifeBillDeleteRequest();
                billDeleteRequest.setBizContent(bizContent.toString());
                billDeleteRequest.putOtherTextParam("app_auth_token", token);

                AlipayEcoCplifeBillDeleteResponse response = (AlipayEcoCplifeBillDeleteResponse) alipayClient
                        .execute(billDeleteRequest);

                if (response.getCode().equals("10000")) {
                    status = 8;
                } else {
                    System.out.println("code>>:" + response.getCode());
                    System.out.println(response.getBody());
                    System.out.println("bill_entry_id>>:" + bill_entry_id);
                }
            }
        }

        System.out.println("status  last >>" + status);
        System.out.println("count >>" + count);

        return status;
    }

    private int deleteBillAccountByLocationId(String locationId) throws AlipayApiException {
        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        int status = 0;

        Location location = this.stockServiceImpl.getLocationById(new Integer(locationId));

        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(location.getHousingId()));

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

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", community_id);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("currPage", Integer.valueOf(0));
        paramMap.put("pageSize", Integer.valueOf(100000));
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("payStatus", "0");
        paramMap.put("locationId", locationId);

        List<BillAccount> billAccounts = stockServiceImpl.getBillAccounts(paramMap);

        System.out.println("billAccounts>>:" + billAccounts);

        for (BillAccount billAccount : billAccounts) {
            String bill_entry_id = billAccount.getBill_entry_id();

            billAccount.setDeleteStatus(1);
            this.stockServiceImpl.updateBillAccount(billAccount, billAccount.getBill_entry_id());
            status = 5;

            if (billAccount.getSendStatus() == 1) {
                DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format,
                        charset, alipay_public_key, sign_type);

                JSONArray bill_set = new JSONArray();
                bill_set.add(bill_entry_id);
                bizContent.put("bill_entry_id_list", bill_set);
                AlipayEcoCplifeBillDeleteRequest billDeleteRequest = new AlipayEcoCplifeBillDeleteRequest();
                billDeleteRequest.setBizContent(bizContent.toString());
                billDeleteRequest.putOtherTextParam("app_auth_token", token);

                AlipayEcoCplifeBillDeleteResponse response = (AlipayEcoCplifeBillDeleteResponse) alipayClient
                        .execute(billDeleteRequest);

                System.out.println(response.getBody());
                System.out.println("code>>:" + response.getCode());

                if (response.getCode().equals("10000")) {
                    status = 6;
                }
            }
        }

        return status;
    }

    private int deleteBillAccountByBuilding(String buildingId) throws AlipayApiException {
        SysUsers user = getUser();

        String departmentCode = user.getDepartmentCode();

        int status = 0;

        Building building = this.stockServiceImpl.getBuildingById(new Integer(buildingId));

        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(building.getHouseId()));

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

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", community_id);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("currPage", Integer.valueOf(0));
        paramMap.put("pageSize", Integer.valueOf(100000));
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("payStatus", "0");
        paramMap.put("buildingId", buildingId);

        List<BillAccount> billAccounts = this.stockServiceImpl.getBillAccounts(paramMap);

        System.out.println("billAccounts>>:" + billAccounts);

        for (BillAccount billAccount : billAccounts) {
            String bill_entry_id = billAccount.getBill_entry_id();

            billAccount.setDeleteStatus(1);
            this.stockServiceImpl.updateBillAccount(billAccount, billAccount.getBill_entry_id());
            status = 3;

            if (billAccount.getSendStatus() != 1)
                continue;
            DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                    alipay_public_key, sign_type);

            JSONArray bill_set = new JSONArray();
            bill_set.add(bill_entry_id);
            bizContent.put("bill_entry_id_list", bill_set);
            AlipayEcoCplifeBillDeleteRequest billDeleteRequest = new AlipayEcoCplifeBillDeleteRequest();
            billDeleteRequest.setBizContent(bizContent.toString());
            billDeleteRequest.putOtherTextParam("app_auth_token", token);

            AlipayEcoCplifeBillDeleteResponse response = (AlipayEcoCplifeBillDeleteResponse) alipayClient
                    .execute(billDeleteRequest);

            System.out.println(response.getBody());
            System.out.println("code>>:" + response.getCode());

            if (response.getCode().equals("10000")) {
                status = 4;
            }

        }

        return status;
    }

    private int deleteBillAccountById(String billId) throws AlipayApiException {

        int status = 0;

        BillAccount billAccount = this.stockServiceImpl.getBillAccountById(new Integer(billId));

        billAccount.setDeleteStatus(1);

        this.stockServiceImpl.updateBillAccount(billAccount, billAccount.getBill_entry_id());

        status = 1;

        Proprietor proprietor = this.stockServiceImpl.getProprietorById(Integer.valueOf(billAccount.getProprietorId()));

        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(proprietor.getHousingId()));

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

        if (billAccount.getSendStatus() == 1) {
            DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                    alipay_public_key, sign_type);
            JSONObject bizContent = new JSONObject();
            bizContent.put("community_id", community_id);
            JSONArray bill_set = new JSONArray();
            for (int i = 0; i < 1; i++) {
                String bill_entry_id = billAccount.getBill_entry_id();
                bill_set.add(bill_entry_id);
            }
            bizContent.put("bill_entry_id_list", bill_set);
            AlipayEcoCplifeBillDeleteRequest billDeleteRequest = new AlipayEcoCplifeBillDeleteRequest();
            billDeleteRequest.setBizContent(bizContent.toString());
            billDeleteRequest.putOtherTextParam("app_auth_token", token);
            AlipayEcoCplifeBillDeleteResponse response = (AlipayEcoCplifeBillDeleteResponse) alipayClient
                    .execute(billDeleteRequest);

            System.out.println(response.getBody());

            if (response.getCode().equals("10000")) {
                status = 2;
            }
        }

        System.out.println("status  last >>" + status);

        return status;

    }

    private int deleteBillAccountByBill_entry_id(String billEntryId) throws AlipayApiException {

        int status = 0;

        BillAccount billAccount = this.stockServiceImpl.getBillAccountByBill_entry_id(billEntryId);

        billAccount.setDeleteStatus(1);

        this.stockServiceImpl.updateBillAccount(billAccount, billAccount.getBill_entry_id());

        status = 1;

        Proprietor proprietor = this.stockServiceImpl.getProprietorById(Integer.valueOf(billAccount.getProprietorId()));

        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(proprietor.getHousingId()));

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

        if (billAccount.getSendStatus() == 1) {
            DefaultAlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                    alipay_public_key, sign_type);
            JSONObject bizContent = new JSONObject();
            bizContent.put("community_id", community_id);
            JSONArray bill_set = new JSONArray();
            for (int i = 0; i < 1; i++) {
                String bill_entry_id = billAccount.getBill_entry_id();
                bill_set.add(bill_entry_id);
            }
            bizContent.put("bill_entry_id_list", bill_set);
            AlipayEcoCplifeBillDeleteRequest billDeleteRequest = new AlipayEcoCplifeBillDeleteRequest();
            billDeleteRequest.setBizContent(bizContent.toString());
            billDeleteRequest.putOtherTextParam("app_auth_token", token);
            AlipayEcoCplifeBillDeleteResponse response = (AlipayEcoCplifeBillDeleteResponse) alipayClient
                    .execute(billDeleteRequest);

            System.out.println(response.getBody());

            if (response.getCode().equals("10000")) {
                status = 2;
            }
        }

        System.out.println("status  last >>" + status);

        return status;
    }

    @ResponseBody
    @RequestMapping(value = { "/deleteRoomInfoFromAlipayById.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap deleteRoomInfoFromAlipayById(HttpServletRequest request, ModelMap model) throws AlipayApiException {
        String roomId = request.getParameter("roomId");
        JSONArray jsonArray = new JSONArray();
        RoomInfo roomInfo = this.stockServiceImpl.getRoomInfoById(Integer.valueOf(Integer.parseInt(roomId)));
        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(roomInfo.getHousingId()));
        String out_room_id = roomInfo.getOut_room_id();
        CommonParm commonParm = new CommonParm();
        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();
        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        JSONObject bizContent = new JSONObject();
        String token = housing.getToken();
        bizContent.put("community_id", housing.getCommunity_id());
        bizContent.put("batch_id", Long.valueOf(System.currentTimeMillis()));
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);
        if (roomInfo.getSendStatus() == 1) {
            jsonArray.add(out_room_id);
            System.out.println(out_room_id);
            bizContent.put("out_room_id_set", jsonArray);
            AlipayEcoCplifeRoominfoDeleteRequest deleteRequest = new AlipayEcoCplifeRoominfoDeleteRequest();
            deleteRequest.putOtherTextParam("app_auth_token", token);
            deleteRequest.setBizContent(bizContent.toString());
            AlipayEcoCplifeRoominfoDeleteResponse response = (AlipayEcoCplifeRoominfoDeleteResponse) alipayClient
                    .execute(deleteRequest);

            if (response.getCode().equals("10000")) {
                roomInfo.setDeleteStatus(1);
                this.stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());

                Proprietor proprietor = this.stockServiceImpl.getProprietorByOut_room_id(roomInfo.getOut_room_id());
                if (proprietor != null) {
                    this.stockServiceImpl.deleteProprietorById(proprietor.getId());
                }
                model.put("status", Boolean.valueOf(true));
            }

            if (response.getCode().equals("ROOM_NOT_EXISTING")) {

                roomInfo.setDeleteStatus(1);
                stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());

                // 删除业主信息
                Proprietor proprietor = stockServiceImpl.getProprietorByOut_room_id(roomInfo.getOut_room_id());
                if (proprietor != null) {
                    stockServiceImpl.deleteProprietorById(proprietor.getId());
                }
                model.put("status", true);
            }

        } else {
            roomInfo.setDeleteStatus(1);
            this.stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());

            Proprietor proprietor = this.stockServiceImpl.getProprietorByOut_room_id(roomInfo.getOut_room_id());
            if (proprietor != null) {
                this.stockServiceImpl.deleteProprietorById(proprietor.getId());
            }
        }
        model.put("status", Boolean.valueOf(true));
        model.put("msg", "房屋信息删除成功");
        return model;
    }

    @ResponseBody
    @RequestMapping(value = { "/deleteRoomInfoFromAlipay.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap deleteRoomInfoFromAlipay(HttpServletRequest request, ModelMap model) throws AlipayApiException {
        String locationId = request.getParameter("locationId");
        String token = "";
        String out_room_id;
        if ((locationId != null) && (!locationId.equals(""))) {
            Location location = this.stockServiceImpl.getLocationById(new Integer(locationId));

            Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(location.getHousingId()));

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

            int count = 0;

            model.put("status", Boolean.valueOf(true));

            JSONObject bizContent = new JSONObject();
            token = housing.getToken();
            bizContent.put("community_id", housing.getCommunity_id());
            bizContent.put("batch_id", Long.valueOf(System.currentTimeMillis()));

            Map<String, Object> paramMap2 = new HashMap<String, Object>();
            paramMap2.put("currPage", Integer.valueOf(0));
            paramMap2.put("pageSize", Integer.valueOf(199));
            paramMap2.put("housingId", housing.getId().toString());
            paramMap2.put("sendStatus", "");
            paramMap2.put("locationId", locationId);

            List<RoomInfo> roomInfos = this.stockServiceImpl.getRoomInfos(paramMap2);

            System.out.println(roomInfos);

            for (RoomInfo roomInfo : roomInfos) {
                JSONArray jsonArray = new JSONArray();

                out_room_id = roomInfo.getOut_room_id();

                if (roomInfo.getSendStatus() == 1) {
                    System.out.println(out_room_id);
                    jsonArray.add(out_room_id);

                    bizContent.put("out_room_id_set", jsonArray);

                    AlipayEcoCplifeRoominfoDeleteRequest deleteRequest = new AlipayEcoCplifeRoominfoDeleteRequest();
                    deleteRequest.putOtherTextParam("app_auth_token", token);
                    deleteRequest.setBizContent(bizContent.toString());

                    AlipayEcoCplifeRoominfoDeleteResponse response = (AlipayEcoCplifeRoominfoDeleteResponse) alipayClient
                            .execute(deleteRequest);

                    if (response.getCode().equals("10000")) {
                        roomInfo.setDeleteStatus(1);
                        stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());
                        // 删除业主信息
                        Proprietor proprietor = stockServiceImpl.getProprietorByOut_room_id(roomInfo.getOut_room_id());
                        if (proprietor != null) {
                            stockServiceImpl.deleteProprietorById(proprietor.getId());
                        }
                        model.put("status", true);
                    }

                    if (response.getCode().equals("ROOM_NOT_EXISTING")) {

                        roomInfo.setDeleteStatus(1);
                        stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());

                        // 删除业主信息
                        Proprietor proprietor = stockServiceImpl.getProprietorByOut_room_id(roomInfo.getOut_room_id());
                        if (proprietor != null) {
                            stockServiceImpl.deleteProprietorById(proprietor.getId());
                        }

                        model.put("status", true);
                    }

                } else {
                    count++;

                    roomInfo.setDeleteStatus(1);

                    this.stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());

                    Proprietor proprietor = this.stockServiceImpl.getProprietorByOut_room_id(roomInfo.getOut_room_id());

                    if (proprietor != null) {
                        this.stockServiceImpl.deleteProprietorById(proprietor.getId());
                    }
                }
            }

            model.put("msg", "房屋信息删除成功:" + count);
        } else {
            Map paramMap = new HashMap();
            paramMap.put("pageSize", Integer.valueOf(100000));

            List<Housing> housings = this.stockServiceImpl.getHousings(paramMap);

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

            int count = 0;

            model.put("status", Boolean.valueOf(true));

            for (Housing housing : housings) {
                JSONObject bizContent = new JSONObject();
                token = housing.getToken();
                bizContent.put("community_id", housing.getCommunity_id());
                bizContent.put("batch_id", Long.valueOf(System.currentTimeMillis()));

                Map paramMap2 = new HashMap();
                paramMap2.put("currPage", Integer.valueOf(0));
                paramMap2.put("pageSize", Integer.valueOf(199));
                paramMap2.put("housingId", housing.getId().toString());
                paramMap2.put("sendStatus", "");

                List<RoomInfo> roomInfos = this.stockServiceImpl.getRoomInfos(paramMap2);

                for (RoomInfo roomInfo : roomInfos) {
                    JSONArray jsonArray = new JSONArray();

                    out_room_id = roomInfo.getOut_room_id();

                    if (roomInfo.getSendStatus() == 0) {
                        System.out.println(out_room_id);
                        jsonArray.add(out_room_id);

                        bizContent.put("out_room_id_set", jsonArray);

                        AlipayEcoCplifeRoominfoDeleteRequest deleteRequest = new AlipayEcoCplifeRoominfoDeleteRequest();
                        deleteRequest.putOtherTextParam("app_auth_token", token);
                        deleteRequest.setBizContent(bizContent.toString());

                        AlipayEcoCplifeRoominfoDeleteResponse response = (AlipayEcoCplifeRoominfoDeleteResponse) alipayClient
                                .execute(deleteRequest);

                        if (response.getCode().equals("10000")) {
                            count++;
                            roomInfo.setDeleteStatus(1);
                            this.stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());

                            Proprietor proprietor = this.stockServiceImpl.getProprietorByOut_room_id(roomInfo
                                    .getOut_room_id());

                            if (proprietor != null) {
                                this.stockServiceImpl.deleteProprietorById(proprietor.getId());
                            }

                            model.put("status", Boolean.valueOf(true));
                        } else {
                            model.put("status", Boolean.valueOf(true));
                        }
                    } else {
                        roomInfo.setDeleteStatus(1);
                        this.stockServiceImpl.saveRoomInfo(roomInfo, roomInfo.getId());

                        Proprietor proprietor = this.stockServiceImpl.getProprietorByOut_room_id(roomInfo
                                .getOut_room_id());

                        if (proprietor != null) {
                            this.stockServiceImpl.deleteProprietorById(proprietor.getId());
                        }
                    }
                }

            }

            model.put("msg", "房屋信息删除成功:" + count);
        }

        return model;
    }

    @ResponseBody
    @RequestMapping(value = { "/roomInfoBachUpload.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap roomInfoBachUpload(HttpServletRequest request, ModelMap model) throws AlipayApiException {
        Map paramMap = new HashMap();
        paramMap.put("currPage", Integer.valueOf(0));
        paramMap.put("pageSize", Integer.valueOf(100000));
        List<Housing> housings = this.stockServiceImpl.getHousings(paramMap);

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();

        int count = 0;

        model.put("status", Boolean.valueOf(true));
        model.put("msg", "暂无可同步的房屋。");

        for (Housing housing : housings) {
            JSONObject bizContent = new JSONObject();

            bizContent.put("batch_id", Long.valueOf(System.currentTimeMillis()));

            String token = housing.getToken();

            bizContent.put("community_id", housing.getCommunity_id());

            JSONArray jsonArray = new JSONArray();

            Map paramMap2 = new HashMap();
            paramMap2.put("pageSize", Integer.valueOf(150));
            paramMap2.put("housingId", housing.getId().toString());
            paramMap2.put("sendStatus", String.valueOf("0"));
            paramMap2.put("currPage", Integer.valueOf(0));

            List<RoomInfo> roomInfos = this.stockServiceImpl.getRoomInfos(paramMap2);

            if (roomInfos.size() == 0) {
                continue;
            }
            System.out.println("room info size >>:" + roomInfos.size());

            for (RoomInfo roomInfo : roomInfos) {
                count++;
                JSONObject room_info_set = new JSONObject();
                room_info_set.put("out_room_id", roomInfo.getOut_room_id());
                room_info_set.put("building", roomInfo.getBuildingName());
                room_info_set.put("room", roomInfo.getRoom());
                room_info_set.put("address", roomInfo.getAddress());

                if (roomInfo.getLocationId() != 0) {
                    room_info_set.put("unit", roomInfo.getLocationName());
                }

                jsonArray.add(room_info_set);
            }

            bizContent.put("room_info_set", jsonArray);

            System.out.println("room_info_set >>:" + jsonArray.size());

            AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                    alipay_public_key, sign_type);

            AlipayEcoCplifeRoominfoUploadRequest ecoCplifeRoominfoUploadRequest = new AlipayEcoCplifeRoominfoUploadRequest();
            ecoCplifeRoominfoUploadRequest.putOtherTextParam("app_auth_token", token);
            ecoCplifeRoominfoUploadRequest.setBizContent(bizContent.toString());

            AlipayEcoCplifeRoominfoUploadResponse response = (AlipayEcoCplifeRoominfoUploadResponse) alipayClient
                    .execute(ecoCplifeRoominfoUploadRequest);

            System.out.println(response.getBody());

            JSONObject jsonObject = JSONObject.fromObject(response.getBody());

            JSONObject room_info_set = (JSONObject) jsonObject.get("alipay_eco_cplife_roominfo_upload_response");

            if (response.getCode().equals("10000")) {
                JSONArray array = JSONArray.fromObject(room_info_set.get("room_info_set"));
                if (array.size() > 0) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String out_room_id = object.get("out_room_id").toString();
                        String room_id = object.get("room_id").toString();

                        RoomInfo info = this.stockServiceImpl.getRoomInfoByOut_room_id(out_room_id);

                        if (info != null) {
                            info.setRoom_id(room_id);
                            info.setSendStatus(1);
                            this.stockServiceImpl.saveRoomInfo(info, info.getId());
                        }
                    }
                }

                model.put("status", Boolean.valueOf(true));
                model.put("msg", "房屋信息批量上传成功！" + count);
            } else {
                model.put("status", Boolean.valueOf(true));
                model.put("msg", "房屋信息批量上传失败：" + response.getSubMsg());
            }
        }

        return model;
    }

    @ResponseBody
    @RequestMapping(value = { "/roominfoUpload.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap roominfoUpload(HttpServletRequest request, ModelMap model) throws AlipayApiException {
        String id = request.getParameter("id");

        RoomInfo roomInfo = this.stockServiceImpl.getRoomInfoById(new Integer(id));

        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(roomInfo.getHousingId()));

        Building building = this.stockServiceImpl.getBuildingById(Integer.valueOf(roomInfo.getBuildingId()));

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String token = housing.getToken();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("batch_id", Long.valueOf(System.currentTimeMillis()));
        bizContent.put("community_id", housing.getCommunity_id());

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 1; i++) {
            JSONObject room_info_set = new JSONObject();
            room_info_set.put("out_room_id", roomInfo.getOut_room_id());
            room_info_set.put("building", building.getName());
            room_info_set.put("room", roomInfo.getRoom());
            room_info_set.put("address", roomInfo.getAddress());
            if (roomInfo.getLocationId() != 0) {
                room_info_set.put("unit", roomInfo.getLocationName());
            }

            jsonArray.add(room_info_set);
        }

        bizContent.put("room_info_set", jsonArray);

        System.out.println("return:" + jsonArray);

        AlipayEcoCplifeRoominfoUploadRequest ecoCplifeRoominfoUploadRequest = new AlipayEcoCplifeRoominfoUploadRequest();
        ecoCplifeRoominfoUploadRequest.putOtherTextParam("app_auth_token", token);
        ecoCplifeRoominfoUploadRequest.setBizContent(bizContent.toString());

        AlipayEcoCplifeRoominfoUploadResponse response = (AlipayEcoCplifeRoominfoUploadResponse) alipayClient
                .execute(ecoCplifeRoominfoUploadRequest);
        System.out.println(response.getBody());
        JSONObject jsonObject = JSONObject.fromObject(response.getBody());

        JSONObject room_info_set = (JSONObject) jsonObject.get("alipay_eco_cplife_roominfo_upload_response");

        if (response.getCode().equals("10000")) {
            JSONArray array = JSONArray.fromObject(room_info_set.get("room_info_set"));
            if (array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String out_room_id = object.get("out_room_id").toString();
                    String room_id = object.get("room_id").toString();

                    RoomInfo info = this.stockServiceImpl.getRoomInfoByOut_room_id(out_room_id);

                    if (info != null) {
                        info.setRoom_id(room_id);
                        info.setSendStatus(1);
                        this.stockServiceImpl.saveRoomInfo(info, info.getId());
                    }
                }
            }

            model.put("status", Boolean.valueOf(true));
            model.put("msg", "房屋信息上传成功！");
        } else {
            model.put("status", Boolean.valueOf(false));
            model.put("msg", "房屋信息上传失败：" + response.getSubMsg());
        }

        return model;
    }

    public void deleteRoomInfo(String out_room_id, Housing housing) {

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

        String token = housing.getToken();
        bizContent.put("community_id", housing.getCommunity_id());
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

    @ResponseBody
    @RequestMapping(value = { "/basicserviceModify.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap BasicserviceModify(HttpServletRequest request, ModelMap model) throws AlipayApiException {
        String id = request.getParameter("id");

        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(Integer.parseInt(id)));

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String token = housing.getToken();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", housing.getCommunity_id());
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

        if (response.getCode().equals("10000")) {
            if (response.getStatus().equals("PENDING_ONLINE")) {
                housing.setInit(1);
                this.stockServiceImpl.updateHousing(housing, housing.getId());
            }
            model.put("status", Boolean.valueOf(true));
            model.put("msg", "申请基础服务完成！");
        } else {
            model.put("status", Boolean.valueOf(true));
            model.put("msg", "申请基础服务失败：" + response.getSubMsg());
        }

        System.out.println(housing.getName());
        // quyerRoomInfo(housing);

        System.out.println(housing.getName());
        // queryBillAccount(housing);

        return model;
    }

    @ResponseBody
    @RequestMapping(value = { "/basicserviceInitialize.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap BasicserviceInitialize(HttpServletRequest request, ModelMap model) {
        String id = request.getParameter("id");

        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(Integer.parseInt(id)));

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String token = housing.getToken();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", housing.getCommunity_id());
        bizContent.put("service_type", "PROPERTY_PAY_BILL_MODE");
        bizContent.put("external_invoke_address", "https://www.alipayjf.com/alipay_web_return.do");
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

        if (response.getCode().equals("10000")) {
            if (response.getStatus().equals("PENDING_ONLINE")) {
                housing.setInit(1);
                this.stockServiceImpl.updateHousing(housing, housing.getId());
            }
            model.put("status", Boolean.valueOf(true));
            model.put("msg", "小区基础服务初始化完成,等待上线！");
        } else {
            model.put("status", Boolean.valueOf(true));
            model.put("msg", "基础服务初始化：" + response.getSubMsg());
        }

        return model;
    }

    @ResponseBody
    @RequestMapping(value = { "/communityDetailsQuery.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap communityDetailsQuery(HttpServletRequest request, ModelMap model) {
        String id = request.getParameter("id");
        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(Integer.parseInt(id)));

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String token = housing.getToken();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_id", housing.getCommunity_id());

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

        JSONObject jsonObject = JSONObject.fromObject(response.getBody());

        JSONObject room_info_set = (JSONObject) jsonObject.get("alipay_eco_cplife_community_details_query_response");

        String msgJiaofei = "";
        String msgXiaoqu = "";

        if (response.getCode().equals("10000")) {
            Object qr_code_image = room_info_set.get("qr_code_image");
            Object audit_status = room_info_set.get("audit_status");

            JSONArray array = JSONArray.fromObject(room_info_set.get("community_services"));

            if (array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String qr_code_image_jiaofei = object.get("qr_code_image").toString();
                    msgJiaofei = qr_code_image_jiaofei;
                }
            }

            if (qr_code_image != null) {
                String qr_code_image_xiaoqu = qr_code_image.toString();
                msgXiaoqu = qr_code_image_xiaoqu;
            }

            model.put("status", Boolean.valueOf(true));
            model.put("msgJiaofei", msgJiaofei);
            model.put("msgXiaoqu", msgXiaoqu);
            model.put("audit_status", audit_status);
        }

        return model;
    }

    @ResponseBody
    @RequestMapping(value = { "/synchCommunityToAlipay.html" },
            method = { org.springframework.web.bind.annotation.RequestMethod.POST })
    public ModelMap synchCommunityToAlipay(HttpServletRequest request, ModelMap model) {
        String id = request.getParameter("id");
        Housing housing = this.stockServiceImpl.getHousingById(Integer.valueOf(Integer.parseInt(id)));

        CommonParm commonParm = new CommonParm();

        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();

        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        String token = housing.getToken();

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);

        JSONObject bizContent = new JSONObject();
        bizContent.put("community_name", housing.getName().trim());
        bizContent.put("community_address", housing.getCommunity_address().trim());
        bizContent.put("district_code", housing.getDistrict_code().trim());
        bizContent.put("city_code", housing.getCity_code().trim());
        bizContent.put("province_code", housing.getProvince_code().trim());
        bizContent.put("community_locations", housing.getCommunity_locations().trim());
        bizContent.put("hotline", housing.getPhone());

        AlipayEcoCplifeCommunityCreateRequest requestAlipayEcoCplifeCommunityCreateRequest = new AlipayEcoCplifeCommunityCreateRequest();
        requestAlipayEcoCplifeCommunityCreateRequest.putOtherTextParam("app_auth_token", token);
        requestAlipayEcoCplifeCommunityCreateRequest.setBizContent(bizContent.toString());

        AlipayEcoCplifeCommunityCreateResponse response = null;
        try {
            response = (AlipayEcoCplifeCommunityCreateResponse) alipayClient
                    .execute(requestAlipayEcoCplifeCommunityCreateRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println("response>>>:" + response.getBody());

        if (response.getCode().equals("10000")) {
            model.put("status", Boolean.valueOf(true));
            model.put("msg", "小区同步完成...,等待下一步基础服务初始化！");
            housing.setCommunity_id(response.getCommunityId());
            this.stockServiceImpl.updateHousing(housing, housing.getId());
        } else {
            model.put("status", Boolean.valueOf(true));
            model.put("msg", "小区同步失败：" + response.getSubMsg());
        }

        return model;
    }
    
    @RequestMapping("/alipay_web_return.do")
    public void alipay_web_return(HttpServletRequest request,HttpServletResponse response) {
        log.info("--------------wangshuodong:调用支付宝回调接口-----------------------");
        boolean isprint = false;
        String bizContent = "";
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        CommonParm commonParm = new CommonParm();
        String alipay_public_key = commonParm.getAlipay_public_key();
        String private_key = commonParm.getPrivate_key();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();
        log.info(alipay_public_key);
        try {
            boolean checkResult = AlipaySignature.rsaCheckV2(params, alipay_public_key, charset, sign_type); //调用SDK验证签名
            /* 实际验证过程建议商户务必添加以下校验：
            1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
            2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
            3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
            4、验证app_id是否为该商户本身。
            */
            if(checkResult) {//验证成功
                //商户订单号
                String out_trade_no = new String(request.getParameter("det_list").getBytes("ISO-8859-1"),"UTF-8");
                //本次交易支付的订单金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
                //交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
                if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                    //BillAccount billAccount = stockServiceImpl.getMaxBill_entry_id(out_trade_no);
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("bill_entry_id", out_trade_no);
                    paramMap.put("currPage", Integer.valueOf(0));
                    paramMap.put("pageSize", Integer.valueOf(10));
                    List<BillAccount> billAccounts = this.stockServiceImpl.getBillAccounts(paramMap);
                    BillAccount billAccount = billAccounts.get(0);
                    if (total_amount.equals(billAccount.getBill_entry_amount())) {
                        PrintInfo info = printInfoService.selectBydepartmentId(billAccount.getDepartmentId());
                        PrintMessage obj = new PrintMessage(info.getMachineCode(), info.getMsign());
                        StringBuffer sb = new StringBuffer("");
                        sb.append("<center>支付宝智慧小区</center>\r");
                        sb.append("小区名称："+billAccount.getDepartmentName()+"\r");
                        sb.append(billAccount.getRoomAddress() + "\r");
                        sb.append("业主姓名："+billAccount.getProprietorName()+"\r");
                        sb.append("付款时间："+billAccount.getPayDate()+"\r");
                        sb.append("订单编号："+out_trade_no+"\r");
                        sb.append("支付方式："+billAccount.getPayType()+"\r");
                        sb.append("缴费金额："+total_amount+"\r");
                        sb.append("缴费明细：\r");
                        sb.append("<table><tr><td>类别</td><td>账期</td><td>金额</td></tr><tr><td>"+billAccount.getCost_type()+"</td><td>"+billAccount.getAcct_period()+"</td><td>"+total_amount+"</td></tr></table>\r");
                        sb.append("<center><FB><FS>浙江中都物业有限公司</FS></FB></center>\r");
                        sb.append("<center>技术支持：杭州早早科技 400-720-8888</center>\r");
                        sb.append("----------------------\r");
                        sb.append("<center>交易小票</center>\r");
                        isprint = obj.sendContent(sb.toString());
                    }
                }
                bizContent = "{\"econotify\":\"success\"}";
                if (isprint) {
                    log.info("--------------wangshuodong:物业缴费小票打印成功-----------------------");
                }
            }else {//验证失败
                log.info("--------------wangshuodong:物业缴费异步通知验签失败-----------------------");
            }
            
            String result = AlipaySignature.encryptAndSign(bizContent, alipay_public_key, private_key, charset, false, true, sign_type);
            response.getOutputStream().print(result);
        }catch(Exception e) {
            log.info(e);
            e.printStackTrace();
        }
    }
}
