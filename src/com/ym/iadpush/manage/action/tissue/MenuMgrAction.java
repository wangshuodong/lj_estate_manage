package com.ym.iadpush.manage.action.tissue;

import java.util.HashMap;
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

import com.ym.common.base.BaseAction;
import com.ym.common.base.Pager;
import com.ym.iadpush.manage.entity.SysMenus;
import com.ym.iadpush.manage.services.tissue.IMenuMgr;

@Controller
public class MenuMgrAction extends BaseAction {

    private @Autowired IMenuMgr menuSv;

    @RequestMapping(value = "/menu_mgr.html", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap model) {
        return AD_HTML + "tissue/menu_mgr";
    }
    
    @RequestMapping(value = "/menu_add.html", method = RequestMethod.GET)
    public String addMenuInit(HttpServletRequest request, ModelMap model) {
    	List<Map<String,String>> parentMenus = menuSv.getParentMenus();
    	model.put("list", parentMenus);
    	
    	return AD_HTML + "tissue/menu_add";
    }
    
    @ResponseBody
    @RequestMapping(value = "/menu_add.html", method = RequestMethod.POST)
    public Object addMenu(HttpServletRequest request, ModelMap model) {
    	String isParent = request.getParameter("isParent");
    	String parentId = request.getParameter("parentMenu");
    	
    	SysMenus menu = new SysMenus();
    	menu.setMenuName(getRequestParams(String.class, request, "menuName"));
    	menu.setIsParent("1".equals(isParent)?true:false);
    	menu.setUrl(getRequestParams(String.class, request, "menuUrl"));
    	menu.setStatus(getRequestParams(Integer.class, request, "status"));
    	menu.setType(getRequestParams(String.class, request, "type"));
    	menu.setParentId(StringUtils.isNotBlank(parentId)?Integer.valueOf(parentId):0);
    	menu.setRemark(getRequestParams(String.class, request, "remark"));
    	
        Map<String, Object> result = getResultMap();
        try {
        	if (menuSv.insertMenu(menu) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "添加成功！");
                result.put("menuId", menu.getMenuId());
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "添加失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常处理失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }
    
    @RequestMapping(value = "/menu_modify.html", method = RequestMethod.GET)
    public String updMenuInit(HttpServletRequest request, ModelMap model) {
    	Integer menuId = getRequestParams(Integer.class, request, "menuId");
		SysMenus menu = menuSv.selectByPrimaryKey(menuId);
    	
    	List<Map<String,String>> parentMenus = menuSv.getParentMenus();
    	model.put("list", parentMenus);
    	model.put("menu", menu);
    	
    	return AD_HTML + "tissue/menu_modify";
    }
    
    @ResponseBody
    @RequestMapping(value = "/menu_modify.html", method = RequestMethod.POST)
    public Object updMenu(HttpServletRequest request, ModelMap model) {
    	String isParent = request.getParameter("isParent");
    	String parentId = request.getParameter("parentMenu");
    	SysMenus menu = new SysMenus();
    	menu.setMenuId(getRequestParams(Integer.class, request, "menuId"));
    	menu.setMenuName(getRequestParams(String.class, request, "menuName"));
    	menu.setIsParent("1".equals(isParent)?true:false);
    	menu.setUrl(getRequestParams(String.class, request, "menuUrl"));
    	menu.setStatus(getRequestParams(Integer.class, request, "status"));
    	menu.setType(getRequestParams(String.class, request, "type"));
    	menu.setParentId(StringUtils.isNotBlank(parentId)?Integer.valueOf(parentId):0);
    	menu.setRemark(getRequestParams(String.class, request, "remark"));
    	
        Map<String, Object> result = getResultMap();
        try {
        	if (menu.getMenuId() != null && menuSv.updateMenu(menu) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "修改成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "修改失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常处理失败！");
            getLog(this).error(e.getMessage(), e);
        }
        
        return result;
    }
    
    @ResponseBody
    @RequestMapping(value = "/queryAll.html", method = RequestMethod.POST)
    public Object queryAllMenu(SysMenus menu, HttpServletRequest request, ModelMap model) {
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
		paramMap.put("pageSize", pageSize);
		paramMap.put("begRow", pager.getBegRow());
		
		List<SysMenus> menusList = menuSv.getAllMenus(paramMap);
		int totalRecord = menuSv.selectTotalRecord(paramMap);
		
		// 设置每页显示条数
		pager.setPageSize(pageSize);

		// 设置总记录数
		pager.setTotalRecord(totalRecord);

		// 合计页
		int totalPage = pager.getTotalPage();
		hashMap.put("totalPage", totalPage);

		// 明细数据
		hashMap.put("result", menusList);
		// 合计记录条数
		hashMap.put("totalRecord", totalRecord);

		// 当前页
		hashMap.put("pageNo", pageNo);
		
		return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/delMenu.html", method = RequestMethod.POST)
    public Object delMenu(Integer menuId, HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();
        try {
            if (menuId != null && menuSv.deleteMenu(menuId) > 0) {
                result.put(RESULT, true);
                result.put(MESSAGE, "删除成功！");
            } else {
                result.put(RESULT, false);
                result.put(MESSAGE, "删除失败！");
            }
        } catch (Exception e) {
            result.put(RESULT, false);
            result.put(MESSAGE, "系统异常处理失败！");
            getLog(this).error(e.getMessage(), e);
        }
        return result;
    }
}
