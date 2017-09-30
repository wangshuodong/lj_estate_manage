/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;

public class AccountDetailDataStatement {
    private BigDecimal receMoney=new BigDecimal(0);
    private BigDecimal payMoney=new BigDecimal(0);
    private BigDecimal advancePayment =new BigDecimal(0);//预收款
    
    public BigDecimal getReceMoney() {
        return receMoney;
    }

    public BigDecimal getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(BigDecimal advancePayment) {
        this.advancePayment = advancePayment;
    }

    public void setReceMoney(BigDecimal receMoney) {
        this.receMoney = receMoney;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

}
