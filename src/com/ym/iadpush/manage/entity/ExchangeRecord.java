/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.util.Date;

public class ExchangeRecord implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Date addate;
    private java.sql.Timestamp createTime;
    private Integer uid;
    private Integer username;
    private Integer companyId;
    private String companyName = "";
    private Integer productId;
    private String productName = "";
    private Integer number;
    private Integer departmentId;
    private String departmentCode = "";
    private String code = "";
    private String remark = "";
    private int checked;
    private int auditingPeople;
    private Date auditingDate;
    private java.sql.Timestamp auditingTime;
    private String auditingUsername = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getAddate() {
        return addate;
    }

    public void setAddate(Date addate) {
        this.addate = addate;
    }

    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getAuditingPeople() {
        return auditingPeople;
    }

    public void setAuditingPeople(int auditingPeople) {
        this.auditingPeople = auditingPeople;
    }

    public Date getAuditingDate() {
        return auditingDate;
    }

    public void setAuditingDate(Date auditingDate) {
        this.auditingDate = auditingDate;
    }

    public java.sql.Timestamp getAuditingTime() {
        return auditingTime;
    }

    public void setAuditingTime(java.sql.Timestamp auditingTime) {
        this.auditingTime = auditingTime;
    }

    public String getAuditingUsername() {
        return auditingUsername;
    }

    public void setAuditingUsername(String auditingUsername) {
        this.auditingUsername = auditingUsername;
    }

}
