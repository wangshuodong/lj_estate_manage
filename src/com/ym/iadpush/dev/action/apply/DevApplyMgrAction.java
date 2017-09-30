package com.ym.iadpush.dev.action.apply;

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
import com.ym.common.utils.DateUtils;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.dev.service.apply.DevIApplyMgr;
import com.ym.iadpush.manage.entity.App;
import com.ym.iadpush.manage.services.apply.IApplyMgr;


@Controller
public class DevApplyMgrAction extends BaseAction {

    private @Autowired DevIApplyMgr devApplyImpl;
    private @Autowired IApplyMgr applyImpl;
    private String packageName = "applymanager";
    
    
    private boolean columFilter(String colum){
    	List<String> list = new ArrayList<String>();
    	list.add("dicount");
    	list.add("aid");
    	list.add("dearn");
    	if(list.contains(colum)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    private boolean sortType(String type){
    	List<String> list = new ArrayList<String>();
    	list.add("asc");
    	list.add("desc");
    	if(list.contains(type)){
    		return true;
    	}else{
    		return false;
    	}
    }
    

    @ResponseBody
    @RequestMapping(value = "/dev_app_list.html", method = RequestMethod.POST)
    public ModelMap searchAppList(HttpServletRequest request, ModelMap model){
    	
    	Map<String,Object> params = new HashMap<String, Object>();
    	 int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                 .getParameter("currPage"));
         String appkey = request.getParameter("appkey");
         String appname = request.getParameter("appname");
         String username = request.getParameter("username");
         String type = request.getParameter("type");
         
         if (appkey != null && !appkey.trim().equalsIgnoreCase("")) {
             String[] arr = appkey.trim().split("-");
             if (arr.length == 3) {
                 params.put("uid", Integer.parseInt(arr[0]));
                 params.put("qid", Integer.parseInt(arr[1]));
                 params.put("appid", Integer.parseInt(arr[2]));
             } else {
                 params.put("uid", -1);
             }
             params.put("appkey", appkey);
         }

         if (appname != null && !appname.trim().equalsIgnoreCase("")) {
             params.put("appname", appname.trim() );
         }

         if (username != null && !username.trim().equalsIgnoreCase("")) {
             params.put("username",username.trim());
         }
         
         if(type!=null&&type.trim().equalsIgnoreCase("search")){
         	currPage=1;
         }
        
        params.put("uid", getUser().getUserId());
        params.put("pageNo", currPage-1);
        params.put("pageSize", super.defaultPageSize);
        if (getUser().getRoleType().equals("outService")) {
        	params.put("serviceId", getUser().getUserId());
        }
        
        String sortColum = request.getParameter("sortColum");
        String sortType = request.getParameter("sortType");
        if(sortColum == null || !columFilter(sortColum.trim())){
        	sortColum = "aid";
        }
        if(sortType == null || !sortType(sortType.trim())){
        	sortType = "desc";
        }
        params.put("sortColum", sortColum);
        params.put("sortType", sortType);
    	QueryResult result = applyImpl.queryAllApp(params);
    	 model.put("listApp", result.getData());
         model.put("count", result.getCount());
         //汇总
         model.put("currPage", currPage);
         model.put("totalPage",getTotalPage(result.getCount(),super.defaultPageSize));
         model.put("pageSize", super.defaultPageSize);
    	return model;
    }
    
    
    @RequestMapping(value = "/dev_app_list.html", method = RequestMethod.GET)
    public String getAppList(HttpServletRequest request, ModelMap model){
    	return AD_HTML + packageName + "/dev_app_list";
    }


    @SuppressWarnings("static-access")
	@ResponseBody
    @RequestMapping(value = "/dev_apply_data_list.html", method = RequestMethod.POST)
    public Object ajaxListApplyProfile(HttpServletRequest request, ModelMap model) {

        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));
        String appkey = request.getParameter("appkey");
        String appname = request.getParameter("appname");
        String type = request.getParameter("type");
        
        
        Map<String, Object> params = new HashMap<String, Object>();

        if (appkey != null && !appkey.trim().equalsIgnoreCase("")) {
            String[] arr = appkey.trim().split("-");
            if (arr.length == 3) {
                params.put("uid", Integer.parseInt(arr[0]));
                params.put("qid", Integer.parseInt(arr[1]));
                params.put("aid", Integer.parseInt(arr[2]));
            } else {
                params.put("uid", -1);
            }
            params.put("appkey", appkey);
        }

