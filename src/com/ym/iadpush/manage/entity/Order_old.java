/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Order_old implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer old_orderId;
    private Date addate;
    private java.sql.Timestamp createTime;
    private Integer createUid;
    private String productName = "";
    private Integer number;
    private BigDecimal money;
    private Integer endNumber;
    private BigDecimal endMoney;
    private Integer outNumber;
    private Integer status;
    private String statusName = "";
    private String departmentCode = "";
    private Integer productId;
    private BigDecimal payMoney;
    private Date paydate = null;
    private Integer companyId;
    private String no;
    private Integer payUid;
    private BigDecimal price;
    private String unit = "";
    private BigDecimal weight;
    private String departmentName = "";
    private BigDecimal rate;
    private Integer departmentId;
    private String companyName = "";
    private String remark = "";
    private String payRemark = "";
    private int propertyId;
    private String color = "";
    private String thickness = "";
    private String shippingAddress = "";
    private String code = "";

    public Integer getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getOld_orderId() {
        return old_orderId;
    }

    public void setOld_orderId(Integer old_orderId) {
        this.old_orderId = old_orderId;
    }

    public BigDecimal getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(BigDecimal endMoney) {
        this.endMoney = endMoney;
    }

    public Integer getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Integer endNumber) {
        this.endNumber = endNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getPayRemark() {
        return payRemark;
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCompanyName(String companyName) {
        if (companyName != null) {
            this.companyName = companyName;
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPayUid() {
        return payUid;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPayUid(Integer payUid) {
        this.payUid = payUid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Date getAddate() {
        return addate;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        int statusInt = (int) status;

        if (statusInt == 0) {
            this.statusName = "待付款";
        }

        if (statusInt == 1) {
            this.statusName = "已付款,财务审核中";
        }

        if (statusInt == 2) {
            this.statusName = "财务审核完毕,订单已传递到工厂";
        }

        if (statusInt == 3) {
            this.statusName = "工厂已接收订单，货物整理打包中";
        }

        if (statusInt == 4) {
            this.statusName = "产品已出库,物流中";
        }

        if (statusInt == 5) {
            this.statusName = "收货完毕";
        }

        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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

    public Integer getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getOutNumber() {
        this.outNumber = this.number - this.endNumber;
        return this.outNumber;
    }
}
