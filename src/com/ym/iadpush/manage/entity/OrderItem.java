/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class OrderItem implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer orderId;
    private Integer number;
    private Integer productId;
    private BigDecimal money;
    private Date addate;
    private java.sql.Timestamp createTime;
    private Integer uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

}
