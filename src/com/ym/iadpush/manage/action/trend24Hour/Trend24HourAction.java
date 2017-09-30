package com.ym.iadpush.manage.action.trend24Hour;

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
import com.ym.iadpush.manage.entity.Trend24hour;
import com.ym.iadpush.manage.services.trend24hour.Trend24hourService;

@Controller
public class Trend24HourAction extends BaseAction {
    
    @Autowired
    private Trend24hourService trend24hourService;
    
    /**
     * 数据管理页面目录
     */
    private static String FLODER = "trend24hour";
    

    @ResponseBody
    @RequestMapping(value = "/search_24hour_statement.html", method = RequestMethod.POST)
    public ModelMap ajaxfindTrend(HttpServletRequest request, ModelMap model){
        
        String date1 = request.getParameter("startdate");
        String date2= request.getParameter("enddate");
        String appkey = request.getParameter("appkey");
        String kv = request.getParameter("kv");
        String column = request.getParameter("column");
        
        Map<String, Object> params1 = new HashMap<String, Object>();
        Map<String,Object> params2 = new HashMap<String, Object>();
        
        if(date1 == null || date1.trim().equalsIgnoreCase("")){
            date1 = DateUtils.getNDayBeforDate(1);
        }
        if(date2 == null || date2.trim().equalsIgnoreCase("")){
            date2 = DateUtils.getDate();
        }
        params1.put("date", date1);
        params2.put("date", date2);
        
        
        if(appkey != null && !appkey.trim().equalsIgnoreCase("")){
            params1.put("appkey", appkey);
            params2.put("appkey", appkey);
        }
        if(kv != null && !kv.trim().equalsIgnoreCase("")){
            params1.put("kv", kv);
            params2.put("kv", kv);
        }
        
        if(column == null || column.trim().equalsIgnoreCase("")){
            params1.put("column", "icount");
            params2.put("column", "icount");
        }else{
            params1.put("column", column);
            params2.put("column", column);
        }
        
        List<Trend24hour> list1= trend24hourService.queryTrend24hour(putServiceId(params1));
        List<Trend24hour> list2 = trend24hourService.queryTrend24hour(putServiceId(params2));
        
        int[] a1 = new int[24];
        int[] a2 = new int[24];
        int total1 = 0;
    	int total2 = 0;
        for(Trend24hour t:list1){
            a1[t.getHour()] = t.getCount();
            total1 += t.getCount();
        }
        for(Trend24hour t:list2){
            a2[t.getHour()] = t.getCount();
            total2 += t.getCount();
        }
        model.put("r1",a1);
        model.put("r2",a2);
        model.put("date1", date1);
        model.put("date2", date2);
        model.put("t1", String.valueOf(total1));
        model.put("t2", String.valueOf(total2));
        
        return model;
    }
    

    @RequestMapping(value = "/24hour_statement.html", method = RequestMethod.GET)
    public String jumpToTrend(HttpServletRequest request, ModelMap model){
        
        super.mainOther(request, model);
        return AD_HTML + FLODER + "/24hour_statement";
    }
    
    @RequestMapping(value = "/pay_24hour_statement.html", method = RequestMethod.GET)
    public String payTrendInit(HttpServletRequest request, ModelMap model) {
    	return AD_HTML + FLODER + "/pay_24hour_statement";
    }
    
    @ResponseBody
    @RequestMapping(value = "/search_pay_24hour_statement.html", method = RequestMethod.POST)
    public ModelMap ajaxfindPayTrend(HttpServletRequest request, ModelMap model){
    	String date1 = getRequestParams(String.class, request, "startdate");
    	String date2 = getRequestParams(String.class, request, "enddate");
    	if(date1 == null || date1.trim().equalsIgnoreCase("")){
    		date1 = DateUtils.getNDayBeforDate(1);
        }
        if(date2 == null || date2.trim().equalsIgnoreCase("")){
        	date2 = DateUtils.getDate();
        }
    	
    	Map<String, Object> params1 = new HashMap<String, Object>();
    	Map<String, Object> params2 = new HashMap<String, Object>();
    	params1.put("username", getRequestParams(String.class, request, "username"));
    	params1.put("appname", getRequestParams(String.class, request, "appname"));
    	params1.put("date", date1);
    	params2.put("username", getRequestParams(String.class, request, "username"));
    	params2.put("appname", getRequestParams(String.class, request, "appname"));
    	params2.put("date", date2);
    	
    	List<Map<String,String>> list1 = trend24hourService.queryPayTrend24hour(putServiceId(params1));
    	List<Map<String,String>> list2 = trend24hourService.queryPayTrend24hour(putServiceId(params2));
    	float[] a1 = new float[24];
    	float[] a2 = new float[24];
    	float totalEarn1 = 0f;
    	float totalEarn2 = 0f;
        for(Map m:list1){
            a1[Integer.parseInt(String.valueOf(m.get("hour")))] = Float.parseFloat(String.valueOf(m.get("count")));
            totalEarn1 += Float.parseFloat(String.valueOf(m.get("count")));
        }
        for(Map m:list2){
            a2[Integer.parseInt(String.valueOf(m.get("hour")))] = Float.parseFloat(String.valueOf(m.get("count")));
            totalEarn2 += Float.parseFloat(String.valueOf(m.get("count")));
        }
        
        model.put("r1",a1);
        model.put("r2",a2);
        model.put("date1", date1);
        model.put("date2", date2);
        model.put("t1", String.valueOf(totalEarn1));
        model.put("t2", String.valueOf(totalEarn2));
        
    	return model;
    }

    public Trend24hourService getTrend24hourService() {
        return trend24hourService;
    }

    public void setTrend24hourService(Trend24hourService trend24hourService) {
        this.trend24hourService = trend24hourService;
    }

}
