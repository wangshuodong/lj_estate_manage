/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;
import java.util.Date;

// 预收款
public class AdvancePaymentRecord {
    private Integer id;
    private String code = "";
    private Integer uid;
    private Date addate;
    private java.sql.Timestamp createTime;
    private Integer companyId;
    private String companyName = "";
    private Integer stockMonth;
    private Integer stockDetail;
    private BigDecimal money = new BigDecimal(0);
    private String outsideCode = "";
    private String remark = "";
    private int checked;
    private int auditingPeople;
    private Date auditingDate;
    private java.sql.Timestamp auditingTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getStockMonth() {
        return stockMonth;
    }

    public void setStockMonth(Integer stockMonth) {
        this.stockMonth = stockMonth;
    }

    public Integer getStockDetail() {
        return stockDetail;
    }

    public void setStockDetail(Integer stockDetail) {
        this.stockDetail = stockDetail;
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

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getOutsideCode() {
        return outsideCode;
    }

    public void setOutsideCode(String outsideCode) {
        this.outsideCode = outsideCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
