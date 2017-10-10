package com.ym.iadpush.manage.entity;

import java.util.Date;

public class PrintInfo {
    private Integer id;

    private Integer departmentid;

    private String machineCode;

    private String msign;

    private String mobilephone;

    private String printname;
    
    private Integer status;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode == null ? null : machineCode.trim();
    }

    public String getMsign() {
        return msign;
    }

    public void setMsign(String msign) {
        this.msign = msign == null ? null : msign.trim();
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    public String getPrintname() {
        return printname;
    }

    public void setPrintname(String printname) {
        this.printname = printname == null ? null : printname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}