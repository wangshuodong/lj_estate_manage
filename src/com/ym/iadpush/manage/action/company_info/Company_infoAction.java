/**
 * 
 */
package com.ym.iadpush.manage.action.company_info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.common.base.Pager;
import com.ym.iadpush.common.utils.FormatUtil;
import com.ym.iadpush.manage.entity.CompanyInfo;
import com.ym.iadpush.manage.entity.Department;
import com.ym.iadpush.manage.entity.SysRoles;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.companyinfo.ICompanyService;
import com.ym.iadpush.manage.services.department.IDepartmentService;
import com.ym.iadpush.manage.services.tissue.IRoleMgr;
import com.ym.iadpush.manage.services.tissue.IUserMgr;

/**
 * @Version: $Id$
 * @Desc: <p>
 * </p>
 */
@Controller
public class Company_infoAction extends BaseAction {

    private @Autowired
    ICompanyService companyServiceImpl;
    private @Autowired
    IUserMgr userService;
    private @Autowired
    IRoleMgr roleService;
    private @Autowired
    IDepartmentService departmentService;
    private String packageName = "company_info";

    @ResponseBody
    @RequestMapping(value = "/quyer_all_company.html", method = RequestMethod.GET)
    public ModelMap quyer_all_company(HttpServletRequest request, ModelMap model) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currPage", 0);
        params.put("pageSize", Integer.MAX_VALUE);

        List<CompanyInfo> lastList = new ArrayList<CompanyInfo>();
        List<CompanyInfo> listCustomers = companyServiceImpl.getAllCustomers(params);
        List<CompanyInfo> listSales = companyServiceImpl.getAllSales(params);
        List<CompanyInfo> listProxyFactorys = companyServiceImpl.getAllProxyFactorys(params);

        lastList.addAll(listCustomers);
        lastList.addAll(listSales);
        lastList.addAll(listProxyFactorys);
        

        model.put("result", lastList);
        model.put("totalRecord", lastList.size());
        
