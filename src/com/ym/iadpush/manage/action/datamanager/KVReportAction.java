package com.ym.iadpush.manage.action.datamanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.common.utils.DateUtils;
import com.ym.iadpush.manage.services.datamanager.KVReportService;

@Controller
public class KVReportAction extends BaseAction {
	
	@Autowired
	private KVReportService kvService;
	private String packageName = "iadpushkv";
	
	@RequestMapping(value = "/kvreport.html", method = RequestMethod.GET)
	public String queryKvReport(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		
		return AD_HTML + packageName + "/kvreport";
	}
	
	@ResponseBody
    @RequestMapping(value = "/search_kvreport.html", method = RequestMethod.POST)
    public Object ajaxStatement(HttpServletRequest request, ModelMap model) {
        int currPage = Integer.valueOf(request.getParameter("currPage") == null ? "1" : request
                .getParameter("currPage"));
        String startDate = request.getParameter("startdate");
        String endDate = request.getParameter("enddate");
        String dateStr = request.getParameter("dateType");
        String groupKv = request.getParameter("groupKv");
        String groupSv = request.getParameter("groupSv");
        String appkey = request.getParameter("appkey");
        String kv = request.getParameter("kv");
        String sv = request.getParameter("sv");
        String uid = request.getParameter("uid");

        Map<String, Object> params = new HashMap<String, Object>();

        // 日期类型
        if (dateStr != null && !dateStr.trim().equalsIgnoreCase("")) {
            if (dateStr.equalsIgnoreCase("today")) {
                startDate = DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd");
                endDate = DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd");
            } else if (dateStr.equalsIgnoreCase("yestoday")) {
                startDate = DateUtils.getNDayBeforDate(1);
                endDate = DateUtils.getNDayBeforDate(1);
            } else if (dateStr.equalsIgnoreCase("sevenday")) {
                startDate = DateUtils.getNDayBeforDate(6);
                endDate = DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd");
            } else if (dateStr.equalsIgnoreCase("thismonth")) {
                startDate = DateUtils.getThisMothStart();
                endDate = DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd");
            } else if (dateStr.equalsIgnoreCase("lastmonth")) {
                startDate = DateUtils.getLastMonFirtDay();
                endDate = DateUtils.getLastMothEnd();
            } else if (dateStr.equalsIgnoreCase("all")) {
                startDate = "";
                endDate = "";
            }
        }
        
        if (startDate != null && !startDate.trim().equalsIgnoreCase("")) {
            model.put("startDate", startDate);
            params.put("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
        } else {
            model.put("startDate", "");
        }
        if (endDate != null && !endDate.trim().equalsIgnoreCase("")) {
            model.put("endDate", endDate);
            params.put("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
        } else {
            model.put("endDate", "");
        }
        
        params.put("appkey", appkey);
        params.put("groupKv", groupKv);
        params.put("groupSv", groupSv);
        params.put("kv", kv);
        params.put("sv", sv);
        params.put("uid", uid);

        params.put("currPage", currPage - 1);
        params.put("pageSize", super.defaultPageSize);
        
        List list = kvService.queryKVReport(params);
        int count = kvService.countWithKVReport(params);
        Map<String,String> collect = kvService.collectKVReport(params);

        model.put("list", list);
        model.put("count", count);
        model.put("currPage", currPage);
        model.put("pageSize", super.defaultPageSize);
        model.put("collect", collect);
        model.put("groupKv", groupKv);
        model.put("groupSv", groupSv);

        return model;
    }

}
