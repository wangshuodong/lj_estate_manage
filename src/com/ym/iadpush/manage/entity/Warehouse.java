/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;

public class Warehouse implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name = "";
    private String address = "";
    private String phone = "";
    private String contract_people = "";
    private String mobilePhone = "";
    private String city = "";
    private Integer companyId;
    private String companyName = "";
    private int uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

}
