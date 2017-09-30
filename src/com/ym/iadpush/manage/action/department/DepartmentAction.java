/**
 * 
 */
package com.ym.iadpush.manage.action.department;

import java.util.ArrayList;
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
import com.ym.common.utils.JacksonBuilder;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.common.utils.FormatUtil;
import com.ym.iadpush.manage.entity.Department;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.department.IDepartmentService;
import com.ym.iadpush.manage.services.tissue.IUserMgr;

@Controller
public class DepartmentAction extends BaseAction {

    public static String FLODER = "department";

    @Autowired
    public IDepartmentService departmentService;

    private @Autowired
    IUserMgr userService;

    @RequestMapping(value = "department_update.html", method = RequestMethod.GET)
    public String jumpToUpdate(HttpServletRequest request, ModelMap model) {
        String id = request.getParameter("id");
        try {
            SysUsers user = getUser();
            String departmentCode = user.getDepartmentCode();

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("code", departmentCode);

            List<Department> departments = departmentService.getDepartmentByCode(paramMap);

            List<Department> lastDepartments = new ArrayList<Department>();
            Department temp = new Department();
            temp.setId(0);
            temp.setName("无");
            lastDepartments.add(temp);

            for (Department department : departments) {
                lastDepartments.add(department);
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("departmentId", id);

            model.put("result", departmentService.getDepartmentById(params));
            model.put("org", lastDepartments);
        } catch (Exception e) {

        }
        return AD_HTML + FLODER + "/department_update";
    }

    @ResponseBody
    @RequestMapping(value = "department_update.do", method = RequestMethod.POST)
    public ModelMap update(HttpServletRequest request, ModelMap model) {
        String name = request.getParameter("orgName");
        String parentId = request.getParameter("parentId");
        String orgUserName = request.getParameter("orgUserName");
        String orgPhone = request.getParameter("orgPhone");
        String orgAddress = request.getParameter("orgAddress");
        String id = request.getParameter("id");

        try {
            Department department = new Department();
            department.setAddress(orgAddress);
            department.setPhone(orgPhone);
            department.setContact_people(orgUserName);
            department.setParentId(Integer.valueOf(parentId));
            department.setName(name);
            department.setBycode(FormatUtil.getMemocode(name));
            department.setId(Integer.valueOf(id));

            departmentService.update(department);

            model.put("status", true);
            model.put("msg", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            model.put("status", false);
            model.put("msg", "修改时发生异常");
        }
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "department_delete.do", method = RequestMethod.POST)
    public ModelMap deleteById(HttpServletRequest request, ModelMap model) {
        String id = request.getParameter("id");
        try {
            departmentService.delete(Integer.valueOf(id));
            model.put("status", true);
            model.put("msg", "操作成功");
        } catch (Exception e) {
            model.put("status", false);
            model.put("msg", "操作失败");
            e.printStackTrace();
        }
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "department_add.do", method = RequestMethod.POST)
    public ModelMap addDepartment(HttpServletRequest request, ModelMap model) {
        String name = request.getParameter("orgName");
        String parentId = request.getParameter("parentId");
        String orgUserName = request.getParameter("orgUserName");
        String orgPhone = request.getParameter("orgPhone");
        String orgAddress = request.getParameter("orgAddress");
        String saleId = request.getParameter("saleId");

        try {
            Department department = new Department();
            department.setAddress(orgAddress);
            department.setPhone(orgPhone);
            department.setContact_people(orgUserName);
            department.setParentId(Integer.valueOf(parentId));
            department.setName(name);
            department.setBycode(FormatUtil.getMemocode(name));
            if (saleId != null && !saleId.equals("")) {
                department.setSaleId(new Integer(saleId));
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("departmentId", Integer.valueOf(parentId));
            Department p = departmentService.getDepartmentById(params);

            String code = p.getCode();

            Department max = departmentService.selectMaxByParentCode(code);

            if (max != null) {
                String orgCode = FormatUtil.getOrgCode(max == null ? null : max.getCode(), code);
                department.setCode(orgCode);
            } else {
                String orgCode = FormatUtil.getOrgCode(null, code);
                department.setCode(orgCode);
            }

            departmentService.insert(department);

            model.put("status", true);
            model.put("msg", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            model.put("status", false);
            model.put("msg", "添加时发生异常");
        }
        return model;
    }

    @RequestMapping(value = "department_add.html", method = RequestMethod.GET)
    public String jumpToAdd(HttpServletRequest request, ModelMap model) {
        try {
            SysUsers user = getUser();
            String departmentCode = user.getDepartmentCode();

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("code", departmentCode);

            List<Department> departments = departmentService.getDepartmentByCode(paramMap);

            model.put("org", departments);
            model.put("salers", userService.querySales());

            System.out.println(userService.querySales());

        } catch (Exception e) {

        }
        return AD_HTML + FLODER + "/department_add";
    }

    @ResponseBody
    @RequestMapping(value = "department_menu.do", method = RequestMethod.POST)
    public ModelMap list(HttpServletRequest request, ModelMap model) {
        String currPage = request.getParameter("currPage");
        String orgName = request.getParameter("orgName");
        String companyName = request.getParameter("companyName");
        
        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();

        Map<String, Object> params = new HashMap<String, Object>();
        if (currPage == null || currPage.trim().equalsIgnoreCase("")) {
            params.put("currPage", Integer.valueOf(0));
            params.put("pageSize", 10000);
        } else {
            params.put("currPage", Integer.valueOf(currPage.trim()) - 1);
            params.put("pageSize", super.defaultPageSize);
        }
        if (orgName != null && !orgName.trim().equalsIgnoreCase("")) {
            params.put("orgName", orgName);
        }
        params.put("code", departmentCode);
        params.put("companyName", companyName);

        try {
            QueryResult result = departmentService.query(params);
            model.put("list", result.getData());
            model.put("count", result.getCount());
            model.put("currPage", Integer.valueOf(String.valueOf(params.get("currPage"))) + 1);
            model.put("pageSize", params.get("pageSize"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @RequestMapping(value = "department_menu.html", method = RequestMethod.GET)
    public String jumpToDepartMent(HttpServletRequest request, ModelMap model) throws Exception {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Department> departmentByCode = departmentService.getDepartmentByCode(paramMap);

        model.put("othermenusJson", JacksonBuilder.mapper().writeValueAsString(departmentByCode));

        return AD_HTML + FLODER + "/department_menu";
    }

    @RequestMapping(value = "department_menu_init.html", method = RequestMethod.GET)
    public String department_menu_init(HttpServletRequest request, ModelMap model) throws Exception {

        SysUsers user = getUser();
        String departmentCode = user.getDepartmentCode();

        // 组织机构
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", departmentCode);

        List<Department> departmentByCode = departmentService.getDepartmentByCode(paramMap);

        model.put("othermenusJson", JacksonBuilder.mapper().writeValueAsString(departmentByCode));

        return AD_HTML + FLODER + "/department_menu_init";
    }

}
