package com.ym.iadpush.manage.action.tissue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.common.base.Pager;
import com.ym.common.utils.MD5Encrypt;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.CompanyInfo;
import com.ym.iadpush.manage.entity.CompanyInfo_old;
import com.ym.iadpush.manage.entity.Department;
import com.ym.iadpush.manage.entity.MemberLevel;
import com.ym.iadpush.manage.entity.SysRoles;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.SysUsers_old;
import com.ym.iadpush.manage.mapper.MemberLevelMapper;
import com.ym.iadpush.manage.services.companyinfo.ICompanyService;
import com.ym.iadpush.manage.services.department.IDepartmentService;
import com.ym.iadpush.manage.services.tissue.IRoleMgr;
import com.ym.iadpush.manage.services.tissue.IUserMgr;

@Controller
public class UserAction extends BaseAction {

    private @Autowired
    IUserMgr userService;
    private @Autowired
    IRoleMgr roleService;
    private @Autowired
    IDepartmentService departmentService;
    @Autowired
    private MemberLevelMapper memberLevelMapper;

    private @Autowired
    ICompanyService companyServiceImpl;

    private String packageName = "tissue";
    
    @ResponseBody
    @RequestMapping(value = "/deleteUser.html", method = RequestMethod.POST)
    public String deleteUser(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        String userId = request.getParameter("userId");

        Integer id = Integer.valueOf(userId);

        SysUsers sysUsers = userService.getByUserId(id);

        if (sysUsers != null) {

            saveSysUser_old(sysUsers);

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("username", sysUsers.getUsername());
            CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoByUsername(paramMap);

            if (companyInfo != null) {
                saveCompany_info_old(companyInfo);

                companyServiceImpl.deleteCompanyInfoByUsername(sysUsers.getUsername());
            }

            userService.deleteByUserRole(id);

            userService.deleteUser(id);

        }

        String result = "{success:true}";

        return result;

    }

    private void saveCompany_info_old(CompanyInfo companyInfo) {
        CompanyInfo_old companyInfo_old = new CompanyInfo_old();
        companyInfo_old.setOld_companyId(companyInfo.getId());
        companyInfo_old.setName(companyInfo.getName());
        companyInfo_old.setUsername(companyInfo.getUsername());
        companyInfo_old.setProvince(companyInfo.getProvince());
        companyInfo_old.setCity(companyInfo.getCity());
        companyInfo_old.setCounty(companyInfo.getCounty());
        companyInfo_old.setTowns(companyInfo.getTowns());
        companyInfo_old.setDepartmentCode(companyInfo.getDepartmentCode());
        companyInfo_old.setDepartmentId(companyInfo.getDepartmentId());
        companyInfo_old.setContract_people(companyInfo.getContract_people());
        companyInfo_old.setMobilePhone(companyInfo.getMobilePhone());
        companyInfo_old.setType(companyInfo.getType());
        companyInfo_old.setParentId(companyInfo.getParentId());
        companyInfo_old.setBycode(companyInfo.getBycode());
        companyInfo_old.setQq(companyInfo.getQq());
        companyInfo_old.setCreateUid(companyInfo.getCreateUid());
        companyInfo_old.setParentCompanyId(companyInfo.getParentCompanyId());
        companyInfo_old.setShippingAddress(companyInfo.getShippingAddress());
        companyServiceImpl.insertCompanyInfo_old(companyInfo_old);
    }

