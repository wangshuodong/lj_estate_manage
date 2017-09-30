package com.ym.iadpush.manage.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ym.common.base.BaseAction;
import com.ym.common.utils.DateUtils;
import com.ym.iadpush.dev.service.user.DevUserService;
import com.ym.iadpush.manage.entity.News;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.news.INewsService;

@Controller
public class LoginAction extends BaseAction {

    private @Autowired
    INewsService newsServiceImpl;
    
    @Autowired
    private DevUserService devUserService;
 
    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login() {
        return AD_HTML + "portal/login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String _login() {
        return AD_HTML + "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public Object _login(String username, String password, String authcode, Boolean rememberMe,
            HttpServletRequest request, ModelMap model) {
        Map<String, Object> result = getResultMap();

        if (StringUtils.isBlank(username)) {
            result.put(ERROR, "用户名不能为空！");
            return result;
        }

        if (StringUtils.isBlank(password)) {
            result.put(ERROR, "密码不能为空！");
            return result;
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe == null ? false : rememberMe);
        try {
            if (subject.isAuthenticated()) {// 重复登录
                SysUsers user = getUser();
                if (!user.getUsername().equals(username)) {// 如果登录名不同
                    subject.logout();
                    subject.login(token);
                }
            } else {
                subject.login(token);
            }
            SysUsers sysUsers = getUser();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("uid", sysUsers.getUserId());
            params.put("ptpwd", password.trim());
            devUserService.updatePtPwd(params);

            // if (sysUsers.getRoleType().equals("dev")) {
            // 查询当前用户(开发者)，是否已经阅读了最新的公告
            isLookNews(sysUsers);
            // }
            //判断当前用户是否为业务员
            if(sysUsers.getType().equals("saler")){
                result.put("isSaler", true);
            }else{
                result.put("isSaler", false);
            }
            if (sysUsers.getStatus() == 1) {
                result.put(RESULT, true);
            } else {
                result.put(ERROR, "该账户尚未激活！");
                subject.logout();
                token.clear();
            }

        } catch (AuthenticationException e) {
            result.put(ERROR, "用户名或密码错误！");
            token.clear();
            getLog(this).error("登录失败错误信息:" + e);
        } catch (NullPointerException e) {
            result.put(ERROR, "用户未分配岗位！");
            token.clear();
            getLog(this).error("登录失败错误信息:" + e);
        }
        return result;
    }

    private void isLookNews(SysUsers sysUsers) {
        Map<String, Object> param = new HashMap<String, Object>();

        Integer noticeid = 0, next_noticeid = 0;
        boolean boo = true;
        param.put("userid", sysUsers.getUserId());
        param.put("startdate", DateUtils.get30DayBeforDate());
        param.put("enddate", DateUtils.getDate(DateUtils.getCurDate(), "yyyy-MM-dd"));

        News news_mc = newsServiceImpl.getMaxIdOrCount(param);

        param.put("noticeid", news_mc.getMaxid());

        for (int i = 0; i < news_mc.getCount(); i++) {
            param.put("flagOne", (i == 0) ? "flagOne" : null);
            noticeid = newsServiceImpl.getNoticeId(param);
            if (noticeid == null) {
                boo = i == 0 ? false : true;
                break;
            } else {
                next_noticeid = noticeid;
            }
            param.put("noticeid", noticeid == null ? next_noticeid : noticeid);
            // 如果循环遍历完，说明公告已被当前用户全部访问，则不作展示
            if (i >= news_mc.getCount() - 1) {
                return;
            }
        }
        //
        if (boo) {
            noticeid = newsServiceImpl.getNextMaxId(param);
            if (noticeid == null) {
                return;
            }
            param.put("noticeid", noticeid);
        }
        // 如果记录不存在（未阅读），显示公告
        sysUsers.setNews(newsServiceImpl.getNewestNotice(param));

    }

    @RequestMapping(value = "/logout.html", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        SecurityUtils.getSubject().logout();
        return redirect("login.html");
    }

    @RequestMapping(value = "/main.html", method = RequestMethod.GET)
    public String main(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        SysUsers user = getUser();
        String assortment = user.getAssortment();
        if (assortment.equals("customer")) {
            return "forward:/orderInput.html";
        } else {
            return "forward:/manager_index.html";
        }
    }
}
