/**
 * 
 */
package com.ym.iadpush.manage.entity;


public class Account {

    private int id;

    private String bank = "";

    private String bankNo = "";

    private String name = "";

    private String bankName = "";

    private String bankAddress = "";

    private String contract_phone = "";

    private String contract_people = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getContract_phone() {
        return contract_phone;
    }

    public void setContract_phone(String contract_phone) {
        this.contract_phone = contract_phone;
    }

    public String getContract_people() {
        return contract_people;
    }

    public void setContract_people(String contract_people) {
        this.contract_people = contract_people;
    }

}
