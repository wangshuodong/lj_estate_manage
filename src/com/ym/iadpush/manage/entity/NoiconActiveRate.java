
package com.ym.iadpush.manage.entity;

import java.sql.Date;


public class NoiconActiveRate {
	private Date date;
	/**
	 * 新增量
	 */
	private Integer acount;
	/**
	 * 安装量
	 */
	private Integer icount;
	private Double rate;
	private String kv;
	
	public String getKv() {
		return kv;
	}
	public void setKv(String kv) {
		this.kv = kv;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getAcount() {
		return acount;
	}
	public void setAcount(Integer acount) {
		this.acount = acount;
	}
	public Integer getIcount() {
		return icount;
	}
	public void setIcount(Integer icount) {
		this.icount = icount;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	
}
