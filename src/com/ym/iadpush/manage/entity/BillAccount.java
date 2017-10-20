/**
 * 
 */
package com.ym.iadpush.manage.entity;

/**
 * 账单
 * 
 * @Author: lixingbiao 2017-6-11 下午8:41:45
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public class BillAccount {
    private int id;
    private Integer departmentId;// 部门ID
    private String departmentCode = "";// 部门code
    private String departmentName = "";// 部门名称
    private String bill_entry_id;
    private int proprietorId;
    private String proprietorName;// 业主姓名
    private String month;
    private String release_day;
    private int state;
    private String acct_period;
    private String deadline;
    private String payDate;
    private String payType = "";
    private int payUid;
    private int createUid;
    private String roomAddress = "";
    private double bill_entry_amount = 0;
    private String out_room_id = "";
    private int sendStatus = 0;
    private int payStatus = 0;
    private String cost_type = "";
    private int deleteStatus = 0;
    private int housingId;
    private String accpetMoney;
    private String gmt_payment;
    private String trade_no;
    
    private double sumAmount;
    private int countNum;
    
    public String getAccpetMoney() {
        return accpetMoney;
    }

    public void setAccpetMoney(String accpetMoney) {
        this.accpetMoney = accpetMoney;
    }

    public int getHousingId() {
        return housingId;
    }

    public void setHousingId(int housingId) {
        this.housingId = housingId;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getCost_type() {
        return cost_type;
    }

    public void setCost_type(String cost_type) {
        this.cost_type = cost_type;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getOut_room_id() {
        return out_room_id;
    }

    public void setOut_room_id(String out_room_id) {
        this.out_room_id = out_room_id;
    }

    public double getBill_entry_amount() {
        return bill_entry_amount;
    }

    public void setBill_entry_amount(double bill_entry_amount) {
        this.bill_entry_amount = bill_entry_amount;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBill_entry_id() {
        return bill_entry_id;
    }

    public void setBill_entry_id(String bill_entry_id) {
        this.bill_entry_id = bill_entry_id;
    }

    public int getProprietorId() {
        return proprietorId;
    }

    public void setProprietorId(int proprietorId) {
        this.proprietorId = proprietorId;
    }

    public String getProprietorName() {
        return proprietorName;
    }

    public void setProprietorName(String proprietorName) {
        this.proprietorName = proprietorName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getRelease_day() {
        return release_day;
    }

    public void setRelease_day(String release_day) {
        this.release_day = release_day;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAcct_period() {
        return acct_period;
    }

    public void setAcct_period(String acct_period) {
        this.acct_period = acct_period;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getPayUid() {
        return payUid;
    }

    public void setPayUid(int payUid) {
        this.payUid = payUid;
    }

    public int getCreateUid() {
        return createUid;
    }

    public void setCreateUid(int createUid) {
        this.createUid = createUid;
    }

    public double getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(double sumAmount) {
        this.sumAmount = sumAmount;
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    public String getGmt_payment() {
        return gmt_payment;
    }

    public void setGmt_payment(String gmt_payment) {
        this.gmt_payment = gmt_payment;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

}
