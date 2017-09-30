package com.ym.iadpush.common.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.ym.iadpush.common.utils.SysConfigPropertiesHelper;

public class EmailUtil {
	
	private static final String email_from_host = SysConfigPropertiesHelper.getProperty("email_from_host");// 邮件发送方的地址
    
	private static final int    eamil_from_port = Integer.valueOf(SysConfigPropertiesHelper.getProperty("eamil_from_port")) ;// 邮件发送方的端口
   
	private static final String email_from_address = SysConfigPropertiesHelper.getProperty("email_from_address");//邮件发送方 显示账号的地址
    
	private static final String  email_from_nick = SysConfigPropertiesHelper.getProperty("email_from_nick");//邮件发送方的名字
   
	private static final String  email_from_username = SysConfigPropertiesHelper.getProperty("email_from_username");//邮件发送方的用户名
   
	private static final String  email_from_password = SysConfigPropertiesHelper.getProperty("email_from_password");//邮件发送方的密码

	
	/**
	 * 
	 * 方法说明：发送普通邮件
	 * @param email_from_host  邮件发送方的地址
	 * @param eamil_from_port  邮件发送方的端口
	 * @param email_from_address  邮件发送方的显示账户地址
	 * @param email_from_nick 邮件发送方的名称
	 * @param email_from_username 邮件发送方的邮件帐号
	 * @param email_from_password 邮件发送方的邮件密码
	 * @param receive	收件人的邮件地址 如: John@163.com
	 * @param subject	邮件主题
	 * @param html	邮件内容(普通文本)
	 * @throws EmailException
	 */
	public static void sendCommonEmail( String receive, String subject, String html) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		 email.setHostName(email_from_host);
		 email.setSmtpPort(eamil_from_port);
		 email.setFrom(email_from_address, email_from_nick);
		 email.setAuthentication(email_from_username, email_from_password);
		 email.addTo(receive, receive);
		 email.setSubject(subject);
		 email.setSSL(true);
		 email.setSslSmtpPort(eamil_from_port+"");
		 try {
			email.setHtmlMsg(new String(html.getBytes(),"ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 email.send();
	}
	
	/**
	 * 发送管理员邮件
	 * @param receive
	 * @param subject
	 * @param html
	 * @throws EmailException
	 */
	public static void sendToManager( String receive, String subject, String html) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		 email.setHostName(email_from_host);
		 email.setSmtpPort(eamil_from_port);
		 email.setFrom(email_from_address, email_from_nick);
		 email.setAuthentication(email_from_username, email_from_password);
		 email.addTo(receive, receive);
		 email.setSubject(subject);
		 email.setSSL(true);
		 email.setSslSmtpPort(eamil_from_port+"");
		 try {
			email.setHtmlMsg(new String(html.getBytes(),"ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 email.send();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
