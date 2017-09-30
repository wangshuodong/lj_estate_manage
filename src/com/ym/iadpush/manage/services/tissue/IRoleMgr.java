package com.ym.iadpush.manage.services.tissue;

import java.util.List;
import java.util.Map;

import com.ym.common.base.Pager;
import com.ym.iadpush.manage.entity.SysRoleMenuKey;
import com.ym.iadpush.manage.entity.SysRoles;

public interface IRoleMgr {

 
    public List<SysRoles> getAllRoles();

    public List<SysRoles> getRoles();

    
    public int addRole(SysRoles role);

   
    public int updRole(SysRoles role);

   
    public int delRole(Integer roleId);

   
    public List<SysRoleMenuKey> getRoleMenus(Integer roleId);

    public int changeRoleMenu(SysRoleMenuKey roleMenu, boolean checked);

   
    public List<SysRoles> getRoleByParent(Integer roleId);
    
    SysRoles selectByRoleName(String roleName);
    
    SysRoles selectByPrimaryKey(Integer roleId);
    
    List<Map<String,String>> selectMenuByRoleId(Integer roleId);
    
    List<Map<String,String>> selectAllMenus();
    
    List<Map<String,String>> selectMenusByType(String type);
    
    boolean assign(Integer roleId,String[] menus);

  
     SysRoles getCustomerRole(String assortment);
}
