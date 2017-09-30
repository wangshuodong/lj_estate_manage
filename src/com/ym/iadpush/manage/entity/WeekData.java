package com.ym.iadpush.manage.entity;

import com.ym.common.utils.Arith;


public class WeekData {
    
    /**
     * 开始日期
     */
    private String startDate;
    
    /**
     * 结束日期
     */
    private String endDate;
    
    /**
     * 支出
     */
    private Double earn = 0.00;
    
    /**
     * 新增
     */
    private int icount;
    
    /**
     * 活跃
     */
    private int acount;
    
    private int orderCount;
    
    private Double orderMoney = 0.00;
    
    private Double payMoney = 0.00;

    
    public String getEarn() {
        return Arith.getBigDecimalValue(earn, 2);
    }

    public void setEarn(Double earn) {
        if(earn == null){
            earn=0.00;
        }
        this.earn = earn;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderMoney() {
        return Arith.getBigDecimalValue(orderMoney, 2);
    }

    public void setOrderMoney(Double orderMoney) {
        if(orderMoney == null){
            orderMoney = 0.00;
        }
        this.orderMoney = orderMoney;
    }

    public String getPayMoney() {
        return Arith.getBigDecimalValue(payMoney, 2);
    }

    public void setPayMoney(Double payMoney) {
        if(payMoney == null){
            payMoney = 0.00;
        }
        this.payMoney = payMoney;
    }
    
    

}
