package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.text.DecimalFormat;

public class UserBalance implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -1311732017245956027L;
	
	DecimalFormat df = new DecimalFormat(".00");

	private Integer userId;
	
	private Integer balanceId;
	
	private String username;
	
	private String qq;
	
	private String phone;
	
	private String bankname;
	
	private String bankNo;
	
	private String province;
	
	private String city;
	
	private String bankUserName;
	
	private String bankAddress;
	
	private String payUsername;
	
	private String payTime;
	
	/**
	 * 身份证号码
	 */
	private String certificate;
	
	/**
	 * 身份认证状态
	 */
	private String certification;
	
	private String payDate;
	
	private double bonus;
	
	private String sdate;
	
	private String edate;
	
	private double earn;
	
	@SuppressWarnings("unused")
	private double fee;
	
	private double earnNoFee;
	
	private Integer status;
	
	private String remark;
	
	private String serviceName;
	
	private Integer count;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(Integer balanceId) {
		this.balanceId = balanceId;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBankUserName() {
		return bankUserName;
	}

	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public String getPayUsername() {
		return payUsername;
	}

	public void setPayUsername(String payUsername) {
		this.payUsername = payUsername;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getPhone() {
		return phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public double getEarn() {
		return earn;
	}

	public void setEarn(double earn) {
		this.earn = earn;
	}
	
	/**
     * @return Returns the fee
     */
    public double getFee() {
        return Double.valueOf(df.format(earn + bonus - earnNoFee));
    }

	public double getEarnNoFee() {
		return earnNoFee;
	}

	public void setEarnNoFee(double earnNoFee) {
		this.earnNoFee = earnNoFee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? "":remark;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
