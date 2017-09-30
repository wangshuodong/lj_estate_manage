package com.ym.iadpush.dev.action.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ym.common.base.BaseAction;

@Controller
public class SdkAction extends BaseAction {
    
    private static String FLODER = "sdk";
    
    @RequestMapping(value="downloadSDK.html",method=RequestMethod.GET)
    public String jumpToDownload(HttpServletRequest request, ModelMap model){
        return AD_HTML + FLODER + "/sdk-download";
    }

}
