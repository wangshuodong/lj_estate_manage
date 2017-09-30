/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class OutBill implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Date addate;
    private java.sql.Timestamp createTime;
    private Integer warehouseId = 0;
    private String warehouseName = "";
    private Integer productId = 0;
    private Integer orderId = 0;
    private String productName = "";
    private Integer stockMonth = 0;
    private Integer stockDetail = 0;
    private Integer uid = 0;
    private Integer number = 0;
    private BigDecimal price;
    private BigDecimal money;
    private String outsideCode = "";
    private String code = "";
    private String remark = "";
    private Integer departmentId;
    private Integer companyId;
    private String departmentCode = "";
    private String companyName = "";
    private String unit="";
    private String orderCode="";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Date getAddate() {
        return addate;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getProductName() {
        return productName;
    }

    public String getOutsideCode() {
        return outsideCode;
    }

    public void setOutsideCode(String outsideCode) {
        this.outsideCode = outsideCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