    private void saveSysUser_old(SysUsers sysUsers) {
        try {
            SysUsers_old sysUsers_old = new SysUsers_old();
            sysUsers_old.setRoleId(sysUsers.getRoleId());
            sysUsers_old.setOld_userId(sysUsers.getUserId());
            sysUsers_old.setUsername(sysUsers.getUsername());
            sysUsers_old.setNick(sysUsers.getNick());
            sysUsers_old.setEmail(sysUsers.getEmail());
            sysUsers_old.setDepartmentCode(sysUsers.getDepartmentCode());
            sysUsers_old.setPassword(sysUsers.getPassword());
            sysUsers_old.setPtpwd(sysUsers.getPtpwd());
            sysUsers_old.setDepartmentId(sysUsers.getDepartmentId());
            sysUsers_old.setPhone(sysUsers.getPhone());
            sysUsers_old.setAddress(sysUsers.getAddress());
            sysUsers_old.setStatus(sysUsers.getStatus());
            sysUsers_old.setUpdateDate(sysUsers.getUpdateDate());
            sysUsers_old.setCreateDate(sysUsers.getCreateDate());
            sysUsers_old.setQq(sysUsers.getQq());
            sysUsers_old.setProvince(sysUsers.getProvince());
            sysUsers_old.setCity(sysUsers.getCity());
            sysUsers_old.setCounty(sysUsers.getCounty());
            sysUsers_old.setTowns(sysUsers.getTowns());
            sysUsers_old.setCompanyId(sysUsers.getCompanyId());
            sysUsers_old.setType(sysUsers.getType());
            sysUsers_old.setCertification(sysUsers.getCertification());
            sysUsers_old.setDesc(sysUsers.getDesc());
            sysUsers_old.setLogin_count(sysUsers.getLogin_count());
            userService.insertSysUsers_old(sysUsers_old);
        } catch (Exception e) {

        }
    }
    @ResponseBody
    @RequestMapping(value = "/resetUserPasword.html", method = RequestMethod.POST)
    public String resetUserPasword(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        String userId = request.getParameter("userId");

        if (userId != null && !userId.equals("")) {
            userService.resetPassword(new Integer(userId));
        }

        String result = "{success:true}";

        return result;

    }

    @RequestMapping(value = "/user.html", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String result = AD_HTML + packageName + "/user";
        SysUsers user = getUser();

        List<SysRoles> roles = roleService.getRoles();

        List<Map<String, String>> services = null;
        if (getUser().getRoleType().equals("outService")) {
            services = new ArrayList<Map<String, String>>();
            Map temp = new HashMap();
            temp.put("userId", user.getUserId());
            temp.put("username", user.getUsername());
            services.add(temp);
        } else {
            services = userService.selectByType("outService");
        }
        model.put("roles", roles);
        model.put("services", services);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/query_all_user.html", method = RequestMethod.GET)
    public ModelMap queryAllUser(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        // 角色类型
        String assortment = user.getAssortment();
        String departmentCode = user.getDepartmentCode();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("departmentCode", departmentCode);

        // 终端门店时候，传入companyId
        if (assortment.equals("customer")) {
            params.put("companyId", user.getCompanyId());
        }

        QueryResult result = userService.queryAll(params);
        model.put("result", result.getData());
        model.put("totalRecord", result.getCount());

        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/queryUser.html", method = RequestMethod.POST)
    public Object queryUserList(HttpServletRequest request, ModelMap model) {
        // 页码
        String pageNo = request.getParameter("currPage");
        String companyName = request.getParameter("companyName");
        String departmentName = request.getParameter("departmentName");

        SysUsers user = getUser();

        // 角色类型
        String assortment = user.getAssortment();
        String departmentCode = user.getDepartmentCode();

        // 没传页码参数时候显示第一页
        if (pageNo == null || pageNo == "") {
            pageNo = "1";
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, Integer.parseInt(pageNo));

        // 返回结果map
        Map<String, Object> hashMap = new HashMap<String, Object>();

        // 查询参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("companyName", companyName);
        paramMap.put("departmentName", departmentName);

        // 终端门店时候，传入userId
        if (assortment.equals("customer")) {
            paramMap.put("userId", user.getUserId());
        }

        paramMap.put("username", getRequestParams(String.class, request, "username"));
        paramMap.put("roleId", getRequestParams(String.class, request, "roleId"));
        paramMap.put("status", getRequestParams(String.class, request, "status"));
        paramMap.put("certification", getRequestParams(String.class, request, "certification"));
        paramMap.put("startDate", getRequestParams(String.class, request, "startDate"));
        paramMap.put("endDate", getRequestParams(String.class, request, "endDate"));
        paramMap.put("pageSize", pageSize);
        paramMap.put("begRow", Integer.parseInt(pageNo)*10-10);

        List<SysUsers> list = userService.queryUsers(paramMap);

        // 总记录数
        int totalRecord = userService.countQueryUsers(paramMap);

        // 设置每页显示条数
        pager.setPageSize(pageSize);

        // 设置总记录数
        pager.setTotalRecord(totalRecord);

        // 合计页
        int totalPage = pager.getTotalPage();
        hashMap.put("totalPage", totalPage);

        // 明细数据
        hashMap.put("result", list);

        // 合计记录条数
        hashMap.put("totalRecord", totalRecord);

        // 当前页
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);

        return hashMap;
    }

    @RequestMapping(value = "/authorityRole.html", method = RequestMethod.GET)
    public String authorityRoleInit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        return AD_HTML + packageName + "/authorityRole";
    }

