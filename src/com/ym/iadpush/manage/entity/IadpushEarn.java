package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;
import java.util.Date;

public class IadpushEarn {
    private Integer id;

    private Integer uid;

    private Integer qid;

    private Integer appid;

    private Date edate;

    private BigDecimal earnMoney;

    private Integer status;

    private Date createTime;

    private Short hour;

    private Integer type;

    private Integer icount;

    private Integer icountkou;

    private Integer acount;

    private Integer acountkou;

    private BigDecimal icountkouAct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public BigDecimal getEarnMoney() {
        return earnMoney;
    }

    public void setEarnMoney(BigDecimal earnMoney) {
        this.earnMoney = earnMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getHour() {
        return hour;
    }

    public void setHour(Short hour) {
        this.hour = hour;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIcount() {
        return icount;
    }

    public void setIcount(Integer icount) {
        this.icount = icount;
    }

    public Integer getIcountkou() {
        return icountkou;
    }

    public void setIcountkou(Integer icountkou) {
        this.icountkou = icountkou;
    }

    public Integer getAcount() {
        return acount;
    }

    public void setAcount(Integer acount) {
        this.acount = acount;
    }

    public Integer getAcountkou() {
        return acountkou;
    }

    public void setAcountkou(Integer acountkou) {
        this.acountkou = acountkou;
    }

    public BigDecimal getIcountkouAct() {
        return icountkouAct;
    }

    public void setIcountkouAct(BigDecimal icountkouAct) {
        this.icountkouAct = icountkouAct;
    }
}