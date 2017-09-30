package com.ym.iadpush.dev.entity;

import com.ym.common.utils.Arith;

public class DevQuickReport {
    
    private Double today;
    
    private Double yestoday;
    
    private Double sevenday;
    
    private Double thismonth;
    
    private Double lastmonth;
    
    private Double all;

    public String getToday() {
        String d = "0.00";
        if(today != null){
            d = Arith.getBigDecimalValue(today, 2);
        }
        return d;
    }

    public void setToday(Double today) {
        this.today = today;
    }

    public String getYestoday() {
        String d = "0.00";
        if(yestoday != null){
            d = Arith.getBigDecimalValue(yestoday, 2);
        }
        return d;
    }

    public void setYestoday(Double yestoday) {
        this.yestoday = yestoday;
    }

    public String getSevenday() {
        String d = "0.00";
        if(sevenday != null){
            d = Arith.getBigDecimalValue(sevenday, 2);
        }
        return d;
    }

    public void setSevenday(Double sevenday) {
        this.sevenday = sevenday;
    }

    public String getThismonth() {
        String d = "0.00";
        if(thismonth != null){
            d = Arith.getBigDecimalValue(thismonth, 2);
        }
        return d;
    }

    public void setThismonth(Double thismonth) {
        this.thismonth = thismonth;
    }

    public String getLastmonth() {
        String d = "0.00";
        if(lastmonth != null){
            d = Arith.getBigDecimalValue(lastmonth, 2);
        }
        return d;
    }

    public void setLastmonth(Double lastmonth) {
        this.lastmonth = lastmonth;
    }

    public String getAll() {
        String d = "0.00";
        if(all != null){
            d = Arith.getBigDecimalValue(all, 2);
        }
        return d;
    }

    public void setAll(Double all) {
        this.all = all;
    }
    
    

}
