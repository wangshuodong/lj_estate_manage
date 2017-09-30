/**
 * 
 */
package com.ym.iadpush.manage.entity;

/**
 * 小区
 * 
 * @Author: lixingbiao 2017-6-11 下午8:40:29
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public class Housing {
    private Integer id;
    private String name = "";
    private String address = "";
    private String phone = "";
    private String contractPeople = "";
    private Integer departmentId;
    private String departmentCode = "";
    private String payAccount = "";
    private String pid = "";// 商户PID
    private String departmentName = "";
    private String remark = "";
    private int createUid;
    private String community_address = "";
    private String province_code = "";
    private String city_code = "";
    private String district_code = "";
    private String community_locations = "";
    private String community_id = "";
    private String private_key = "";
    private String alipay_public_key = "";
    private String app_id = "";// 应用ID
    private String token = "";
    private int init = 0;
    private int saleId = 0;
    private String saleName = "";
    private int smsCount;
    
    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getInit() {
        return init;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

    public String getCommunity_address() {
        return community_address;
    }

    public void setCommunity_address(String community_address) {
        this.community_address = community_address;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getCommunity_locations() {
        return community_locations;
    }

    public void setCommunity_locations(String community_locations) {
        this.community_locations = community_locations;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public int getCreateUid() {
        return createUid;
    }

    public void setCreateUid(int createUid) {
        this.createUid = createUid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContractPeople() {
        return contractPeople;
    }

    public void setContractPeople(String contractPeople) {
        this.contractPeople = contractPeople;
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

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
