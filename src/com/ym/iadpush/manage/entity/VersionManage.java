package com.ym.iadpush.manage.entity;

import java.util.Date;


public class VersionManage {
	/**
	 * 版本ID
	 */
	private int id;
	/**
	 * 应用key
	 */
	private String appkey;
	/**
	 * 框架版本号
	 */
	private String sv;
	/**
	 * 子版本号
	 */
	private String kv;
	/**
	 * 创建时间
	 */
	private Date create_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getSv() {
		return sv;
	}
	public void setSv(String sv) {
		this.sv = sv;
	}
	public String getKv() {
		return kv;
	}
	public void setKv(String kv) {
		this.kv = kv;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	
}
