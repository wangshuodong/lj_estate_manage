package com.ym.iadpush.manage.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SysUsers implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String nick = "";

    private String username = "";

    private @JsonIgnore
    String password;

    private String email = "";

    private String qq = "";

    private Integer status;

    private Date updateDate;

    private Date createDate;

    private Double rate;

    private String phone = "";

    private Integer counts;

    private Date regeditTime;

    private String address = "";

    private String bankName = "";

    private String bankAddress = "";

    private String bankNo = "";

    private String certificate = "";

    private String certification = "0";

    private String type = "";

    private Integer serviceId;

    private String serviceName = "";

    private String roleName = "";

    private String roleType = "";

    private Integer roleStatus;

    private Integer roleId;

    private String province = "";

    private String city = "";

    private String bankUserName = "";

    private String assortment = "";

    private String ptpwd = "";

    private Integer departmentId;

    private String departmentCode = "";

    private String departmentName = "";

    private Integer member_levelId;

    private String desc;

    private String county;

    private String towns;

    private Integer companyId;

    private String companyName;

    private List<SysRoles> roles;

    private List<SysMenus> menus;

    private List<SysPosts> posts;
 
    private News news;
    
    private int saleUserId;
    

    public int getSaleUserId() {
        return saleUserId;
    }

    public void setSaleUserId(int saleUserId) {
        this.saleUserId = saleUserId;
    }

    private Integer login_count;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCounty() {
        return county;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Integer getLogin_count() {
        return login_count;
    }

    public void setLogin_count(Integer login_count) {
        this.login_count = login_count;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTowns() {
        return towns;
    }

    public void setTowns(String towns) {
        this.towns = towns;
    }

    public String getNick() {
        return nick;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getAssortment() {
        return assortment;
    }

    public void setAssortment(String assortment) {
        this.assortment = assortment;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getMember_levelId() {
        return member_levelId;
    }

    public void setMember_levelId(Integer member_levelId) {
        this.member_levelId = member_levelId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public String getUsername() {
        return username;
    }

    public String getQq() {
        return qq;
    }

    public String getRoleType() {
        return roleType;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Date getRegeditTime() {
        return regeditTime;
    }

    public void setRegeditTime(Date regeditTime) {
        this.regeditTime = regeditTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<SysRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRoles> roles) {
        this.roles = roles;
    }

    public List<SysMenus> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenus> menus) {
        this.menus = menus;
    }

    public List<SysPosts> getPosts() {
        return posts;
    }

    public void setPosts(List<SysPosts> posts) {
        this.posts = posts;
    }

    public Integer getRoleId() {
        return roleId;
    }


    public Integer getPostId() {
        if (posts != null && posts.size() > 0) {
            return posts.get(0).getPostId();
        }
        return null;
    }


    public Integer getParentRole() {
        if (roles != null && roles.size() > 0) {
            return roles.get(0).getParentRole();
        }
        return null;
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

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    public String getPtpwd() {
        return ptpwd;
    }

    public void setPtpwd(String ptpwd) {
        this.ptpwd = ptpwd;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
