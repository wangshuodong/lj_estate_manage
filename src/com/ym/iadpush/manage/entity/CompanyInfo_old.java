/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.sql.Timestamp;

public class CompanyInfo_old {
    private int id;
    private int old_companyId;
    private String name = "";
    private String address = "";
    private String phone = "";
    private String contract_people = "";
    private String departmentCode = "";
    private int type;
    private int departmentId;
    private int parentId;
    private String bycode = "";
    private Timestamp createTime;
    private String qq = "";
    private String role_name = "";
    private String username = "";
    private String departmentName = "";
    private String province = "";
    private String city = "";
    private String county = "";
    private String towns = "";
    private String mobilePhone = "";
    private String salesman = "";
    private String parentCompanyName = "";
    private int parentCompanyId;
    private Integer createUid;
    private String shippingAddress = "";
    private String susername = "";

    public int getId() {
        return id;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public int getOld_companyId() {
        return old_companyId;
    }

    public void setOld_companyId(int old_companyId) {
        this.old_companyId = old_companyId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Integer getCreateUid() {
        return createUid;
    }

    public String getSusername() {
        return susername;
    }

    public void setSusername(String susername) {
        this.susername = susername;
    }

    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public int getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(int parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public void setParentCompanyName(String parentCompanyName) {
        if (parentCompanyName != null) {
            this.parentCompanyName = parentCompanyName;
        }
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTowns() {
        return towns;
    }

    public void setTowns(String towns) {
        this.towns = towns;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRole_name() {
        return role_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getName() {
        return name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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

    public String getContract_people() {
        return contract_people;
    }

    public void setContract_people(String contract_people) {
        this.contract_people = contract_people;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getBycode() {
        return bycode;
    }

    public void setBycode(String bycode) {
        this.bycode = bycode;
    }

}
