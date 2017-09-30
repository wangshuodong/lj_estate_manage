package com.ym.iadpush.manage.action.tissue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ym.common.base.BaseAction;
import com.ym.common.utils.JacksonBuilder;
import com.ym.iadpush.manage.entity.SysRoleMenuKey;
import com.ym.iadpush.manage.entity.SysRoles;
import com.ym.iadpush.manage.services.tissue.IRoleMgr;

@Controller
public class RoleAction extends BaseAction {
    private @Autowired
    IRoleMgr roleService;

    @RequestMapping(value = "/role.html", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap model) {
        List<SysRoles> roleList = roleService.getAllRoles();
        model.put("result", roleList);
        return AD_HTML + "tissue/role";
    }
    
    @ResponseBody
    @RequestMapping(value = "/roleList.html", method = RequestMethod.POST)
    public Object getRole(HttpServletRequest request, ModelMap model) {
        List<SysRoles> roleList = roleService.getAllRoles();
        model.put("list", roleList);
        return model;
    }
    @RequestMapping(value = "/addRole.html", method = RequestMethod.GET)
    public String addRoleInit(HttpServletRequest request, ModelMap model) {

        return AD_HTML + "tissue/role_add";
    }

    @ResponseBody
    @RequestMapping(value = "/addRole.html", method = RequestMethod.POST)
    public Object addRole(HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        String roleName = getRequestParams(String.class, request, "roleName");
        String assortment = request.getParameter("assortment");
        Integer status = getRequestParams(Integer.class, request, "status");

        try {
            if (StringUtils.isBlank(roleName)) {
                result.put(RESULT, false);
                result.put(MESSAGE, "角色名称不能为空！");
            } else if (roleService.selectByRoleName(roleName.trim()) != null) {
                result.put(RESULT, false);
                result.put(MESSAGE, "该角色名称已经存在！");
            } else {
                SysRoles role = new SysRoles();
                role.setAssortment(assortment);
                role.setRoleName(roleName.trim());
                role.setStatus(status);
                role.setParentRole(0);
                role.setType("other");
                if (roleService.addRole(role) != 0) {
                    result.put(RESULT, true);
                    result.put(MESSAGE, "新增成功！");
                } else {
                    result.put(RESULT, false);
                    result.put(MESSAGE, "新增失败！");
                }
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，操作失败！");
            getLog(this).error(e.getMessage(), e);
        }

        return result;
    }

    @RequestMapping(value = "/updRole.html", method = RequestMethod.GET)
    public String updRoleInit(HttpServletRequest request, ModelMap model) {
        List<SysRoles> roleList = roleService.getAllRoles();
        model.put("result", roleList);

        Integer roleId = getRequestParams(Integer.class, request, "roleId");
        SysRoles role = roleService.selectByPrimaryKey(roleId);
        model.put("role", role);

        return AD_HTML + "tissue/role_modify";
    }

    @ResponseBody
    @RequestMapping(value = "/updRole.html", method = RequestMethod.POST)
    public Object updRole(HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        SysRoles role = new SysRoles();
        role.setRoleId(getRequestParams(Integer.class, request, "roleId"));
        role.setRoleName(getRequestParams(String.class, request, "roleName"));
        role.setStatus(getRequestParams(Integer.class, request, "status"));
        Integer parentRole = getRequestParams(Integer.class, request, "parentRole");
        role.setParentRole(parentRole == null ? 0 : parentRole);
        role.setType(getRequestParams(String.class, request, "type"));

        try {
            if (role.getRoleId() == null || StringUtils.isBlank(role.getRoleName())) {
                result.put(RESULT, false);
                result.put(MESSAGE, "角色名称不能为空！");
            } else if (roleService.updRole(role) != 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "修改成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "修改失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，操作失败！");
            getLog(this).error(e.getMessage(), e);
        }

        return result;
    }

    @RequestMapping(value = "assign_othermenu.html", method = RequestMethod.GET)
    public String assignOtherMenu(HttpServletRequest request, ModelMap model) {
        try {
            model.put("othermenusJson",
                    JacksonBuilder.mapper().writeValueAsString(roleService.selectMenusByType("other")));
            List<SysRoles> devRoles = new ArrayList<SysRoles>();
            for (SysRoles r : roleService.getAllRoles()) {
                if (!r.getType().equals("dev")) {
                    devRoles.add(r);
                }
            }
            model.put("rolesJson", JacksonBuilder.mapper().writeValueAsString(devRoles));
        } catch (JsonProcessingException e) {
            getLog(this).error(e.getMessage(), e);
        }
        return AD_HTML + "tissue/assign_othermenu";
    }

    @RequestMapping(value = "assign_devmenu.html", method = RequestMethod.GET)
    public String assignDevMenu(HttpServletRequest request, ModelMap model) {
        try {
            model.put("devmenusJson", JacksonBuilder.mapper().writeValueAsString(roleService.selectMenusByType("dev")));
            List<SysRoles> otherRoles = new ArrayList<SysRoles>();
            for (SysRoles r : roleService.getAllRoles()) {
                if (r.getType().equals("dev")) {
                    otherRoles.add(r);
                }
            }
            model.put("rolesJson", JacksonBuilder.mapper().writeValueAsString(otherRoles));
        } catch (JsonProcessingException e) {
            getLog(this).error(e.getMessage(), e);
        }
        return AD_HTML + "tissue/assign_devmenu";
    }

    @ResponseBody
    @RequestMapping(value = "menu_checked.html", method = RequestMethod.POST)
    public Object roleMenus(SysRoleMenuKey roleMenu, Boolean checked, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (checked != null && roleMenu.getRoleId() != null && roleMenu.getMenuId() != null) {
                result.put(RESULT, true);
                roleService.changeRoleMenu(roleMenu, checked);
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "参数错误，获取数据失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，获取数据失败！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "role_menus.html", method = RequestMethod.POST)
    public Object roleMenus(Integer roleId, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (roleId != null) {
                result.put(RESULT, true);
                result.put(DATA, roleService.getRoleMenus(roleId));
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "参数错误，获取数据失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，获取数据失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }
}
