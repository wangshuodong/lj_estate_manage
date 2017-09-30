package com.ym.iadpush.dev.entity;

import com.ym.common.utils.Arith;


public class DevAppStatement {
    
    //应用名称
    private String appName;
    
    //安装量
    private String icount;
    
    //收入
    private String earn;

    
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIcount() {
        if(icount == null || icount.trim().equalsIgnoreCase("null")){
            icount = "0";
        }
        return icount;
    }

    public void setIcount(String icount) {
        String i = Arith.getBigDecimalValue(Double.valueOf(icount==null?"0":icount), 0);
        this.icount = i;
    }

    public String getEarn() {
        if(earn == null || earn.trim().equalsIgnoreCase("null")){
            earn = "0.00";
        }
        return earn;
    }

    public void setEarn(String earn) {
        String e = Arith.getBigDecimalValue(Double.valueOf(earn==null?"0":earn), 2);
        this.earn = e;
    }


    
    

}
