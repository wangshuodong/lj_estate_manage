package com.ym.iadpush.manage.entity;

import java.sql.Date;


public class NoiconCount {
	/**
	 * ID 
	 */
	private int id;
	/**
	 * 日期
	 */
	private Date adate;
	/**
	 * 展示量
	 */
	private Integer scount;
	/**
	 *安装量 
	 */
	private Integer icount;
	/**
	 * 点击量
	 */
	private Integer infoc;
	/**
	 * 点击率
	 */
	private Double infoc_rate=0.00;
	/**
	 * 安装率
	 */
	private Double icount_rate=0.00;
	
	private String kv;
	
	private String sv;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getAdate() {
		return adate;
	}
	public void setAdate(Date adate) {
		this.adate = adate;
	}
	public Integer getScount() {
		return scount;
	}
	public void setScount(Integer scount) {
		this.scount = scount;
	}
	public Integer getIcount() {
		return icount;
	}
	public void setIcount(Integer icount) {
		this.icount = icount;
	}
	public Integer getInfoc() {
		return infoc;
	}
	public void setInfoc(Integer infoc) {
		this.infoc = infoc;
	}
	public Double getInfoc_rate() {
		return infoc_rate;
	}
	public void setInfoc_rate(Double infoc_rate) {
		this.infoc_rate = infoc_rate;
	}
	public Double getIcount_rate() {
		return icount_rate;
	}
	public void setIcount_rate(Double icount_rate) {
		this.icount_rate = icount_rate;
	}
	public String getKv() {
		return kv;
	}
	public void setKv(String kv) {
		this.kv = kv;
	}
	public String getSv() {
		return sv;
	}
	public void setSv(String sv) {
		this.sv = sv;
	}
	
}
