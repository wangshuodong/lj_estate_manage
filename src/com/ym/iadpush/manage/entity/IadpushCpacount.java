package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;
import java.util.Date;

public class IadpushCpacount {
    private Integer id;

    private Integer icount;

    private Integer acount;

    private Date adate;

    private Integer appid;

    private Integer qid;

    private Integer uid;

    private String appkey;

    private String packagename;

    private Short hour;

    private Integer icountkou;

    private Integer acountkou;

    private String platformtype;

    private Integer compensationicount;

    private Integer compensationacount;

    private BigDecimal icountkouAct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIcount() {
        return icount;
    }

    public void setIcount(Integer icount) {
        this.icount = icount;
    }

    public Integer getAcount() {
        return acount;
    }

    public void setAcount(Integer acount) {
        this.acount = acount;
    }

    public Date getAdate() {
        return adate;
    }

    public void setAdate(Date adate) {
        this.adate = adate;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey == null ? null : appkey.trim();
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename == null ? null : packagename.trim();
    }

    public Short getHour() {
        return hour;
    }

    public void setHour(Short hour) {
        this.hour = hour;
    }

    public Integer getIcountkou() {
        return icountkou;
    }

    public void setIcountkou(Integer icountkou) {
        this.icountkou = icountkou;
    }

    public Integer getAcountkou() {
        return acountkou;
    }

    public void setAcountkou(Integer acountkou) {
        this.acountkou = acountkou;
    }

    public String getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(String platformtype) {
        this.platformtype = platformtype == null ? null : platformtype.trim();
    }

    public Integer getCompensationicount() {
        return compensationicount;
    }

    public void setCompensationicount(Integer compensationicount) {
        this.compensationicount = compensationicount;
    }

    public Integer getCompensationacount() {
        return compensationacount;
    }

    public void setCompensationacount(Integer compensationacount) {
        this.compensationacount = compensationacount;
    }

    public BigDecimal getIcountkouAct() {
        return icountkouAct;
    }

    public void setIcountkouAct(BigDecimal icountkouAct) {
        this.icountkouAct = icountkouAct;
    }
}