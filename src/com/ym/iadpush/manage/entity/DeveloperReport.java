package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;
import java.util.Date;

public class DeveloperReport extends DeveloperReportKey {
    private Integer operUid;

    private String username;

    private Integer icount;

    private Integer acount;

    private BigDecimal earn;

    private Date lastUpdate;

    private Integer icountkou;

    private Integer acountkou;

    private BigDecimal icountkouAct;

    public Integer getOperUid() {
        return operUid;
    }

    public void setOperUid(Integer operUid) {
        this.operUid = operUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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

    public BigDecimal getEarn() {
        return earn;
    }

    public void setEarn(BigDecimal earn) {
        this.earn = earn;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
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

    public BigDecimal getIcountkouAct() {
        return icountkouAct;
    }

    public void setIcountkouAct(BigDecimal icountkouAct) {
        this.icountkouAct = icountkouAct;
    }
}