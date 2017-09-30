/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;

public class StockMonthDataStatement {
    private BigDecimal enterNumber;
    private BigDecimal outNumber;

    public BigDecimal getEnterNumber() {
        return enterNumber;
    }

    public void setEnterNumber(BigDecimal enterNumber) {
        this.enterNumber = enterNumber;
    }

    public BigDecimal getOutNumber() {
        return outNumber;
    }

    public void setOutNumber(BigDecimal outNumber) {
        this.outNumber = outNumber;
    }

}
