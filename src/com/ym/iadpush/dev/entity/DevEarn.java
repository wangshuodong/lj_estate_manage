package com.ym.iadpush.dev.entity;

import com.ym.common.utils.Arith;

public class DevEarn {
    
    //已结算广告收入
    private Double settledEarn;
    
    //未结算广告收入
    private Double unsettledEarn;
    
    //已结算奖金收入
    private Double settledAward;
    
    //未结算奖金收入
    private Double unsettledAward;

    public String getSettledEarn() {
        
        String se = "0.00";
        if(settledEarn != null){
            se = Arith.getBigDecimalValue(settledEarn, 2);
        }
        return se;
    }

    public void setSettledEarn(Double settledEarn) {
        this.settledEarn = settledEarn;
    }

    public String getUnsettledEarn() {
        String ue = "0.00";
        if(unsettledEarn != null){
            ue = Arith.getBigDecimalValue(unsettledEarn, 2);
        }
        return ue;
    }

    public void setUnsettledEarn(Double unsettledEarn) {
        this.unsettledEarn = unsettledEarn;
    }

    public String getSettledAward() {
        String sa = "0.00";
        if(settledAward != null){
            sa = Arith.getBigDecimalValue(settledAward, 2);
        }
        return sa;
    }

    public void setSettledAward(Double settledAward) {
        this.settledAward = settledAward;
    }

    public String getUnsettledAward() {
        String ua = "0.00";
        if(unsettledAward != null){
            ua = Arith.getBigDecimalValue(unsettledAward, 2);
        }
        return ua;
    }

    public void setUnsettledAward(Double unsettledAward) {
        this.unsettledAward = unsettledAward;
    }

    
    
}
