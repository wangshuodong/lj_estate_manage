/**
 * 
 */
package com.ym.common.utils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
 

 
/**
*短信API服务调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/54
**/
 
public class JuheUtile {
    public static void main(String[] args) {
        try {
            sendSmsMessage("18996245667", "重庆", "102", "ly");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void sendSmsMessage(String phone,String house,String number,String property) throws Exception{
        house = "#house#="+house;
        number = "#number#="+number;
        property = "#property#="+property;
        String value = house+"&"+number+"&"+property;
        try {
            value = URLEncoder.encode(value,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("发送失败");
        }
        //拼接请求参数
        String param =
                "mobile="+phone+"&"
                        +"tpl_id="+ 41750+"&"
                        +"tpl_value="+value+"&"
                        +"key="+"d9f33d45dcf3ede7878be5dfd6268ff5";
        //System.out.println(param);
        //发送get请求
        String resJson =sendGet("http://v.juhe.cn/sms/send",param);
        System.out.println(resJson);
    }
    
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}