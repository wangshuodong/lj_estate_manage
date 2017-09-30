/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CurrentAccountCollect {
    private int id;
    private String month;
    private Integer companyId;
    private Timestamp lastUpdateTime;
    private String departmentCode;
    private Integer departmentId;
    private BigDecimal startMoney = new BigDecimal(0);
    private BigDecimal receMoney = new BigDecimal(0);
    private BigDecimal advancePayment = new BigDecimal(0);// 预收款
    private BigDecimal payMoney = new BigDecimal(0);
    private BigDecimal endMoney = new BigDecimal(0);
    private String companyName = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public BigDecimal getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(BigDecimal advancePayment) {
        this.advancePayment = advancePayment;
        compute();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public BigDecimal getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(BigDecimal startMoney) {
        this.startMoney = startMoney;
        compute();
    }

    public BigDecimal getReceMoney() {
        return receMoney;
    }

    public void setReceMoney(BigDecimal receMoney) {
        this.receMoney = receMoney;
        compute();
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
        compute();
    }

    public BigDecimal getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(BigDecimal endMoney) {
        this.endMoney = endMoney;
    }

    /**
     * 计算期末应收款
     * 
     * @Author lixingbiao 2014-8-7 下午12:46:22
     */
    private void compute() {
        BigDecimal temp = this.startMoney.add(this.receMoney);
        this.endMoney = temp.subtract(this.payMoney.add(this.advancePayment));
    }
}