        if (appname != null && !appname.trim().equalsIgnoreCase("")) {
            params.put("appname", "%" + appname.trim() + "%");
        }

        
        if(type!=null&&type.trim().equalsIgnoreCase("search")){
        	currPage=1;
        }
        int pagesize=10;
        params.put("currPage", (currPage-1)*pagesize);
        params.put("pageSize", pagesize);
        params.put("nowtime", DateUtils.getDate());
        params.put("uid", this.getUser().getUserId());
        params.put("roleName", this.getUser().getRoleType());
        QueryResult result = applyImpl.queryDataStatementForDev(params);
        
        int count=result.getCount();
        model.put("listApp", result.getData());
        model.put("count", count);
        //汇总
        App app=applyImpl.getTotalEarnAndIcountForDev(params);
        if(app!=null){
	        model.put("totalICount", app.getTotalICount());
	        model.put("totalEarn", app.getTotalEarn());
	        model.put("totalAcount", app.getAcount());
        }
        
        model.put("currPage", currPage);
        model.put("totalPage",getTotalPage(count,pagesize));
        model.put("pageSize", pagesize);

        return model;
    }
    private int getTotalPage(int count,int pagesize){
		return count%pagesize>0?count/pagesize+1:count/pagesize;
    }
    @ResponseBody
    @RequestMapping(value = "/dev_update_app_status.html", method = RequestMethod.POST)
    public Object dev_update_app_status(HttpServletRequest request, ModelMap model) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	int appid = Integer.parseInt(request.getParameter("appid"));
    	String status = request.getParameter("status");
    	if(status.equals("undefined")){
    		status="0,0,0";
    	}
    	params.put("appid", appid);
    	params.put("status", status);
    	int result=devApplyImpl.updateAppStatus(params);
    	model.put("result", result);
    	return model;
    }
    @ResponseBody
    @RequestMapping(value = "/addApp.html", method = RequestMethod.POST)
    public Object addApp(HttpServletRequest request, ModelMap model) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	String name=request.getParameter("name");
    	String packagename=request.getParameter("packagename");
    	params.put("appname", name);
    	params.put("qid", request.getParameter("channel"));
    	params.put("packagename", packagename);
    	params.put("ex", request.getParameter("ex"));
    	params.put("uid", this.getUser().getUserId());
    	if(name == null || name.trim().equalsIgnoreCase("")){
        	model.put("name", false);
        	return model;
    	}
    	else if(packagename == null || packagename.trim().equalsIgnoreCase("")){
        	model.put("packagename", false);
        	return model;
    	}
    	model.put("result", devApplyImpl.insertApp(params));
    	return model;
    }
	@ResponseBody
    @RequestMapping(value = "/updateApp.html", method = RequestMethod.POST)
    public Object updateApp(HttpServletRequest request, ModelMap model) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	String name= request.getParameter("name");
    	params.put("appname",name);
    	params.put("id", request.getParameter("id"));
    	params.put("ex", request.getParameter("ex"));
    	if(name == null || name.trim().equalsIgnoreCase("")){
        	model.put("name", false);
        	return model;
    	}
    	model.put("result", devApplyImpl.updatetApp(params));
    	return model;
    }
    @ResponseBody
    @RequestMapping(value = "/loadChannel.html", method = RequestMethod.POST)
    public Object loadChannel(HttpServletRequest request, ModelMap model) {
    	model.put("listChannel", devApplyImpl.selectChannel());
    	return model;
    }
    @RequestMapping(value = "/dev_apply_au.html", method = RequestMethod.GET)
    public Object dev_update_apply(HttpServletRequest request, ModelMap model) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	int id=0;
    	try {
			id=Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
		}
    	params.put("id", id);
    	model.put("devApp", devApplyImpl.selectAppById(params));
        mainOther(request, model);
        return AD_HTML + packageName + "/dev_apply_au";
    }
    @RequestMapping(value = "/dev_apply_list.html", method = RequestMethod.GET)
    public Object jumpToPage(HttpServletRequest request, ModelMap model) {
        mainOther(request, model);
        return AD_HTML + packageName + "/dev_apply_list";
    }
   
}
