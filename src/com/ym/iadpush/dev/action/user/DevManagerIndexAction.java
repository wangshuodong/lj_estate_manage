package com.ym.iadpush.dev.action.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.iadpush.dev.service.earn.DevEarnService;


@Controller
public class DevManagerIndexAction extends BaseAction {
    
    @Autowired
    private DevEarnService devEarnService;
    
    private static String FLODER = "dev";
    
    
 
    @SuppressWarnings("static-access")
    @RequestMapping(value="/dev_manager_index.html",method=RequestMethod.GET)
    public String jumpToIndex(HttpServletRequest request, ModelMap model){
        model.put("devEarn", devEarnService.selectEarnByDate(null, null, this.getUser().getUserId()));
        model.put("quickreport", devEarnService.selectQuickReport(this.getUser().getUserId()));
        return AD_HTML+FLODER+"/manager-index";
    }
    
    @SuppressWarnings("static-access")
    @ResponseBody
    @RequestMapping(value="/getIndexData.html",method=RequestMethod.POST)
    public Object getIndexData(HttpServletRequest request, ModelMap model){
        model.put("devEarn", devEarnService.selectEarnByDate(null, null, this.getUser().getUserId()));
        model.put("quickreport", devEarnService.selectQuickReport(this.getUser().getUserId()));
        return model;
    }
    
    public DevEarnService getDevEarnService() {
        return devEarnService;
    }

    public void setDevEarnService(DevEarnService devEarnService) {
        this.devEarnService = devEarnService;
    }

}
