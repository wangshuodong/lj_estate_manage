package com.ym.iadpush.manage.mapper;

import java.util.List;

import com.ym.iadpush.manage.entity.SysRoles;

public interface SysRolesMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRoles record);

    int insertSelective(SysRoles record);

    SysRoles selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(SysRoles record);

    int updateByPrimaryKey(SysRoles record);

    List<SysRoles> getRoles();

    List<SysRoles> getAllRoles();

    String getRoleTree(Integer roleId);

    String getRoleChild(Integer roleId);

    String getRoleParent(Integer roleId);

    List<SysRoles> selectRoleByParent(String childRole);

    SysRoles selectByRoleName(String roleName);

    SysRoles getCustomerRole(String assortment);
}
