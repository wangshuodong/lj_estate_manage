/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: lixingbiao 2017年8月2日 下午1:46:55 
 * @Version: $Id$
 * @Desc: <p></p>
 */
public class FaceToFacePay {
    private int ID;
    /**
     * 订单号
     */
    private String out_trade_no;
    /**
     * 付款码
     */
    private String auth_code;
    /**
     * 商品描述
     */
    private String subject;
    /**
     * 总额
     */
    private BigDecimal total_amount;
    /**
     * 生成时间
     */
    private Date time;
    /**
     * 小区ID
     */
    private int housingId;
    
    public String getAuth_code() {
        return auth_code;
    }
    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }
    public int getHousingId() {
        return housingId;
    }
    public void setHousingId(int housingId) {
        this.housingId = housingId;
    }
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getOut_trade_no() {
        return out_trade_no;
    }
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public BigDecimal getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    
}
