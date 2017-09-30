package com.ym.iadpush.manage.entity;

import com.ym.common.utils.Arith;


public class DataReport {
	
	/**
	 * 用户ID
	 */
	private int uid;
	
	/**
	 * 应用ID
	 */
	private int appid;
	
	/**
	 * 渠道ID
	 */
	private int qid;
	
	/*
	 *应用名称 
	 */
	private String appName;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 支出比率
	 */
	private String erate;
	
	/**
	 * 新增比率
	 */
	private String irate;
	
	/**
	 * 活跃比率
	 */
	private String arate;
	
	private String earn1;
	
	private String earn2;
	
	private String icount1;
	
	private String icount2;
	
	private String acount1;
	
	private String acount2;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
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

	public String getErate() {
		try {
			erate = Arith.getBigDecimalValue(Double.valueOf(erate), 4);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return erate;
	}

	public void setErate(String erate) {
		try {
			erate = Arith.getBigDecimalValue(Double.valueOf(erate), 4);
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.erate = erate;
	}

	public String getIrate() {
		try {
			irate = Arith.getBigDecimalValue(Double.valueOf(irate), 4);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return irate;
	}

	public void setIrate(String irate) {
		try {
			irate = Arith.getBigDecimalValue(Double.valueOf(irate), 4);
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.irate = irate;
	}

	public String getArate() {
		try {
			arate = Arith.getBigDecimalValue(Double.valueOf(arate), 4);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return arate;
	}

	public void setArate(String arate) {
		try {
			arate = Arith.getBigDecimalValue(Double.valueOf(arate), 4);
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.arate = arate;
	}

	public String getEarn1() {
		return earn1;
	}

	public void setEarn1(String earn1) {
		this.earn1 = earn1;
	}

	public String getEarn2() {
		return earn2;
	}

	public void setEarn2(String earn2) {
		this.earn2 = earn2;
	}

	public String getIcount1() {
		return icount1;
	}

	public void setIcount1(String icount1) {
		this.icount1 = icount1;
	}

	public String getIcount2() {
		return icount2;
	}

	public void setIcount2(String icount2) {
		this.icount2 = icount2;
	}

	public String getAcount1() {
		return acount1;
	}

	public void setAcount1(String acount1) {
		this.acount1 = acount1;
	}

	public String getAcount2() {
		return acount2;
	}

	public void setAcount2(String acount2) {
		this.acount2 = acount2;
	}
	
	

}
