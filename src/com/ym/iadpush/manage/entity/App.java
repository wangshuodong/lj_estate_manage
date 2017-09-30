package com.ym.iadpush.manage.entity;

import java.sql.Date;


public class App {
	/***
	 * 应用ID
	 */
	private Integer aid;
	/***
	 * 用户ID
	 */
	private Integer uid;
	/***
	 * 渠道ID
	 */
	private Integer qid;
	/***
	 *用户名称 
	 */
	private String username;
	/***
	 * 渠道名称
	 */
	private String qname;
	/***
	 * 应用名称
	 */
	private String appname;
	/***
	 * 今日安装量
	 */
	private Integer icount=0;
	
	/**
	 * 
	 * 活跃
	 */
	private Integer acount=0;
	/***
	 * 今日收入
	 */
	
	private Double earn_money=0.00;
	/***
	 * 状态
	 */
	private String status;
	/***
	 * 开发者报表--用户ID
	 */
	private Integer duid;
	/***
	 * 开发者报表--应用ID ---APP ID
	 */
	private Integer dappid;
	
	private Date addate;
	
	private Date nowtime;
	
	private Integer totalICount;
	
	private Double totalEarn;
	
	private String greenYellow;
	
	
	public String getGreenYellow() {
		return greenYellow;
	}
	public void setGreenYellow(String greenYellow) {
		this.greenYellow = greenYellow;
	}
	public Integer getTotalICount() {
		return totalICount;
	}
	public void setTotalICount(Integer totalICount) {
		this.totalICount = totalICount;
	}
	public Double getTotalEarn() {
		return totalEarn;
	}
	public void setTotalEarn(Double totalEarn) {
		this.totalEarn = totalEarn;
	}
	public Date getNowtime() {
		return nowtime;
	}
	public void setNowtime(Date nowtime) {
		this.nowtime = nowtime;
	}
	public Integer getAid() {
		return aid;
	}
	public void setAid(Integer aid) {
		this.aid = aid;
	}
	public Date getAddate() {
		return addate;
	}
	public void setAddate(Date addate) {
		this.addate = addate;
	}
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getQname() {
		return qname;
	}
	public void setQname(String qname) {
		this.qname = qname;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public Integer getIcount() {
		return icount;
	}
	public void setIcount(Integer icount) {
		this.icount = icount;
	}
	public Double getEarn_money() {
		return earn_money;
	}
	public void setEarn_money(Double earn_money) {
		this.earn_money = earn_money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getDuid() {
		return duid;
	}
	public void setDuid(Integer duid) {
		this.duid = duid;
	}
	public Integer getDappid() {
		return dappid;
	}
	public void setDappid(Integer dappid) {
		this.dappid = dappid;
	}
	public App(Integer aid, Integer uid, Integer qid, String username, String qname,
			String appname, Integer icount, Double earn_money) {
		super();
		this.aid = aid;
		this.uid = uid;
		this.qid = qid;
		this.username = username;
		this.qname = qname;
		this.appname = appname;
		this.icount = icount;
		this.earn_money = earn_money;
	}
	public App() {
		super();
	}
	public Integer getAcount() {
		return acount;
	}
	public void setAcount(Integer acount) {
		this.acount = acount;
	}
	
	
}
