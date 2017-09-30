package com.ym.iadpush.dev.action.app;

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
import com.ym.iadpush.dev.service.app.DevAppStatementService;

@Controller
public class DevAppStatementAction extends BaseAction {
    
    @Autowired
    private DevAppStatementService devAppStatementService;
    
    private static String FLODER = "app";
    
    

    @ResponseBody
    @RequestMapping(value="search_dev_app_statement_detail.html",method=RequestMethod.POST)
    public ModelMap searchStatementDetail(HttpServletRequest request, ModelMap model){
        
        Map<String,Object> params = this.getParams(request);
        
        QueryResult result = devAppStatementService.queyAppStatementDetal(params);
        
        this.setModeMap(result, model, params);
        
        return model;
    }
    
    
    

    @RequestMapping(value="/dev_app_statement_detail.html",method = RequestMethod.GET)
    public String jumpToAppStatementDetail(HttpServletRequest request, ModelMap model){
        return AD_HTML + FLODER + "/dev-app-statement-detail";
    }
    
 
    private Map<String,Object> getParams(HttpServletRequest request){
        
        String pageNo = request.getParameter("currPage");
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String appName = request.getParameter("appName");
        
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("pageNo", pageNo==null?0:Integer.valueOf(pageNo)-1);
        params.put("pageSize", this.defaultPageSize);
        params.put("uid", this.getUser().getUserId());
        
        if(startDate != null && !startDate.trim().equalsIgnoreCase("")){
            params.put("startDate", startDate);
        }
        if(endDate != null && !endDate.trim().equalsIgnoreCase("")){
            params.put("endDate", endDate);
        }
        if(appName != null && !appName.trim().equalsIgnoreCase("")){
            params.put("appName", appName);
        }
        
        return params;
    }
    
    private ModelMap setModeMap(QueryResult result, ModelMap model,Map<String,Object> params){
       
        model.put("list", result.getData());
        model.put("count", result.getCount());
        model.put("collect", result.getCollect());
        model.put("currPage", Integer.valueOf(String.valueOf(params.get("pageNo")))+1);
        model.put("pageSize", params.get("pageSize"));
        model.put("startDate", params.get("startDate"));
        model.put("endDate", params.get("endDate"));
        
        return model;
    }
    

    @ResponseBody
    @RequestMapping(value="/search_dev_app_statement.html",method = RequestMethod.POST)
    public ModelMap searchAppStatement(HttpServletRequest request, ModelMap model){
        
        Map<String,Object> params = getParams(request);
        
        QueryResult result = devAppStatementService.query(params);
        
        model = this.setModeMap(result, model, params);
        
        return model;
    }
    

    @RequestMapping(value="/dev_app_statement.html",method = RequestMethod.GET)
    public String jumpToAppStatement(HttpServletRequest request, ModelMap model){
        return AD_HTML + FLODER + "/dev-app-statement";
    }

    public DevAppStatementService getDevAppStatementService() {
        return devAppStatementService;
    }

    public void setDevAppStatementService(DevAppStatementService devAppStatementService) {
        this.devAppStatementService = devAppStatementService;
    }

}
