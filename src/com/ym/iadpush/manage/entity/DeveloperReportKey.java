package com.ym.iadpush.manage.entity;

import java.util.Date;

public class DeveloperReportKey {
    private Date addate;

    private Integer uid;

    private Integer appid;

    public Date getAddate() {
        return addate;
    }

    public void setAddate(Date addate) {
        this.addate = addate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }
}