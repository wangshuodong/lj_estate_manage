package com.ym.iadpush.manage.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ym.common.base.BaseAction;
import com.ym.iadpush.manage.services.common.IConfigMgr;


@Controller
public class ConfigAction extends BaseAction {

    private @Autowired IConfigMgr configMgr;
    
    @RequestMapping(value = "/config.js", method = RequestMethod.GET)
    public String index(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
        response.setContentType("application/javascript; charset=UTF-8");
        model.put("link", request.getParameter("link"));
        return "common/config";
    }
    
    @RequestMapping(value = "/cascade-{cfgId}.js", method = RequestMethod.GET)
    public String cascade(HttpServletResponse response, @PathVariable("cfgId") String cfgId, ModelMap model) {
        response.setContentType("application/javascript; charset=UTF-8");
        model.put("configs", configMgr.getByParentCfg(cfgId));
        model.put("cfgId", cfgId);
        return "common/cascade";
    }
}
