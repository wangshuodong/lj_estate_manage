package com.ym.iadpush.manage.services.tissue.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.common.base.BeansManager;
import com.ym.iadpush.manage.entity.SysRoleMenuKey;
import com.ym.iadpush.manage.entity.SysRoles;
import com.ym.iadpush.manage.mapper.SysRoleMenuMapper;
import com.ym.iadpush.manage.mapper.SysRolesMapper;
import com.ym.iadpush.manage.services.tissue.IRoleMgr;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class RoleMgrSv implements IRoleMgr {

    private @Autowired
    SysRolesMapper roleDao;
    private @Autowired
    SysRoleMenuMapper roleMenuDao;

    public List<SysRoles> getAllRoles() {
        return roleDao.getAllRoles();
    }

    public List<SysRoles> getRoles() {
        return roleDao.getRoles();
    }

    public int addRole(SysRoles role) {
        return roleDao.insert(role);
    }

    public int updRole(SysRoles role) {
        return roleDao.updateByPrimaryKey(role);
    }

    public int delRole(Integer roleId) {
        return roleDao.deleteByPrimaryKey(roleId);
    }

    public List<SysRoleMenuKey> getRoleMenus(Integer roleId) {
        return roleMenuDao.selectByRoleId(roleId);
    }

    public int changeRoleMenu(SysRoleMenuKey roleMenu, boolean checked) {
        if (checked) {
            return roleMenuDao.insert(roleMenu);
        } else {
            return roleMenuDao.deleteByPrimaryKey(roleMenu);
        }
    }

    @Override
    public List<SysRoles> getRoleByParent(Integer roleId) {
        return roleDao.selectRoleByParent(getRoleChild(roleId));
    }


    public static String getRoleChild(int roleId) {
        return BeansManager.getBean(SysRolesMapper.class).getRoleChild(roleId);
    }


    public static String getRoleParent(int roleId) {
        return BeansManager.getBean(SysRolesMapper.class).getRoleParent(roleId);
    }


    public static String getRoleTree(int roleId) {
        return BeansManager.getBean(SysRolesMapper.class).getRoleTree(roleId);
    }

    public SysRoles selectByRoleName(String roleName) {
        return roleDao.selectByRoleName(roleName);
    }

    public SysRoles selectByPrimaryKey(Integer roleId) {
        return roleDao.selectByPrimaryKey(roleId);
    }

    public List<Map<String, String>> selectMenuByRoleId(Integer roleId) {
        return roleMenuDao.selectMenuByRoleId(roleId);
    }

    public List<Map<String, String>> selectAllMenus() {
        return roleMenuDao.selectAllMenus();
    }

    public boolean assign(Integer roleId, String[] menus) {
        roleMenuDao.deleteById(roleId);
        SysRoleMenuKey roleMenu = null;
        List<SysRoleMenuKey> roleMenuList = null;

        if (menus != null && menus.length > 0) {
            roleMenuList = new ArrayList<SysRoleMenuKey>();
            for (String menu : menus) {
                roleMenu = new SysRoleMenuKey();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(Integer.parseInt(menu));
                roleMenuList.add(roleMenu);
            }

            if (roleMenuDao.batchInsert(roleMenuList) != 0) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    public List<Map<String, String>> selectMenusByType(String type) {
        return roleMenuDao.selectMenusByType(type);
    }

    @Override
    public SysRoles getCustomerRole(String assortment) {
        return roleDao.getCustomerRole(assortment);
    }

}
