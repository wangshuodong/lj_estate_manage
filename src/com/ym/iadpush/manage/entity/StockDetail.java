/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.util.Date;

public class StockDetail implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Date addate;
    private java.sql.Timestamp createTime;
    private String month;
    private Integer warehouseId;
    private String warehouseName;
    private Integer productId;
    private String productName;
    private Integer startNumber = 0;
    private Integer enterNumber = 0;
    private Integer outNumber = 0;
    private Integer endNumber = 0;
    private Integer proProperty = 0;
    private Integer departmentId = 0;
    private String departmentCode = "";
    private String unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAddate() {
        return addate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setAddate(Date addate) {
        this.addate = addate;
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

    public String getWarehouseName() {
        return warehouseName;
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

    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public Integer getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Integer startNumber) {
        this.startNumber = startNumber;
        computeNumber();
    }

    public Integer getEnterNumber() {
        return enterNumber;
    }

    public void setEnterNumber(Integer enterNumber) {
        this.enterNumber = enterNumber;
        computeNumber();
    }

    public Integer getOutNumber() {
        return outNumber;
    }

    public void setOutNumber(Integer outNumber) {
        this.outNumber = outNumber;
        computeNumber();
    }

    public Integer getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Integer endNumber) {
        this.endNumber = endNumber;
    }

    public Integer getProProperty() {
        return proProperty;
    }

    public void setProProperty(Integer proProperty) {
        this.proProperty = proProperty;
    }

    private void computeNumber() {
        this.endNumber = this.startNumber + this.enterNumber - this.outNumber;
    }
}
