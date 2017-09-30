package com.ym.iadpush.common.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ym.common.base.BaseAction;

@Controller
public class PortalAction extends BaseAction {
	
	private String FLODER = "portal";
	
	

	@RequestMapping(value="/forgotpwd.html",method=RequestMethod.GET)
	public String forgotpwd(){
		return this.AD_HTML + FLODER + "/forgotpwd";
	}
	

	@RequestMapping(value="/developer.html",method=RequestMethod.GET)
	public String jumpToDeveloper(){
		return this.AD_HTML+FLODER+"/developer";
	}
	

	@RequestMapping(value="/accurateData.html",method=RequestMethod.GET)
	public String jumpToData(){
		return this.AD_HTML+FLODER+"/accurateData";
	}
	

	@RequestMapping(value="/knowIadpush.html",method=RequestMethod.GET)
	public String jumpToAbout(){
		return this.AD_HTML+FLODER+"/knowIadpush";
	}
	

    @RequestMapping(value = "/regist.html", method = RequestMethod.GET)
    public String _login() {
        return AD_HTML + FLODER + "/regist";
    }
	

}
