package com.ym.iadpush.manage.entity;

import java.io.Serializable;

public class Earn implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4976230639691722126L;
	
	private Integer uid;
	
	private Integer qid;
	
	private Integer appid;
	
	private String edate;
	
	private double earnMoney;
	
	private Integer status;
	
	private Integer hour;
	
	private Integer type;
	
	private String mindate;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public String getMindate() {
		return mindate;
	}

	public void setMindate(String mindate) {
		this.mindate = mindate;
	}

	public Integer getAppid() {
		return appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public double getEarnMoney() {
		return earnMoney;
	}

	public void setEarnMoney(double earnMoney) {
		this.earnMoney = earnMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