    @ResponseBody
    @RequestMapping(value = "/authorityRole.html", method = RequestMethod.POST)
    public Object queryAuthorityRole(HttpServletRequest request, ModelMap model) {
        // 页码
        String pageNo = request.getParameter("pageNo");

        // 没传页码参数时候显示第一页
        if (pageNo == null || pageNo == "") {
            pageNo = "1";
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, Integer.parseInt(pageNo));

        // 返回结果map
        Map<String, Object> hashMap = new HashMap<String, Object>();

        // 查询参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("username", getRequestParams(String.class, request, "username"));
        paramMap.put("rolename", getRequestParams(String.class, request, "rolename"));
        paramMap.put("menuname", getRequestParams(String.class, request, "menuname"));
        paramMap.put("pageSize", pageSize);
        paramMap.put("begRow", pager.getBegRow());

        List<Map<String, String>> list = userService.selectAuthorityRole(paramMap);
        // 总记录数
        int totalRecord = userService.selectAuthorityRoleCount(paramMap);
        // 设置每页显示条数
        pager.setPageSize(pageSize);

        // 设置总记录数
        pager.setTotalRecord(totalRecord);

        // 合计页
        int totalPage = pager.getTotalPage();
        hashMap.put("totalPage", totalPage);

        // 明细数据
        hashMap.put("result", list);

        // 合计记录条数
        hashMap.put("totalRecord", totalRecord);

        // 当前页
        hashMap.put("pageNo", pageNo);
        hashMap.put("pageSize", pageSize);

        return hashMap;
    }

    @RequestMapping(value = "/addUser.html", method = RequestMethod.GET)
    public String addUserInit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<SysRoles> roles = roleService.getRoles();
        List<Map<String, String>> services = userService.selectByType("outService");

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Department> departments = departmentService.getDepartmentByCode(paramMap);

        // 查询会员等级
        List<MemberLevel> levels = memberLevelMapper.queryAll();

        model.put("departments", departments);
        model.put("roles", roles);
        model.put("services", services);
        model.put("memberLevel", levels);

