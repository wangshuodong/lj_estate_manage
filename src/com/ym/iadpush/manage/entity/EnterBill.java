/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EnterBill implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Date addate;
    private java.sql.Timestamp createTime;
    private Integer warehouseId;
    private String warehouseName;
    private Integer productId;
    private String productName;
    private Integer stockMonth;
    private Integer stockDetail;
    private Integer uid;
    private Integer number;
    private BigDecimal price;
    private BigDecimal money;
    private String outsideCode = "";
    private String code = "";
    private String remark = "";
    private Integer departmentId;
    private Integer companyId;
    private String departmentCode = "";
    private String companyName = "";
    private int checked;
    private int auditingPeople;
    private Date auditingDate;
    private java.sql.Timestamp auditingTime;
    private String unit;
    private String auditingUsername;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getChecked() {
        return checked;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getOutsideCode() {
        return outsideCode;
    }

    public void setOutsideCode(String outsideCode) {
        this.outsideCode = outsideCode;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

}
