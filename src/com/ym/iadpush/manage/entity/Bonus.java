package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Bonus {
	private int id;
	private int uid;
	private String username;
	private int createUid;
	private Date createTime;
	private BigDecimal bonus;
	private BigDecimal taxBonus;
	private String type;
	private String description;
	private int status;
	private java.sql.Date addDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getCreateUid() {
		return createUid;
	}
	public void setCreateUid(int createUid) {
		this.createUid = createUid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getBonus() {
		return bonus;
	}
	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}
	public BigDecimal getTaxBonus() {
		return taxBonus;
	}
	public void setTaxBonus(BigDecimal taxBonus) {
		this.taxBonus = taxBonus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public java.sql.Date getAddDate() {
		return addDate;
	}
	public void setAddDate(java.sql.Date addDate) {
		this.addDate = addDate;
	}
	
}