        return AD_HTML + packageName + "/user_add";
    }

    @ResponseBody
    @RequestMapping(value = "/addUser.html", method = RequestMethod.POST)
    public Object addUser(HttpServletRequest request, ModelMap model) {
        String departmentId = request.getParameter("department");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentId", Integer.valueOf(departmentId));
        Department department = departmentService.getDepartmentById(paramMap);

        SysUsers user = new SysUsers();
        user.setDepartmentId(Integer.parseInt(departmentId));
        user.setDepartmentCode(department.getCode());
        user.setNick(getRequestParams(String.class, request, "nick"));
        user.setUsername(getRequestParams(String.class, request, "username"));
        user.setEmail(getRequestParams(String.class, request, "email"));
        user.setQq(getRequestParams(String.class, request, "qq"));
        user.setPhone(getRequestParams(String.class, request, "phone"));
        Integer roleId = getRequestParams(Integer.class, request, "roleId");
        user.setAddress(getRequestParams(String.class, request, "address"));
        user.setBankName(getRequestParams(String.class, request, "bankName"));
        user.setBankAddress(getRequestParams(String.class, request, "bankAddress"));
        user.setBankUserName(getRequestParams(String.class, request, "bankUserName"));
        user.setBankNo(getRequestParams(String.class, request, "bankNo"));
        user.setCertificate(getRequestParams(String.class, request, "certificate"));
        user.setRegeditTime(new Date());
        user.setCertification("2");
        user.setType("other");
        user.setStatus(getRequestParams(Integer.class, request, "status"));
        user.setRate(getRequestParams(Double.class, request, "rate"));
        user.setCreateDate(new Date());
        Integer member_levelId = getRequestParams(Integer.class, request, "member_levelId");

        user.setMember_levelId(member_levelId);

        String services = getRequestParams(String.class, request, "service");

        if (StringUtils.isNotBlank(services)) {
            String service[] = services.split("[-]");
            if (service.length == 2) {
                user.setServiceId(Integer.parseInt(service[0]));
                user.setServiceName(service[1]);
            }
        }

        Map<String, Object> result = getResultMap();
        try {
            if (roleId == null || StringUtils.isBlank(user.getUsername())) {
                result.put(RESULT, false);
                result.put(MESSAGE, "提交信息不完整！");
            } else if (userService.getByName(user.getUsername()) != null) {
                result.put(RESULT, false);
                result.put(MESSAGE, "用户已存在！");

            } else if (userService.addUser(user, roleId) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "新增成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "新增失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常，操作失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }

    @RequestMapping(value = "/updUser.html", method = RequestMethod.GET)
    public String updUserInit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<SysRoles> roles = roleService.getRoles();

        SysUsers indexUser = getUser();

        List<SysRoles> lastRoles = new ArrayList<SysRoles>();

        SysUsers user = userService.getByUserId(getRequestParams(Integer.class, request, "userId"));

        // 角色类型
        String assortment = indexUser.getAssortment();

        // 大区域代理商可以管理地级市经销商与分销商
        if (assortment.equals("dqsale")) {
            for (SysRoles sysRoles : roles) {
                if (sysRoles.getAssortment().equals("djsale")) {
                    lastRoles.add(sysRoles);
                }
                if (sysRoles.getAssortment().equals("fxsale")) {
                    lastRoles.add(sysRoles);
                }
                if (sysRoles.getAssortment().equals("customer")) {
                    lastRoles.add(sysRoles);
                }
            }
        }

        // 地级代理商
        if (assortment.equals("djsale")) {
            for (SysRoles sysRoles : roles) {
                if (sysRoles.getAssortment().equals("customer")) {
                    lastRoles.add(sysRoles);
                }
                if (sysRoles.getAssortment().equals("fxsale")) {
                    lastRoles.add(sysRoles);
                }
                if (sysRoles.getAssortment().equals("djsale")) {
                    lastRoles.add(sysRoles);
                }
            }
        }

        if (assortment.equals("manger")) {
            for (SysRoles sysRoles : roles) {
                lastRoles.add(sysRoles);
            }
        }

        if (assortment.equals("customer")) {
            for (SysRoles sysRoles : roles) {
                if (sysRoles.getAssortment().equals("customer")) {
                    lastRoles.add(sysRoles);
                }
            }
        }

        if (assortment.equals("proxyFactory")) {
            for (SysRoles sysRoles : roles) {
                if (sysRoles.getAssortment().equals("proxyFactory")) {
                    lastRoles.add(sysRoles);
                }
            }
        }

        // 查询会员等级
        List<MemberLevel> levels = memberLevelMapper.queryAll();

        model.put("result", user);
        model.put("roles", lastRoles);
        model.put("memberLevel", levels);
        return AD_HTML + packageName + "/user_modify";
    }

    @ResponseBody
    @RequestMapping(value = "/updUser.html", method = RequestMethod.POST)
    public Object updUser(HttpServletRequest request, ModelMap model) {
        SysUsers user = new SysUsers();

        user.setUserId(getRequestParams(Integer.class, request, "userId"));
        user.setNick(getRequestParams(String.class, request, "nick"));
        user.setUsername(getRequestParams(String.class, request, "username"));
        user.setEmail(getRequestParams(String.class, request, "email"));
        user.setQq(getRequestParams(String.class, request, "qq"));
        user.setPhone(getRequestParams(String.class, request, "phone"));
        Integer roleId = getRequestParams(Integer.class, request, "roleId");
        user.setAddress(getRequestParams(String.class, request, "address"));
        user.setBankName(getRequestParams(String.class, request, "bankName"));
        user.setBankAddress(getRequestParams(String.class, request, "bankAddress"));
        user.setBankNo(getRequestParams(String.class, request, "bankNo"));
        user.setCertificate(getRequestParams(String.class, request, "certificate"));
        user.setCertification(getRequestParams(String.class, request, "certification"));
        user.setType(getRequestParams(String.class, request, "type"));
        user.setStatus(getRequestParams(Integer.class, request, "status"));
        user.setBankUserName(getRequestParams(String.class, request, "bankUserName"));
        user.setDepartmentCode(getRequestParams(String.class, request, "departmentCode"));
        user.setDepartmentId(getRequestParams(Integer.class,request,"departmentId"));
        user.setRate(getRequestParams(Double.class, request, "rate"));
        String services = getRequestParams(String.class, request, "service");
        String province = getRequestParams(String.class, request, "province");
        String city = getRequestParams(String.class, request, "city");
        Integer member_levelId = getRequestParams(Integer.class, request, "member_levelId");
        user.setMember_levelId(member_levelId);
        user.setProvince(province);
        user.setCity(city);
        if (StringUtils.isNotBlank(services)) {
            String service[] = services.split("[-]");
            if (service.length == 2) {
                user.setServiceId(Integer.parseInt(service[0]));
                user.setServiceName(service[1]);
            }
        }

        Map<String, Object> result = getResultMap();
        try {
            if (user.getUserId() == null || roleId == null) {
                result.put(RESULT, false);
                result.put(MESSAGE, "提交数据不完整！");
            } else if (userService.updateUser(user, roleId)) {
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

    @ResponseBody
    @RequestMapping(value = "/updStatus.html", method = RequestMethod.POST)
    public Object updStatus(HttpServletRequest request, ModelMap model) {
        Integer userId = getRequestParams(Integer.class, request, "userId");
        Integer status = getRequestParams(Integer.class, request, "status");

        Map<String, Object> result = getResultMap();
        if (userService.updateStatus(userId, status) != 0) {
            result.put(RESULT, true);
            result.put(MESSAGE, "操作成功！");
        } else {
            result.put(RESULT, false);
            result.put(MESSAGE, "操作失败！");
        }

        return result;
    }

    @RequestMapping(value = "/personal_info.html", method = RequestMethod.GET)
    public String personalInit(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        SysUsers sysUser = userService.getByUserId(getUser().getUserId());
        model.put("sysUser", sysUser);

        return AD_HTML + packageName + "/personal_info";
    }
    
    @ResponseBody
    @RequestMapping(value = "/getUserInfo.html", method = RequestMethod.POST)
    public Object getUserInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        SysUsers sysUser = userService.getByUserId(getUser().getUserId());
        model.put("sysUser", sysUser);
        return model;
    }
    
    @ResponseBody
    @RequestMapping(value = "/personal_info.html", method = RequestMethod.POST)
    public Object updPersonal(HttpServletRequest request, ModelMap model) {
        SysUsers user = new SysUsers();
        user.setUserId(getUser().getUserId());
        user.setEmail(getRequestParams(String.class, request, "email"));
        user.setQq(getRequestParams(String.class, request, "qq"));
        user.setPhone(getRequestParams(String.class, request, "phone"));
        user.setCertificate(getRequestParams(String.class, request, "certificate"));
        String oldPassword = getRequestParams(String.class, request, "oldPassword");
        String newPassword = getRequestParams(String.class, request, "newPassword");
        String confirmPassword = getRequestParams(String.class, request, "confirmPassword");
        boolean isOldPass = true;
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)
                && StringUtils.isNotBlank(confirmPassword)) {
            if (newPassword.equals(confirmPassword)) {
                if (getUser().getPassword().equals(MD5Encrypt.MD5(oldPassword))) {
                    user.setPassword(MD5Encrypt.MD5(newPassword));
                    user.setPtpwd(newPassword);
                    isOldPass = true;
                } else {
                    isOldPass = false;
                }
            }
        }

        Map<String, Object> result = getResultMap();
        try {
            if (!isOldPass) {
                result.put(RESULT, true);
                result.put(MESSAGE, "原密码错误！");
            } else if (userService.updateUser(user)) {
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
}
