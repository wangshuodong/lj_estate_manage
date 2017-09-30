/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.util.Date;

import com.ym.common.utils.DateUtils;


public class VersionSdk {
    private Integer id;

    private String mid;

    private String sv;

    private String name;

    private Integer type;

    private Date create_time;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv == null ? null : sv.trim();
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public String getCreate_timeStr() {
        String str = DateUtils.formartRandomDate(create_time, "yyyy-MM-dd HH:mm:ss");
        return str;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

}
