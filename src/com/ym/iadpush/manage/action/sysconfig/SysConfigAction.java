package com.ym.iadpush.manage.action.sysconfig;

import java.sql.Timestamp;
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
import com.ym.iadpush.manage.entity.SysConfig;
import com.ym.iadpush.manage.entity.SysConfigLog;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.sysconfig.ISysConfig;
import com.ym.iadpush.manage.services.sysconfig.ISysConfigLog;

@Controller
public class SysConfigAction extends BaseAction {

    private @Autowired
    ISysConfig sysConfigImpl;
    private @Autowired ISysConfigLog configLogImpl;
    private String packageName = "sysconfig";

    @ResponseBody
    @RequestMapping(value = "/system_data_config.html", method = RequestMethod.POST)
    public Object getAllSysConfig(HttpServletRequest request, ModelMap model) {
    	model.put("listSysConfig", sysConfigImpl.getAllSysConfig());
    	return model;
    }
    @ResponseBody
    @RequestMapping(value = "/update_data_config.html", method = RequestMethod.POST)
    public Object updateSysConfig(HttpServletRequest request, ModelMap model) {
    	 Map<String, Object> params = new HashMap<String, Object>();
    	 
    	String[] cfgval=request.getParameterValues("cfgval");
    	if(cfgval[0]==""||cfgval[1]==""){
    		model.put("error", 1);
    		return model;
    	}
    	
    	List<SysConfig> configList = sysConfigImpl.getAllSysConfig();
    	String []cfgid=request.getParameterValues("cfgid");
    	int result=0;
    	SysConfigLog log = null;
    	String changeContent = "";
    	for (int i = 0; i < cfgval.length; i++) {
    		params.put("val", cfgval[i]);
    		params.put("id", cfgid[i]);
    		result+=sysConfigImpl.updateSysConfig(params);
    		
    		for (SysConfig conf : configList) {
    			if (conf.getSysid() == Integer.parseInt(cfgid[i]) &&
    					!conf.getCfgVal().equals(cfgval[i])) {
    				log = new SysConfigLog();
    	    		log.setOprationUid(getUser().getUserId());
    	    		log.setOprationUsername(getUser().getUsername());
    	    		log.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    	    		changeContent = conf.getRemark() + " 由 " + conf.getCfgVal() + " 改成 " + cfgval[i];
    	    		log.setChangeContent(changeContent);
    	    		configLogImpl.insert(log);
    				break;
    			}
    		}
		}
    	model.put("result", result);
    	return model;
    }
    @RequestMapping(value = "/finance_config.html", method = RequestMethod.GET)
    public Object jumpToPage_finance_config(HttpServletRequest request, ModelMap model) {
    	request.setAttribute("param", "财务设置");
        mainOther(request, model);
        return AD_HTML + packageName + "/system_config";
    }
    @RequestMapping(value = "/dev_config.html", method = RequestMethod.GET)
    public Object jumpToPage_dev_config(HttpServletRequest request, ModelMap model) {
    	request.setAttribute("param", "开发者设置");
        mainOther(request, model);
        return AD_HTML + packageName + "/system_config";
    }
    
    @RequestMapping(value = "/system_configlog.html", method = RequestMethod.GET)
    public Object configLogInit(HttpServletRequest request, ModelMap model) {
        return AD_HTML + packageName + "/system_configlog";
    }
    
    @ResponseBody
    @RequestMapping(value = "/system_configlog.html", method = RequestMethod.POST)
    public Object queryLogList(HttpServletRequest request, ModelMap model) {
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
        
        paramMap.put("startDate", getRequestParams(String.class, request, "startDate"));
        paramMap.put("endDate", getRequestParams(String.class, request, "endDate"));
        paramMap.put("pageSize", pageSize);
        paramMap.put("begRow", pager.getBegRow());

        List<SysConfigLog> list = configLogImpl.getAllConfigLog(paramMap);
        // 总记录数
        int totalRecord = configLogImpl.totalCount(paramMap);
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
}
