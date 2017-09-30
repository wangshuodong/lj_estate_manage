package com.ym.iadpush.manage.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SysConfig extends SysConfigKey implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private String cfgVal;
    private int sysid;

	private @JsonIgnore String remark;

    private @JsonIgnore String parentCfg;

    private @JsonIgnore String parentKey;

    private @JsonIgnore Integer status;

    private @JsonIgnore Integer sort;

    public int getSysid() {
		return sysid;
	}

	public void setSysid(int sysid) {
		this.sysid = sysid;
	}

	public String getCfgVal() {
        return cfgVal;
    }

    public void setCfgVal(String cfgVal) {
        this.cfgVal = cfgVal == null ? null : cfgVal.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getParentCfg() {
        return parentCfg;
    }

    public void setParentCfg(String parentCfg) {
        this.parentCfg = parentCfg == null ? null : parentCfg.trim();
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey == null ? null : parentKey.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}