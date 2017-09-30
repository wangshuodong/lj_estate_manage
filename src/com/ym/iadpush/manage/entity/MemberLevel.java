/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;


public class MemberLevel {

    private Integer id;

    // 会员等级
    private Integer level;

    // 会员秒速
    private String desc;

    // 折扣i
    private BigDecimal rate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

}
