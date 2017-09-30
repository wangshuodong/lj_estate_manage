package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Balance implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -9120108747788989530L;
	
    private int	id;
	
	private int	status;
	
	private int	uid;
	
	private double earn;
	
	private double earnNofee;
	
	private double bonus;
	
	private int	operuser;
	
	private String edate;
	
	private String sdate;
	
	private Timestamp addtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public double getEarn() {
		return earn;
	}

	public void setEarn(double earn) {
		this.earn = earn;
	}

	public double getEarnNofee() {
		return earnNofee;
	}

	public void setEarnNofee(double earnNofee) {
		this.earnNofee = earnNofee;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public int getOperuser() {
		return operuser;
	}

	public void setOperuser(int operuser) {
		this.operuser = operuser;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public Timestamp getAddtime() {
		return addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}
	
}
