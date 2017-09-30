/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.util.Date;

public class ExchangEapplyRecord implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer uid;
    private Date addate;
    private java.sql.Timestamp createTime;
    private Integer productId;
    private Integer newProductId;
    private Integer warehouseId;
    private String productName = "";
    private String newproductName = "";
    private Integer number;
    private Integer exchangNumber;
    private String code = "";
    private String outsideCode = "";
    private String remark = "";
    private Integer departmentId;
    private String departmentCode = "";
    private Integer companyId;
    private String companyName = "";
    private int status;
    private String statusName;
    private int checked;
    private int auditingPeople;
    private Date auditingDate;
    private java.sql.Timestamp auditingTime;
    private String unit = "";
    private String auditingUsername = "";
    private String deliverCode = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getChecked() {
        return checked;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getNewProductId() {
        return newProductId;
    }

    public void setNewProductId(Integer newProductId) {
        this.newProductId = newProductId;
    }

    public String getNewproductName() {
        return newproductName;
    }

    public void setNewproductName(String newproductName) {
        this.newproductName = newproductName;
    }

    public String getDeliverCode() {
        return deliverCode;
    }

    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        int statusInt = (int) status;

        if (statusInt == 0) {
            this.statusName = "处理中...";
        }

        if (statusInt == 1) {
            this.statusName = "已换货";
        }

        this.status = status;
    }

    public Integer getExchangNumber() {
        return exchangNumber;
    }

    public void setExchangNumber(Integer exchangNumber) {
        this.exchangNumber = exchangNumber;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getAuditingUsername() {
        return auditingUsername;
    }

    public void setAuditingUsername(String auditingUsername) {
        this.auditingUsername = auditingUsername;
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

    public Date getAddate() {
        return addate;
    }

    public String getCode() {
        return code;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRemark() {
        return remark;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAddate(Date addate) {
        this.addate = addate;
    }

    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public String getOutsideCode() {
        return outsideCode;
    }

    public void setOutsideCode(String outsideCode) {
        this.outsideCode = outsideCode;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
