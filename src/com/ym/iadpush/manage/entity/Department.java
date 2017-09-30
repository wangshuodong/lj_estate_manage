/**
 * 
 */
package com.ym.iadpush.manage.entity;

import java.io.Serializable;

public class Department implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String name;
    private String address;
    private String phone;
    private String bycode;
    private String contact_people;
    private int parentId;
    private String parentName = "";
    private String companyName = "";
    private int saleId = 0;

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact_people() {
        return contact_people;
    }

    public void setContact_people(String contact_people) {
        this.contact_people = contact_people;
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

    public String getContract_people() {
        return contact_people;
    }

    public void setContract_people(String contact_people) {
        this.contact_people = contact_people;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

}
