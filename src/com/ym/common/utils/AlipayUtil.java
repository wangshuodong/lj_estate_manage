/**
 * 
 */
package com.ym.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayEcoCplifeResidentinfoDeleteRequest;
import com.alipay.api.request.AlipayEcoCplifeRoominfoDeleteRequest;
import com.alipay.api.response.AlipayEcoCplifeResidentinfoDeleteResponse;
import com.alipay.api.response.AlipayEcoCplifeRoominfoDeleteResponse;
import com.ym.iadpush.manage.entity.common.CommonParm;

/**
 * @Copyright: 物业缴费支付宝工具类
 * @Author: lixingbiao 2017年8月22日 下午3:24:46 
 * @Version: $Id$
 * @Desc: <p></p>
 */
public class AlipayUtil {
    /**
     * 删除小区业主信息
     * @Author lixingbiao 2017年8月22日 下午3:52:09
     * @param community_id
     * @param out_resident_id_set
     * @throws Exception
     */
    public static final void deleteProprietor(String community_id,List<String> out_resident_id_set) throws Exception{
        if(out_resident_id_set.size()>200){
            throw new Exception("单次最多删除200条业主信息");
        }
        CommonParm commonParm = new CommonParm();
        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();
        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();

        JSONObject params = new JSONObject();
        params.put("community_id", community_id);
        params.put("out_resident_id_set", out_resident_id_set.toString());
        
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);
        AlipayEcoCplifeResidentinfoDeleteRequest request = new AlipayEcoCplifeResidentinfoDeleteRequest();
        request.setBizContent(params.toString());
        AlipayEcoCplifeResidentinfoDeleteResponse response = alipayClient.execute(request);
        if(!response.isSuccess()){
            throw new Exception(response.getSubMsg());
        }
    }
    
    /**
     * 从支付宝删除房屋
     * @Author lixingbiao 2017年8月22日 下午3:46:25
     * @param community_id
     * @param out_room_id_set
     * @throws Exception
     */
    public static final void deleteRoomInfo(String community_id,List<String> out_room_id_set) throws Exception{
        if(out_room_id_set.size()>200){
            throw new Exception("单次最多删除200条房屋信息");
        }
        CommonParm commonParm = new CommonParm();
        String app_id = commonParm.getApp_id();
        String private_key = commonParm.getPrivate_key();
        String alipay_public_key = commonParm.getAlipay_public_key();
        String serverUrl = commonParm.getServerUrl();
        String format = commonParm.getFormat();
        String charset = commonParm.getCharset();
        String sign_type = commonParm.getSign_type();

        JSONObject params = new JSONObject();
        params.put("community_id", community_id);
        params.put("out_room_id_set", out_room_id_set.toString());
        
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, app_id, private_key, format, charset,
                alipay_public_key, sign_type);
        AlipayEcoCplifeRoominfoDeleteRequest request = new AlipayEcoCplifeRoominfoDeleteRequest();
        request.setBizContent(params.toString());
        AlipayEcoCplifeRoominfoDeleteResponse response = alipayClient.execute(request);
        if(!response.isSuccess()){
            throw new Exception(response.getSubMsg());
        }
    }
    
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list.toString());
    }
}