        return model;

    }

    @RequestMapping(value = "/customer.html", method = RequestMethod.GET)
    public Object customer(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        // 角色类型
        String assortment = user.getAssortment();
        List<CompanyInfo> list = new ArrayList<CompanyInfo>();

        if (assortment.equals("other") || assortment.equals("manger")) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("type", new Integer(0));
            paramMap.put("assortment", "djsale");
            paramMap.put("currPage", 0);
            paramMap.put("pageSize", Integer.MAX_VALUE);

            list = companyServiceImpl.getAllSales(paramMap);
            model.put("companyInfos", list);
            return AD_HTML + packageName + "/customersOther";
        }

        if (assortment.equals("djsale")) {
            model.put("companyInfos", list);
            return AD_HTML + packageName + "/customers";
        }
        model.put("companyInfos", list);
        return AD_HTML + packageName + "/customers";

    }

    @RequestMapping(value = "/initUpdateCustomerInfo.html", method = RequestMethod.GET)
    public Object initUpdateCustomerInfo(HttpServletRequest request, ModelMap model) {
        String companyId = request.getParameter("companyId");

        CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoById(new Integer(companyId));

        if (companyInfo == null) {
            companyInfo = new CompanyInfo();
        }

        model.put("company", companyInfo);

        return AD_HTML + packageName + "/initUpdateCustomerInfo";
    }

    @ResponseBody
    @RequestMapping(value = "/updateCustomerInfo.html", method = RequestMethod.POST)
    public Object updateCustomerInfo(HttpServletRequest request, ModelMap model) {

        String companyId = request.getParameter("companyId");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String towns = request.getParameter("towns");
        String mobilePhone = request.getParameter("mobilePhone");
        String contract_people = request.getParameter("contract_people");
        String name = request.getParameter("companyName");
        String username = request.getParameter("username");

        if (name == null || name.equals("")) {
            model.put("status", true);
            model.put("msg", "门店信息修改失败,门店名称不能为空！");
            return model;
        }

        if (username == null || username.equals("")) {
            model.put("status", true);
            model.put("msg", "门店信息修改失败,用户名不能为空！");
            return model;
        }

        CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoById(new Integer(companyId));

        if (companyInfo != null) {
            SysUsers sysUsers = userService.getUserByCompanyId(companyInfo.getId());
            if (sysUsers != null) {
                sysUsers.setUsername(username);
                sysUsers.setAddress(address);
                sysUsers.setPhone(phone);
                sysUsers.setProvince(province);
                sysUsers.setCity(city);
                sysUsers.setCounty(county);
                sysUsers.setTowns(towns);
                userService.updateUser(sysUsers);
            }

            companyInfo.setUsername(username);
            companyInfo.setAddress(address);
            companyInfo.setPhone(phone);
            companyInfo.setProvince(province);
            companyInfo.setCity(city);
            companyInfo.setCounty(county);
            companyInfo.setTowns(towns);
            companyInfo.setMobilePhone(mobilePhone);
            companyInfo.setContract_people(contract_people);
            companyInfo.setName(name);
            companyInfo.setBycode(FormatUtil.getMemocode(name));
            companyServiceImpl.updateCompanyInfo(companyInfo, companyInfo.getId());
        } else {
            model.put("status", true);
            model.put("msg", "门店信息修改失败！");
            return model;
        }

        model.put("status", true);
        model.put("msg", "门店信息修改完成！");
        return model;

    }

    @RequestMapping(value = "/initUpdateComanyInfo.html", method = RequestMethod.GET)
    public Object initUpdateComanyInfo(HttpServletRequest request, ModelMap model) {
        String companyId = request.getParameter("companyId");

        CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoById(new Integer(companyId));

        SysUsers sysUsers = null;

        if (companyInfo != null) {
            sysUsers = userService.getUserByCompanyId(companyInfo.getId());
        }

        if (companyInfo == null) {
            companyInfo = new CompanyInfo();
        }

        if (sysUsers == null) {
            sysUsers = new SysUsers();
        }

        model.put("company", companyInfo);
        model.put("sys_user", sysUsers);

        return AD_HTML + packageName + "/initUpdateComanyInfo";
    }

    @ResponseBody
    @RequestMapping(value = "/updateComanyInfo.html", method = RequestMethod.POST)
    public Object updateComanyInfo(HttpServletRequest request, ModelMap model) {

        String companyId = request.getParameter("companyId");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String towns = request.getParameter("towns");
        String mobilePhone = request.getParameter("mobilePhone");
        String contract_people = request.getParameter("contract_people");
        String name = request.getParameter("companyName");
        String username = request.getParameter("username");

        if (name == null || name.equals("")) {
            model.put("status", true);
            model.put("msg", "经销商信息修改失败,经销商名称不能为空！");
            return model;
        }

        if (username == null || username.equals("")) {
            model.put("status", true);
            model.put("msg", "经销商信息修改失败,用户名不能为空！");
            return model;
        }

        CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoById(new Integer(companyId));

        if (companyInfo != null) {
            SysUsers sysUsers = userService.getUserByCompanyId(companyInfo.getId());

            if (sysUsers != null) {
                sysUsers.setUsername(username);
                sysUsers.setAddress(address);
                sysUsers.setPhone(phone);
                sysUsers.setProvince(province);
                sysUsers.setCity(city);
                sysUsers.setCounty(county);
                sysUsers.setTowns(towns);
                userService.updateUser(sysUsers);
            }

            companyInfo.setUsername(username);
            companyInfo.setAddress(address);
            companyInfo.setPhone(phone);
            companyInfo.setProvince(province);
            companyInfo.setCity(city);
            companyInfo.setCounty(county);
            companyInfo.setTowns(towns);
            companyInfo.setMobilePhone(mobilePhone);
            companyInfo.setContract_people(contract_people);
            companyInfo.setName(name);
            companyInfo.setBycode(FormatUtil.getMemocode(name));
            companyServiceImpl.updateCompanyInfo(companyInfo, companyInfo.getId());
        } else {
            model.put("status", true);
            model.put("msg", "经销商信息修改失败！");
            return model;
        }

        model.put("status", true);
        model.put("msg", "经销商信息修改完成！");
        return model;

    }

    @ResponseBody
    @RequestMapping(value = "/saveCustomerInfoOther.html", method = RequestMethod.POST)
    public Object saveCustomerInfoOther(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String companyName = request.getParameter("companyName");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String contract_people = request.getParameter("contract_people");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String towns = request.getParameter("towns");
        String mobilePhone = request.getParameter("mobilePhone");
        String companyId = request.getParameter("companyId");

        CompanyInfo parentCompany = companyServiceImpl.getCompanyInfoById(new Integer(companyId));

        String departmentCode = parentCompany.getDepartmentCode();
        Integer departmentId = parentCompany.getDepartmentId();

        if (userService.getByName(username) != null) {
            model.put("status", false);
            model.put("msg", "用户已存在！");
            return model;
        }

        // 保存门店信息
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setUsername(username);
        companyInfo.setParentId(parentCompany.getId());
        companyInfo.setName(companyName);
        companyInfo.setPhone(phone);
        companyInfo.setAddress(address);
        companyInfo.setDepartmentCode(departmentCode);
        companyInfo.setDepartmentId(departmentId);
        companyInfo.setType(1);
        companyInfo.setContract_people(contract_people);
        companyInfo.setBycode(FormatUtil.getMemocode(companyName));
        companyInfo.setProvince(province);
        companyInfo.setCity(city);
        companyInfo.setCounty(county);
        companyInfo.setTowns(towns);
        companyInfo.setMobilePhone(mobilePhone);
        companyInfo.setCreateUid(user.getUserId());
        companyServiceImpl.insertCompany(companyInfo);

        // 保存终端门店用户
        SysRoles role = roleService.getCustomerRole("customer");

        SysUsers newUser = new SysUsers();
        newUser.setUsername(username);
        newUser.setNick(username);
        newUser.setDepartmentId(departmentId);
        newUser.setDepartmentCode(departmentCode);
        newUser.setCertification("2");
        newUser.setType("other");
        newUser.setCompanyId(companyInfo.getId());
        newUser.setRegeditTime(new Date());
        newUser.setAddress(address);
        newUser.setPhone(phone);
        newUser.setProvince(province);
        newUser.setCity(city);
        newUser.setCounty(county);
        newUser.setTowns(towns);

        userService.addUser(newUser, role.getRoleId());

        model.put("status", true);
        model.put("msg", "门店收录完成");
        return model;

    }

    @RequestMapping(value = "/deleteCompanyInfo.html", method = RequestMethod.GET)
    public Object deleteCompanyInfo(HttpServletRequest request, ModelMap model) {

        Integer companyId = Integer.valueOf(request.getParameter("companyId"));

        CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoById(companyId);

        if (companyInfo != null) {
            String username = companyInfo.getUsername();
            if (username != null) {
                companyServiceImpl.deleteCompanyInfoByUsername(username);

                SysUsers sysUsers = userService.getByName(username);

                if (sysUsers != null) {

                    Integer userId = sysUsers.getUserId();

                    userService.deleteByUserRole(userId);

                    userService.deleteUser(userId);
                }

            }
        }

        return AD_HTML + packageName + "/querySale";

    }

    @RequestMapping(value = "/factoryInput.html", method = RequestMethod.GET)
    public Object factoryInput(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Department> departments = departmentService.getDepartmentByCode(paramMap);
        List<Department> lastDepartments = new ArrayList<Department>();

        // 过滤组织机构
        for (Department department : departments) {
            if (!department.getCode().equals(departmentCode)) {
                lastDepartments.add(department);
            }
        }

        model.put("departments", lastDepartments);

        return AD_HTML + packageName + "/factoryInput";
    }

    @ResponseBody
    @RequestMapping(value = "/saveFactory.html", method = RequestMethod.POST)
    public Object saveFactory(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String companyName = request.getParameter("companyName");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String contract_people = request.getParameter("contract_people");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String towns = request.getParameter("towns");
        String mobilePhone = request.getParameter("mobilePhone");
        String deptId = request.getParameter("department");

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentId", Integer.valueOf(deptId));
        Department department = departmentService.getDepartmentById(paramMap);

        String departmentCode = department.getCode();
        Integer departmentId = department.getId();

        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("departmentId", Integer.valueOf(department.getParentId()));
        CompanyInfo info = companyServiceImpl.getCompanyInfoByDepartmentId(paramMap2);

        // 代工厂
        CompanyInfo companyInfo = new CompanyInfo();

        if (info != null) {
            companyInfo.setParentId(info.getId());
        } else {
            companyInfo.setParentId(user.getCompanyId());
        }

        companyInfo.setCreateUid(user.getUserId());
        companyInfo.setUsername(username);
        companyInfo.setName(companyName);
        companyInfo.setPhone(phone);
        companyInfo.setAddress(address);
        companyInfo.setShippingAddress(address);
        companyInfo.setDepartmentCode(departmentCode);
        companyInfo.setDepartmentId(departmentId);
        companyInfo.setType(4);
        companyInfo.setContract_people(contract_people);
        companyInfo.setBycode(FormatUtil.getMemocode(companyName));
        companyInfo.setProvince(province);
        companyInfo.setCity(city);
        companyInfo.setCounty(county);
        companyInfo.setTowns(towns);
        companyInfo.setMobilePhone(mobilePhone);
        companyServiceImpl.insertCompany(companyInfo);

        // 工厂角色
        SysRoles role = roleService.getCustomerRole("factory");

        if (userService.getByName(username) != null) {
            model.put("status", false);
            model.put("msg", "用户已存在！");
            return model;
        }

        SysUsers newUser = new SysUsers();
        newUser.setUsername(username);
        newUser.setNick(username);
        newUser.setDepartmentId(departmentId);
        newUser.setDepartmentCode(departmentCode);
        newUser.setCertification("2");
        newUser.setType("other");
        newUser.setCompanyId(companyInfo.getId());
        newUser.setRegeditTime(new Date());
        newUser.setAddress(address);
        newUser.setPhone(phone);
        newUser.setProvince(province);
        newUser.setCity(city);
        newUser.setCounty(county);
        newUser.setTowns(towns);

        userService.addUser(newUser, role.getRoleId());

        model.put("status", true);
        model.put("msg", "工厂收录完成");
        return model;

    }

    @RequestMapping(value = "/factoryList.html", method = RequestMethod.GET)
    public Object factoryList(HttpServletRequest request, ModelMap model) {
        return AD_HTML + packageName + "/factoryList";
    }

    @ResponseBody
    @RequestMapping(value = "/queryFactoryList.html", method = RequestMethod.POST)
    public Object queryFactoryList(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();
        Integer companyId = user.getCompanyId();

        String ser = request.getParameter("ser");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String contract_people = request.getParameter("contract_people");
        String username = request.getParameter("username");

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", name);
        paramMap.put("phone", phone);
        paramMap.put("city", city);
        paramMap.put("county", county);
        paramMap.put("contract_people", contract_people);
        paramMap.put("username", username);

        // 角色类型
        String assortment = user.getAssortment();

        // 财务人员查询所有
        if (assortment.equals("financial")) {
            paramMap.put("departmentCode", "");
        }

        // 工厂查询所有
        if (assortment.equals("factory")) {
            paramMap.put("departmentCode", "");
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        List<CompanyInfo> list = companyServiceImpl.getAllFactorys(paramMap);
        pager.setTotalRecord(companyServiceImpl.getAllFactorysCount(paramMap));

        model.put("list", list);
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("assortment", assortment);

        return model;

    }

    @RequestMapping(value = "/proxyFactory.html", method = RequestMethod.GET)
    public Object proxyFactory(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Department> departments = departmentService.getDepartmentByCode(paramMap);
        List<Department> lastDepartments = new ArrayList<Department>();

        // 过滤组织机构
        for (Department department : departments) {
            if (!department.getCode().equals(departmentCode)) {
                lastDepartments.add(department);
            }
        }

        model.put("departments", lastDepartments);

        return AD_HTML + packageName + "/proxyFactory";
    }

    @ResponseBody
    @RequestMapping(value = "/saveProxyFactory.html", method = RequestMethod.POST)
    public Object saveProxyFactory(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();

        String companyName = request.getParameter("companyName");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String contract_people = request.getParameter("contract_people");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String towns = request.getParameter("towns");
        String mobilePhone = request.getParameter("mobilePhone");
        String deptId = request.getParameter("department");

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentId", Integer.valueOf(deptId));
        Department department = departmentService.getDepartmentById(paramMap);

        String departmentCode = department.getCode();
        Integer departmentId = department.getId();

        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("departmentId", Integer.valueOf(department.getParentId()));
        CompanyInfo info = companyServiceImpl.getCompanyInfoByDepartmentId(paramMap2);

        // 代工厂
        CompanyInfo companyInfo = new CompanyInfo();

        if (info != null) {
            companyInfo.setParentId(info.getId());
        } else {
            companyInfo.setParentId(user.getCompanyId());
        }
        companyInfo.setShippingAddress(address);
        companyInfo.setCreateUid(user.getUserId());
        companyInfo.setUsername(username);
        companyInfo.setName(companyName);
        companyInfo.setPhone(phone);
        companyInfo.setAddress(address);
        companyInfo.setDepartmentCode(departmentCode);
        companyInfo.setDepartmentId(departmentId);
        companyInfo.setType(3);
        companyInfo.setContract_people(contract_people);
        companyInfo.setBycode(FormatUtil.getMemocode(companyName));
        companyInfo.setProvince(province);
        companyInfo.setCity(city);
        companyInfo.setCounty(county);
        companyInfo.setTowns(towns);
        companyInfo.setMobilePhone(mobilePhone);
        companyServiceImpl.insertCompany(companyInfo);

        // 代工厂角色
        SysRoles role = roleService.getCustomerRole("proxyFactory");

        if (userService.getByName(username) != null) {
            model.put("status", false);
            model.put("msg", "用户已存在！");
            return model;
        }

        SysUsers newUser = new SysUsers();
        newUser.setUsername(username);
        newUser.setNick(username);
        newUser.setDepartmentId(departmentId);
        newUser.setDepartmentCode(departmentCode);
        newUser.setCertification("2");
        newUser.setType("other");
        newUser.setCompanyId(companyInfo.getId());
        newUser.setRegeditTime(new Date());
        newUser.setAddress(address);
        newUser.setPhone(phone);
        newUser.setProvince(province);
        newUser.setCity(city);
        newUser.setCounty(county);
        newUser.setTowns(towns);

        userService.addUser(newUser, role.getRoleId());

        model.put("status", true);
        model.put("msg", "代工厂收录完成");

        return model;
    }

    @RequestMapping(value = "/proxyFactoryList.html", method = RequestMethod.GET)
    public Object proxyFactoryList(HttpServletRequest request, ModelMap model) {
        return AD_HTML + packageName + "/queryProxyFactory";
    }

    @ResponseBody
    @RequestMapping(value = "/queryProxyFactorys.html", method = RequestMethod.POST)
    public Object queryProxyFactorys(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();
        Integer companyId = user.getCompanyId();

        String ser = request.getParameter("ser");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String contract_people = request.getParameter("contract_people");
        String username = request.getParameter("username");

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", name);
        paramMap.put("phone", phone);
        paramMap.put("city", city);
        paramMap.put("county", county);
        paramMap.put("contract_people", contract_people);
        paramMap.put("username", username);

        // 角色类型
        String assortment = user.getAssortment();

        // 财务人员查询所有
        if (assortment.equals("financial")) {
            paramMap.put("departmentCode", "");
        }

        // 工厂查询所有
        if (assortment.equals("factory")) {
            paramMap.put("departmentCode", "");
        }

        // 终端门店时候，传入companyId
        if (assortment.equals("customer")) {
            paramMap.put("id", Integer.valueOf(companyId));
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        List<CompanyInfo> list = companyServiceImpl.getAllProxyFactorys(paramMap);
        pager.setTotalRecord(companyServiceImpl.getAllProxyFactorysCount(paramMap));

        model.put("list", list);
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("assortment", assortment);

        return model;

    }

    @RequestMapping(value = "/customers.html", method = RequestMethod.GET)
    public Object customers(HttpServletRequest request, ModelMap model) {

        return AD_HTML + packageName + "/queryCustomer";
    }

    @ResponseBody
    @RequestMapping(value = "/queryCustomers.html", method = RequestMethod.POST)
    public Object queryAllCustomers(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();
        Integer companyId = user.getCompanyId();

        String ser = request.getParameter("ser");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String contract_people = request.getParameter("contract_people");
        String username = request.getParameter("username");
        String address = request.getParameter("address");
        String djsale = request.getParameter("djsale");
        String dqsaleCode = request.getParameter("dqsale");

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        // 总统筹
        List<CompanyInfo> listSales = new ArrayList<CompanyInfo>();
        Map<String, Object> paramMap3 = new HashMap<String, Object>();
        paramMap3.put("assortment", "dqsale");
        paramMap3.put("currPage", 0);
        paramMap3.put("pageSize", Integer.MAX_VALUE);
        listSales = companyServiceImpl.getAllSales(paramMap3);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dqsaleCode", dqsaleCode);
        paramMap.put("djsale", djsale);
        paramMap.put("address", address);
        paramMap.put("province", province);
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", name);
        paramMap.put("contract_people", contract_people);
        paramMap.put("city", city);
        paramMap.put("phone", phone);
        paramMap.put("county", county);
        paramMap.put("username", username);

        // 角色类型
        String assortment = user.getAssortment();

        if (assortment.equals("djsale")) {
            paramMap.put("parentCompany", String.valueOf(user.getCompanyId()));
        }

        // 财务人员查询所有
        if (assortment.equals("financial")) {
            paramMap.put("departmentCode", "");
        }

        // 工厂查询所有
        if (assortment.equals("factory")) {
            paramMap.put("departmentCode", "");
        }

        // 终端门店时候，传入companyId
        if (assortment.equals("customer")) {
            paramMap.put("id", Integer.valueOf(companyId));
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        List<CompanyInfo> list = companyServiceImpl.getAllCustomers(paramMap);
        pager.setTotalRecord(companyServiceImpl.getAllCustomersCount(paramMap));

        model.put("list", list);
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("assortment", assortment);
        model.put("listSales", listSales);
        model.put("dqsaleCode", dqsaleCode);

        return model;

    }

    @RequestMapping(value = "/sales.html", method = RequestMethod.GET)
    public Object order(HttpServletRequest request, ModelMap model) {
        return AD_HTML + packageName + "/querySale";
    }

    @ResponseBody
    @RequestMapping(value = "/querySales.html", method = RequestMethod.POST)
    public Object queryAllSales(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();
        String ser = request.getParameter("ser");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String contract_people = request.getParameter("contract_people");
        String username = request.getParameter("username");
        String parentCompanyName = request.getParameter("parentCompanyName");
        String province = request.getParameter("province");
        String address = request.getParameter("address");
        String dqsaleCode = request.getParameter("dqsale");

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));

        if (ser != null && ser.trim().equalsIgnoreCase("search")) {
            currPage = 1;
        }

        // 总统筹
        List<CompanyInfo> listSales = new ArrayList<CompanyInfo>();
        Map<String, Object> paramMap3 = new HashMap<String, Object>();
        paramMap3.put("assortment", "dqsale");
        paramMap3.put("currPage", 0);
        paramMap3.put("pageSize", Integer.MAX_VALUE);
        listSales = companyServiceImpl.getAllSales(paramMap3);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentCode", departmentCode);
        paramMap.put("name", name);
        paramMap.put("contract_people", contract_people);
        paramMap.put("city", city);
        paramMap.put("phone", phone);
        paramMap.put("county", county);
        paramMap.put("username", username);
        paramMap.put("parentCompanyName", parentCompanyName);
        paramMap.put("address", address);
        paramMap.put("province", province);
        paramMap.put("dqsaleCode", dqsaleCode);

        // 角色类型
        String assortment = user.getAssortment();

        // 财务人员查询所有
        if (assortment.equals("financial")) {
            paramMap.put("departmentCode", "");
        }

        // 工厂查询所有
        if (assortment.equals("factory")) {
            paramMap.put("departmentCode", "");
        }

        // 终端门店时候，传入companyId
        if (assortment.equals("customer")) {
            paramMap.put("id", Integer.valueOf(user.getCompanyId()));
        }

        // 每页显示记录条数
        int pageSize = 10;
        Pager pager = new Pager(pageSize, currPage);
        paramMap.put("currPage", (currPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);

        pager.setTotalRecord(companyServiceImpl.getAllSalesCount(paramMap));

        List<CompanyInfo> list = companyServiceImpl.getAllSales(paramMap);

        model.put("list", list);
        model.put("count", pager.getTotalRecord());
        model.put("totalPage", pager.getTotalPage());
        model.put("currPage", currPage);
        model.put("pageSize", pageSize);
        model.put("assortment", assortment);
        model.put("listSales", listSales);
        model.put("dqsaleCode", dqsaleCode);

        return model;
    }

    @RequestMapping(value = "/saleInfo.html", method = RequestMethod.GET)
    public Object saleInfo(HttpServletRequest request, ModelMap model) {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<SysRoles> roles = roleService.getRoles();
        List<Department> departments = departmentService.getDepartmentByCode(paramMap);
        List<Department> lastDepartments = new ArrayList<Department>();
        List<SysRoles> lastRoles = new ArrayList<SysRoles>();

        // 过滤组织机构
        for (Department department : departments) {
            if (!department.getCode().equals(departmentCode)) {
                lastDepartments.add(department);
            }
        }

        // 大区域代理商可以管理地级市经销商与分销商
        if (user.getAssortment().equals("dqsale")) {
            for (SysRoles sysRoles : roles) {
                if (sysRoles.getAssortment().equals("djsale")) {
                    lastRoles.add(sysRoles);
                }
                if (sysRoles.getAssortment().equals("fxsale")) {
                    lastRoles.add(sysRoles);
                }
            }
        }

        // 如果经理角色可以添加所有
        if (user.getAssortment().equals("manger")) {
            for (SysRoles sysRoles : roles) {
                if (sysRoles.getAssortment().equals("dqsale")) {
                    lastRoles.add(sysRoles);
                }

                if (sysRoles.getAssortment().equals("djsale")) {
                    lastRoles.add(sysRoles);
                }
                if (sysRoles.getAssortment().equals("fxsale")) {
                    lastRoles.add(sysRoles);
                }
            }
        }

        // 行政及其他角色
        if (user.getAssortment().equals("other")) {
            for (SysRoles sysRoles : roles) {

                if (sysRoles.getAssortment().equals("dqsale")) {
                    lastRoles.add(sysRoles);
                }

                if (sysRoles.getAssortment().equals("djsale")) {
                    lastRoles.add(sysRoles);
                }

                if (sysRoles.getAssortment().equals("fxsale")) {
                    lastRoles.add(sysRoles);
                }
            }
        }

        model.put("roles", lastRoles);
        model.put("departments", lastDepartments);

        return AD_HTML + packageName + "/sales";
    }

    /**
     * 保存终端门店信息
     * 
     * @Author
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveCustomerInfo.html", method = RequestMethod.POST)
    public Object saveCustomerInfo(HttpServletRequest request, ModelMap model) {

        String companyName = request.getParameter("companyName");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String contract_people = request.getParameter("contract_people");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String towns = request.getParameter("towns");
        String mobilePhone = request.getParameter("mobilePhone");

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();
        Integer departmentId = user.getDepartmentId();

        if (userService.getByName(username) != null) {
            model.put("status", false);
            model.put("msg", "用户已存在！");
            return model;
        }

        // 保存门店信息
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCreateUid(user.getUserId());
        companyInfo.setUsername(username);
        if (user.getCompanyId() != null) {
            companyInfo.setParentId(user.getCompanyId());
        }

        companyInfo.setName(companyName);
        companyInfo.setPhone(phone);
        companyInfo.setShippingAddress(address);
        companyInfo.setAddress(address);
        companyInfo.setDepartmentCode(departmentCode);
        companyInfo.setDepartmentId(departmentId);
        companyInfo.setType(1);
        companyInfo.setContract_people(contract_people);
        companyInfo.setBycode(FormatUtil.getMemocode(companyName));
        companyInfo.setProvince(province);
        companyInfo.setCity(city);
        companyInfo.setCounty(county);
        companyInfo.setTowns(towns);
        companyInfo.setMobilePhone(mobilePhone);
        companyServiceImpl.insertCompany(companyInfo);

        // 保存终端门店用户
        SysRoles role = roleService.getCustomerRole("customer");

        SysUsers newUser = new SysUsers();
        newUser.setUsername(username);
        newUser.setNick(username);
        newUser.setDepartmentId(departmentId);
        newUser.setDepartmentCode(departmentCode);
        newUser.setCertification("2");
        newUser.setType("other");
        newUser.setCompanyId(companyInfo.getId());
        newUser.setRegeditTime(new Date());
        newUser.setAddress(address);
        newUser.setPhone(phone);
        newUser.setProvince(province);
        newUser.setCity(city);
        newUser.setCounty(county);
        newUser.setTowns(towns);

        userService.addUser(newUser, role.getRoleId());

        model.put("status", true);
        model.put("msg", "门店收录完成");

        return model;
    }

    /**
     * 保存经销商
     * 
     * 
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveSaleInfo.html", method = RequestMethod.POST)
    public Object saveSaleInfo(HttpServletRequest request, ModelMap model) {
        SysUsers user = getUser();

        String companyName = request.getParameter("companyName");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String contract_people = request.getParameter("contract_people");
        String deptId = request.getParameter("department");
        String roleId = request.getParameter("roleId");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String county = request.getParameter("county");
        String towns = request.getParameter("towns");
        String mobilePhone = request.getParameter("mobilePhone");

        SysRoles role = roleService.selectByPrimaryKey(Integer.valueOf(roleId));

        if (userService.getByName(username) != null) {
            model.put("status", false);
            model.put("msg", "用户已存在！");
            return model;
        }

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentId", Integer.valueOf(deptId));
        Department department = departmentService.getDepartmentById(paramMap);

        String departmentCode = department.getCode();
        Integer departmentId = department.getId();
        Integer parentId = department.getParentId();

        Map<String, Object> paramMap2 = new HashMap<String, Object>();
        paramMap2.put("departmentId", Integer.valueOf(parentId));
        CompanyInfo info = companyServiceImpl.getCompanyInfoByDepartmentId(paramMap2);

        // 保存门店信息
        CompanyInfo companyInfo = new CompanyInfo();

        if (info != null) {
            companyInfo.setParentId(info.getId());
        } else {
            if (user.getCompanyId() != null) {
                companyInfo.setParentId(user.getCompanyId());
            }
        }
        companyInfo.setShippingAddress(address);
        companyInfo.setUsername(username);
        companyInfo.setName(companyName);
        companyInfo.setPhone(phone);
        companyInfo.setAddress(address);
        companyInfo.setDepartmentCode(departmentCode);
        companyInfo.setDepartmentId(departmentId);
        companyInfo.setType(0);
        companyInfo.setContract_people(contract_people);
        companyInfo.setBycode(FormatUtil.getMemocode(companyName));
        companyInfo.setProvince(province);
        companyInfo.setCity(city);
        companyInfo.setCounty(county);
        companyInfo.setTowns(towns);
        companyInfo.setMobilePhone(mobilePhone);
        companyServiceImpl.insertCompany(companyInfo);

        SysUsers newUser = new SysUsers();
        newUser.setUsername(username);
        newUser.setNick(username);
        newUser.setDepartmentId(departmentId);
        newUser.setDepartmentCode(departmentCode);
        newUser.setCertification("2");
        newUser.setType("other");
        newUser.setRegeditTime(new Date());
        newUser.setCompanyId(companyInfo.getId());
        newUser.setAddress(address);
        newUser.setPhone(phone);
        newUser.setProvince(province);
        newUser.setCity(city);
        newUser.setCounty(county);
        newUser.setTowns(towns);
        userService.addUser(newUser, role.getRoleId());

        model.put("status", true);
        model.put("msg", "经销商收录完成");

        return model;
    }

    @RequestMapping(value = "/company_infoView.html", method = RequestMethod.GET)
    public Object company_infoView(HttpServletRequest request, ModelMap model) {

        Integer companyId = Integer.valueOf(request.getParameter("companyId"));

        CompanyInfo companyInfo = companyServiceImpl.getCompanyInfoById(companyId);

        model.put("companyInfo", companyInfo);

        return AD_HTML + packageName + "/company_infoView";
    }
}
