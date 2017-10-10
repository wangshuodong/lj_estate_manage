package com.ym.iadpush.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class PrintMessage {

	private String partner = "16396";// 用户id
	private String apiKey = "016e55076796080ecb8228e2f2e2ea9ad798b444";// API密钥
	private String machine_code;// 打印机终端号
    private String mKey;// 打印机密钥
//	private String machine_code = "4004540276";// 打印机终端号
//	private String mKey = "cfji72jqwtf8";// 打印机密钥

	public PrintMessage(String machine_code, String mKey) {
	    this.machine_code = machine_code;
	    this.mKey = mKey;
	}
	
	public PrintMessage() {
    }
	
	// 打印机是否在线接口0是离线1是在线2是缺纸
	public boolean pmRequest() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("machine_code", machine_code);
		params.put("partner", partner);
		String sign = signRequest(params);
		params.put("sign", sign);
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod("http://open.10ss.net:8888/getstatus.php");

		for (Map.Entry<String, String> entry : params.entrySet()) {
			post.addParameter(entry.getKey(), entry.getValue());
		}

		HttpMethodParams param = post.getParams();
		param.setContentCharset("UTF-8");

		try {
			httpClient.executeMethod(post);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 如果返回200，表明成功
		if (post.getStatusCode() == 200) {
			try {
				String result;
				result = post.getResponseBodyAsString();
				System.out.println(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			System.out.println("打印失败！");
			return false;
		}

	}

	// 打印机打印消息
	public boolean sendRequest(String content) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		params.put("machine_code", machine_code);
		params.put("content", content);
		String sign = signRequest(params);
		params.put("sign", sign);

		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod("http://open.10ss.net:8888");

		for (Map.Entry<String, String> entry : params.entrySet()) {
			post.addParameter(entry.getKey(), entry.getValue());
		}

		HttpMethodParams param = post.getParams();
		param.setContentCharset("UTF-8");

		try {
			httpClient.executeMethod(post);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 如果返回200，表明成功
		if (post.getStatusCode() == 200) {
			try {
				String result;
				result = post.getResponseBodyAsString();
				if (result.equals("1")) {// 数据已经发送到客户端
					System.out.println("打印成功");
					return true;
				} else {
					System.out.println("打印失败,返回值：" + result);
					return false;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		} else {
			System.out.println("打印失败！");
			return false;
		}

	}

	public boolean sendContent(String content) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("partner", partner);
			params.put("machine_code", machine_code);
			String time = String.valueOf(System.currentTimeMillis());
			params.put("time", time);
			String sign = signRequest(params);

			byte[] data = ("partner=" + partner + "&machine_code=" + machine_code + "&content=" + content + "&sign="
					+ sign + "&time=" + time).getBytes();
			URL url = new URL("http://open.10ss.net:8888");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5 * 1000);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "text/html; charset=utf-8");
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));

			// 获取输出流
			OutputStream outStream = conn.getOutputStream();
			// 传入参数
			outStream.write(data);
			outStream.flush();
			outStream.close();

			// 获取输入流
			InputStream is = conn.getInputStream();

			System.out.println(conn.getResponseCode());
			if (conn.getResponseCode() == 200) {
				int i = -1;
				byte[] b = new byte[1024];
				StringBuffer result = new StringBuffer();
				while ((i = is.read(b)) != -1) {
					result.append(new String(b, 0, i));
				}

				String sub = result.toString();
				if (sub.equals("1")) {// 数据已经发送到客户端
					System.out.println("打印成功");
					return true;
				} else {
					System.out.println("打印失败,返回值：" + result);
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 打印签名
	 * 
	 * @param params
	 * @return
	 */
	public String signRequest(Map<String, String> params) {
		Map<String, String> sortedParams = new TreeMap<String, String>();
		sortedParams.putAll(params);
		Set<Map.Entry<String, String>> paramSet = sortedParams.entrySet();
		StringBuilder query = new StringBuilder();
		query.append(apiKey);
		for (Map.Entry<String, String> param : paramSet) {
			query.append(param.getKey());
			query.append(param.getValue());
		}
		query.append(mKey);
		String encryptStr = MD5.MD5Encode(query.toString()).toUpperCase();
		return encryptStr;
	}
	
	public static void main(String[] args) {
        // pmRequest();//查询打印机状态

        StringBuffer sb = new StringBuffer("");
        
        sb.append("<center>智慧宝智慧小区</center>\r");
        sb.append("小区名称：首创十方界\r");
        sb.append("6栋7单元701房间\r");
        sb.append("业主姓名：汪铄东\r");
        sb.append("付款时间：2017-12-11 12:24:30\r");
        sb.append("订单编号：201700061656541544\r");
        sb.append("支付方式：支付宝\r");
        sb.append("缴费金额：500.00\r");
        sb.append("缴费明细：\r");
        sb.append("<table><tr><td>类别</td><td>账期</td><td>金额</td></tr><tr><td>物业费</td><td>201706</td><td>500.00</td></tr></table>\r");
        sb.append("<center><FB><FS>浙江中都物业有限公司</FS></FB></center>\r");
        sb.append("<center>技术支持：杭州早早科技 400-720-8888</center>\r");
        sb.append("----------------------\r");
        sb.append("<center>交易小票</center>\r");

        System.out.println(sb.toString());

        try {
            PrintMessage obj = new PrintMessage();
            obj.sendContent(sb.toString());// 打印消息
//           sendRequest(sb.toString());//打印消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMachine_code() {
        return machine_code;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
