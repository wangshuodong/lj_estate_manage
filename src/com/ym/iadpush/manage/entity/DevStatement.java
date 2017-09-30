package com.ym.iadpush.manage.entity;

import com.ym.common.utils.Arith;


public class DevStatement {
    
    //用户名
    private String userName;
    
    //用户ID
    private int uid;
    
    //分成比例
    private double rate;
    
    //IMEI新增量
    private int icount;
    
    //IMEI活跃量
    private int acount;
    
    //收入
    private double earn;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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
        double d =  Double.valueOf(Arith.getBigDecimalValue(earn, 2));
        this.earn = d;
    }
    
    

}
