/**
 * 
 */
package com.ym.iadpush.manage.entity;

/**
 * 楼栋
 * 
 * @Author: lixingbiao 2017-6-11 下午8:40:44
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public class Building {
    private Integer id;
    private Integer departmentId;
    private String departmentCode = "";
    private String departmentName = "";
    private String name = "";// 楼栋名称
    private String remark = "";// 备注
    private int houseId;// 小区ID
    private int createUid;
    private String houseName = "";// 小区名称

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public int getCreateUid() {
        return createUid;
    }

    public void setCreateUid(int createUid) {
        this.createUid = createUid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

}
