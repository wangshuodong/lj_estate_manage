/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;


public class OrderStatus implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private java.sql.Timestamp createTime;
    private Integer createUid;
    private Integer status;
    private String statusName;
    private String desc;
    private Integer orderId;
    private String username;
    private String code = "";
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
            this.statusName = "工厂已接受订单，货物整理打包中";
        }

        if (statusInt == 4) {
            this.statusName = "产品已出库,物流中";
        }

        if (statusInt == 5) {
            this.statusName = "收货完毕";
        }

        this.status = status;
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

}
