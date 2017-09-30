package com.ym.iadpush.manage.entity;

import java.io.Serializable;

public class SysRoles implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private String roleName;

    private Integer parentRole;

    private String parentRoleName;

    private Integer status;

    private String type;

    private String assortment;

    private String assortmentName;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getAssortment() {
        return assortment;
    }

    public String getAssortmentName() {
        return assortmentName;
    }

    public void setAssortmentName(String assortmentName) {
        this.assortmentName = assortmentName;
    }

    public void setAssortment(String assortment) {
        this.assortment = assortment;
        if (assortment.equals("manger")) {
            this.assortmentName = "经理";
        }
        if (assortment.equals("financial")) {
            this.assortmentName = "财务人员";
        }
        if (assortment.equals("dqsale")) {
            this.assortmentName = "大区域经销商";
        }
        if (assortment.equals("djsale")) {
            this.assortmentName = "地级市经销商";
        }
        if (assortment.equals("factory")) {
            this.assortmentName = "直属工厂";
        }
        if (assortment.equals("other")) {
            this.assortmentName = "行政及其他";
        }
        if (assortment.equals("customer")) {
            this.assortmentName = "终端门店";
        }
        if (assortment.equals("fxsale")) {
            this.assortmentName = "分销商";
        }
        if (assortment.equals("proxyFactory")) {
            this.assortmentName = "代理工厂";
        }
        if (assortment.equals("warehouse")) {
            this.assortmentName = "物流中心";
        }
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentRole() {
        return parentRole;
    }

    public void setParentRole(Integer parentRole) {
        this.parentRole = parentRole;
    }

    public String getParentRoleName() {
        return parentRoleName;
    }

    public void setParentRoleName(String parentRoleName) {
        this.parentRoleName = parentRoleName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
