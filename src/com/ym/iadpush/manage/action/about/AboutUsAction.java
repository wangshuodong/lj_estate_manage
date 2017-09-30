/**
 * 
 */
package com.ym.iadpush.manage.action.about;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ym.common.base.BaseAction;
import com.ym.iadpush.manage.entity.Account;
import com.ym.iadpush.manage.services.account.AccountService;


@Controller
public class AboutUsAction extends BaseAction {

    private static String FLODER = "about";

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "about_us.html", method = RequestMethod.GET)
    public String aboutUs(HttpServletRequest request, ModelMap model) {
        try {
            List<Account> list = accountService.queryAll();
            model.put("list", list);
        } catch (Exception e) {
            LogFactory.getLog(AboutUsAction.class).error("关于我们查询异常", e);
        }
        return AD_HTML + FLODER + "/about_us";
    }

}
