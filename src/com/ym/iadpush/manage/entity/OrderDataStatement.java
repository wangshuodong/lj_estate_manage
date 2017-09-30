/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;

public class OrderDataStatement {
    private BigDecimal money;
    private Integer number;
    private BigDecimal payMoney;
    private BigDecimal endNumber;

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public Integer getNumber() {
        return number;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public BigDecimal getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(BigDecimal endNumber) {
        this.endNumber = endNumber;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
