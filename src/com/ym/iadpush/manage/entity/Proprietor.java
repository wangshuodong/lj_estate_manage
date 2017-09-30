/**
 * 
 */
package com.ym.iadpush.manage.entity;

/**
 * 业主
 * 
 * @Author: lixingbiao 2017-6-11 下午8:41:27
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
public class Proprietor {
    private int id;
    private Integer departmentId;
    private String departmentCode = "";
    private int housingId;// 小区ID
    private int buildingId;// 楼栋ID
    private int locationId;// 单元ID
    private String card = "";
    private String name = "";// 业主姓名
    private String entryDate = "";// 入住时间
    private String out_room_id = "";// 物业系统房屋编号
    private String room_id = "";// 支付宝房屋编号
    private String housingName = "";// 小区名称
    private String buildingName = "";// 楼栋名称
    private String locationName = "";// 单元名称
    private int roomInfoId;
    private String roomInfoAddress = "";
    private String room = "";
    private int createUid;
    private int entryStatus;
    private String remark = "";
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(int entryStatus) {
        this.entryStatus = entryStatus;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public int getCreateUid() {
        return createUid;
    }

    public void setCreateUid(int createUid) {
        this.createUid = createUid;
    }

    public String getOut_room_id() {
        return out_room_id;
    }

    public void setOut_room_id(String out_room_id) {
        this.out_room_id = out_room_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getHousingName() {
        return housingName;
    }

    public void setHousingName(String housingName) {
        this.housingName = housingName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getRoomInfoId() {
        return roomInfoId;
    }

    public void setRoomInfoId(int roomInfoId) {
        this.roomInfoId = roomInfoId;
    }

    public String getRoomInfoAddress() {
        return roomInfoAddress;
    }

    public void setRoomInfoAddress(String roomInfoAddress) {
        this.roomInfoAddress = roomInfoAddress;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getHousingId() {
        return housingId;
    }

    public void setHousingId(int housingId) {
        this.housingId = housingId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

}
