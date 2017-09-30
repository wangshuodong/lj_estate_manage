package com.ym.iadpush.manage.action.datamanager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.services.app.AppStatementService;

@Controller
public class AppStatementAction extends BaseAction {
    
    @Autowired
    private AppStatementService appStatementService;
    /**
     * 数据管理页面目录
     */
    private static String FLODER = "app";
    

    private Map<String, Object> getParams(HttpServletRequest request){
        Map<String, Object> params = new HashMap<String, Object>();
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String appkey  = request.getParameter("appkey");
        String username = request.getParameter("username");
        String servicename = request.getParameter("servicename");

        if(appkey != null && !appkey.trim().equalsIgnoreCase("")){
            params.put("appkey", appkey);
        }
        
        if(servicename != null && !servicename.trim().equalsIgnoreCase("")){
            params.put("servicename", servicename);
        }

        if(username != null && !username.trim().equalsIgnoreCase("")){
            params.put("username", username);
        }

        if (startDate != null && !startDate.trim().equalsIgnoreCase("")) {
            
            params.put("startDate",startDate);
        }
        if (endDate != null && !endDate.trim().equalsIgnoreCase("")) {
            params.put("endDate", endDate);
        }

        params.put("currPage", currPage - 1);
        params.put("pageSize", super.defaultPageSize);
        return params;
    }
    

    private ModelMap getModelMap(Map<String, Object> params, ModelMap model,QueryResult result){
        
        model.put("list", result.getData());
        model.put("count", result.getCount());
        model.put("currPage", Integer.valueOf(String.valueOf(params.get("currPage")))+1);
        model.put("pageSize", params.get("pageSize"));
        model.put("collect", result.getCollect());
        model.put("startDate", params.get("startDate")==null?"":params.get("startDate"));
        model.put("endDate", params.get("endDate")==null?"":params.get("endDate"));

        return model;
    }
    

    @ResponseBody
    @RequestMapping(value = "/search_app_statement.html", method = RequestMethod.POST)
    public Object ajaxAppStatement(HttpServletRequest request, ModelMap model) {

        Map<String, Object> params = this.getParams(request);

        QueryResult result = appStatementService.queryAppStatement(putServiceId(params));
        
        model = this.getModelMap(params, model, result);
        return model;

    }

    
    

    @RequestMapping(value = "/app_statement.html", method = RequestMethod.GET)
    public String jumpToAppStatement(HttpServletRequest request, ModelMap model){
        
        super.mainOther(request, model);
        return AD_HTML + FLODER + "/app-statement";
        
    }



    public AppStatementService getAppStatementService() {
        return appStatementService;
    }



    public void setAppStatementService(AppStatementService appStatementService) {
        this.appStatementService = appStatementService;
    }

}
