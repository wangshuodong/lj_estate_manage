package com.ym.iadpush.manage.entity;

import java.sql.Date;



public class NoiconActive {
	
	private String sv;
	private String kv;
	private Integer appid;
	private Integer uid;
	private Integer qdid;
	private String version;
	private Integer icount;
	private Integer acount;
	private String appName;
	private String userName;
	private String qdName;
	private Date adate;
	
	private Long totalIcount;
	private Long totalAcount;
	
	public Long getTotalIcount() {
		return totalIcount;
	}
	public void setTotalIcount(Long totalIcount) {
		this.totalIcount = totalIcount;
	}
	public Long getTotalAcount() {
		return totalAcount;
	}
	public void setTotalAcount(Long totalAcount) {
		this.totalAcount = totalAcount;
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
	public Integer getAppid() {
		return appid;
	}
	public void setAppid(Integer appid) {
		this.appid = appid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getQdid() {
		return qdid;
	}
	public void setQdid(Integer qdid) {
		this.qdid = qdid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getIcount() {
		return icount;
	}
	public void setIcount(Integer icount) {
		this.icount = icount;
	}
	public Integer getAcount() {
		return acount;
	}
	public void setAcount(Integer acount) {
		this.acount = acount;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getQdName() {
		return qdName;
	}
	public void setQdName(String qdName) {
		this.qdName = qdName;
	}
	public Date getAdate() {
		return adate;
	}
	public void setAdate(Date adate) {
		this.adate = adate;
	}
	
	
}
