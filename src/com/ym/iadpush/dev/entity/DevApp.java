package com.ym.iadpush.dev.entity;

public class DevApp {
	/***
	 * 应用ID
	 */
	private int aid;
	/***
	 * 用户ID
	 */
	private int uid;
	/***
	 * 渠道ID
	 */
	private int qid;
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
	private int icount;
	/***
	 * 今日收入
	 */
	private Double earn_money=0.00;
	
	private String status;
	
	
	private String ex;
	private String packagename;
	
	
	
	public String getEx() {
		return ex;
	}
	public void setEx(String ex) {
		this.ex = ex;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
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
	public int getIcount() {
		return icount;
	}
	public void setIcount(int icount) {
		this.icount = icount;
	}
	public Double getEarn_money() {
		return earn_money;
	}
	public void setEarn_money(Double earn_money) {
		this.earn_money = earn_money;
	}
	public DevApp(int aid, int uid, int qid, String username, String qname,
			String appname, int icount, Double earn_money) {
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
	public DevApp() {
		super();
	}
	
	
}
