package com.ym.iadpush.manage.entity;

import com.ym.common.utils.Arith;

public class DataStatement {
    
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

    
}
