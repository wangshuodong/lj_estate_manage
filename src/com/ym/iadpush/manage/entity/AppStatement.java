package com.ym.iadpush.manage.entity;

import com.ym.common.utils.Arith;

public class AppStatement {
    
    /**
     * 日期
     */
    private String date;
    
    /**
     * 新增
     */
    private int icount;
    
    /**
     * 活跃
     */
    private int acount;
    
    /**
     * 支出
     */
    private double earn;
    
    private int uid;
    
    private int appid;
    
    private int qid;
    
    private String username;
    
    private String appname;
    
    private String serviceId;
    
    private String serviceName;
    
    private String status;
    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIcount() {
        return icount;
    }

    public void setIcount(int icount) {
        this.icount = icount;
    }

    public int getAcount() {
        return acount;
    }

    public void setAcount(int acount) {
        this.acount = acount;
    }

    public String getEarn() {
        return Arith.getBigDecimalValue(earn, 2);
    }

    public void setEarn(double earn) {
        this.earn = earn;
    }

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
}
