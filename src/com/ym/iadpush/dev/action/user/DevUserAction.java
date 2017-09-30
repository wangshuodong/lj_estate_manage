package com.ym.iadpush.dev.action.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
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
import com.ym.common.utils.MD5Encrypt;
import com.ym.iadpush.dev.service.user.DevUserService;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.sysconfig.ISysConfig;


@Controller
public class DevUserAction extends BaseAction {
    
    @Autowired
    private DevUserService devUserService;
    
    @Autowired
    private ISysConfig iSysConfig;
    
    private static String FLODER = "user";
    
    @ResponseBody
    @RequestMapping(value="/checkishaveUser.do",method=RequestMethod.POST)
    public ModelMap checkishaveUser(HttpServletRequest request, ModelMap model){
    	String userName = request.getParameter("username");
    	String userMail = request.getParameter("email");
    	SysUsers sysUsers = devUserService.findByUserName(userName);
    	
    	if(userName == null || userName.trim().equalsIgnoreCase("")){
    		model.put("msg", "输入不允许为空");
    	}else if(userMail == null || userMail.trim().equalsIgnoreCase("")){
    		model.put("msg", "邮箱不能为空");
    	}else if(sysUsers == null){
    		model.put("msg", "用户不存在");
    	}else if(sysUsers != null){
    		String email = sysUsers.getEmail();
    		if(email == null){
    			model.put("msg", "很抱歉，你注册时未填写邮箱，请联系客服重置密码");
    		}else if(!userMail.trim().equalsIgnoreCase(email)){
    			model.put("msg", "很抱歉，未找到匹配用户");
    		}else{
    			boolean status = devUserService.resetpassword(sysUsers, request);
    			if(status){
    				model.put("msg", "密码重置成功,请登录邮箱查看");
    				model.put("ishaveUser", "yes");
    			}else{
    				model.put("msg", "密码重置失败,请联系管理员");
    			}
    		}
    	}
    	return model;
    }
    
    @ResponseBody
    @RequestMapping(value="/regist.do",method=RequestMethod.POST)
    public ModelMap register(HttpServletRequest request, ModelMap model){
        
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String phone = request.getParameter("phone");
        String qq = request.getParameter("qq");
        String email = request.getParameter("email");
        String nick = request.getParameter("nick");
        String bankUserName = request.getParameter("bankUserName");
        String bankName = request.getParameter("bankName");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String bankAddress = request.getParameter("bankAddress");
        String bankNo = request.getParameter("bankNo");
        String vcode = request.getParameter("vcode");
        String agree = request.getParameter("agree");
        
        SysUsers users = new SysUsers();
        users.setPtpwd(password);
        users.setUsername(username.trim());
        users.setPassword(MD5Encrypt.MD5(password.trim()));
        users.setPhone(phone.trim());
        users.setQq(qq.trim());
        users.setEmail(email);
        users.setNick(nick);
        users.setBankName(bankName);
        users.setCity(city);
        users.setBankAddress(bankAddress);
        users.setBankNo(bankNo);
        users.setUpdateDate(new Date());
        users.setCreateDate(new Date());
        users.setProvince(province);
        users.setType("dev");
        users.setBankUserName(bankUserName);
        
        try {
        	users.setRate(Double.valueOf(iSysConfig.getValueByName("defaultRate")));
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        boolean exists = devUserService.exists(username);
        
        String code = (String) this.getSession().getAttribute("checkcode"); 
        
        if(exists){
            model.put("status", false);
            model.put("message", "用户已存在");
            return model;
        }else if(agree == null || agree.trim().equalsIgnoreCase("")){
        	model.put("status", false);
            model.put("message", "请先阅读网站服务条款");
            return model;
        }/*else if(bankNo == null || bankNo.trim().equalsIgnoreCase("")){
        	model.put("status", false);
            model.put("message", "银行账号不能为空");
            return model;
        }*/else if(username == null || username.trim().equalsIgnoreCase("")){
            model.put("status", false);
            model.put("message", "用户名不能为空");
            return model;
        }else if(username != null && username.trim().length() < 4){
            model.put("status", false);
            model.put("message", "请输入长度介于4~20字符的用户名");
            return model;
        }else if(username != null && username.trim().length() > 20){
            model.put("status", false);
            model.put("message", "请输入长度介于6~20字符的用户名");
            return model;
        }else if(password == null || password.trim().equalsIgnoreCase("")){
            model.put("status", false);
            model.put("message", "密码不能为空");
            return model;
        }else if(password != null && password.trim().length() < 6 ){
            model.put("status", false);
            model.put("message", "请输入长度介于6~20字符的密码");
            return model;
        }else if(password != null && password.trim().length() > 20 ){
            model.put("status", false);
            model.put("message", "请输入长度介于6~20字符的密码");
            return model;
        }else if(password2 == null || password2.trim().equalsIgnoreCase("")){
            model.put("status", false);
            model.put("message", "请确认密码");
            return model;
        }else if(!password.trim().equalsIgnoreCase(password2.trim())){
            model.put("status", false);
            model.put("message", "两次输入的密码不一致");
            return model;
        }else if(vcode == null || vcode.trim().equalsIgnoreCase("")){
            model.put("status", false);
            model.put("message", "验证码不能为空");
            return model;
        }else if(!vcode.trim().equalsIgnoreCase(code)){
            model.put("status", false);
            model.put("message", "验证码输入不正确");
            return model;
        }else{
            devUserService.register(users,request);
            model.put("status", true);
            model.put("message", "注册成功,请通过邮件激活,如未收到邮件请联系管理员协助激活");
        }
        
        return model;
    }
    

    @ResponseBody
    @RequestMapping(value="/update_pwd.html",method=RequestMethod.POST)
    public ModelMap updatePwd(HttpServletRequest request, ModelMap model){
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        String confirmPwd = request.getParameter("confirmPwd");
        
        if(oldPwd == null || oldPwd.trim().length() < 6 || oldPwd.trim().length() > 20){
            model.put("pwdMessage", "请输入6~20之间的密码");
            model.put("status", false);
            return model;
        }else if(newPwd == null || newPwd.trim().length() < 6 || newPwd.trim().length() > 20){
            model.put("pwdMessage", "请输入6~20之间的密码");
            model.put("status", false);
            return model;
        }else if(confirmPwd == null || confirmPwd.trim().length() < 6 || confirmPwd.trim().length() > 20){
            model.put("pwdMessage", "请输入6~20之间的密码");
            model.put("status", false);
            return model;
        }else if(oldPwd != null && !this.getUser().getPassword().trim().equalsIgnoreCase(MD5Encrypt.MD5(oldPwd.trim()))){
            model.put("pwdMessage", "密码输入错误");
            model.put("status", false);
            return model;
        }else if(!newPwd.trim().equalsIgnoreCase(confirmPwd.trim())){
            model.put("pwdMessage", "两次输入的不一致");
            model.put("status", false);
            return model;
        }else if(newPwd.trim().equalsIgnoreCase(oldPwd.trim())){
            model.put("pwdMessage", "新密码不能和旧密码一样");
            model.put("status", false);
        }else{
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("password", MD5Encrypt.MD5(newPwd.trim()));
            params.put("updatetime", new Timestamp(System.currentTimeMillis()));
            params.put("userId", this.getUser().getUserId());
            devUserService.updateUserInfo(params);
            this.getUser().setPassword(MD5Encrypt.MD5(newPwd.trim()));
            model.put("pwdMessage", "密码修改成功");
            model.put("status", true);
        }
        
        return model;
    }
    
   
    @RequestMapping(value="/user_active.html",method=RequestMethod.GET)
    public String active(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException{
    	
    	String userName = request.getParameter("username");
    	String email = request.getParameter("email");
    	
    	SysUsers user = devUserService.findByUserName(URLDecoder.decode(userName, "utf-8"));
    	if(user != null && user.getStatus() == 0 && user.getEmail().trim().equalsIgnoreCase(email.trim())){
    		user.setStatus(1);
    		devUserService.updateUserByKey(user);
    		return this.redirect("/assets/1.0.0/paging/login.html?active=true");
    	}else if(user != null && user.getStatus() == 1){
    		return this.redirect("/assets/1.0.0/paging/login.html?active=haven");
    	}else{
    		return this.redirect("/assets/1.0.0/paging/login.html?active=false");
    	}
    	
    	
    }
    
   
    private Map<String,Object> getAccountParams(HttpServletRequest request){
        Map<String,Object> params = new HashMap<String, Object>();
        String bankName = request.getParameter("bankName");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String bankAddress = request.getParameter("bankAddress");
        String bankUserName = request.getParameter("bankUserName");
        String bankNo = request.getParameter("bankNo");
        String certificate = request.getParameter("certificate");
        String certification = request.getParameter("certification");
        
        params.put("bankName", bankName);
        params.put("province", province);
        params.put("city", city);
        params.put("bankAddress", bankAddress);
        params.put("name", bankUserName);
        params.put("bankNo", bankNo);
        params.put("certificate", certificate);
        params.put("updatetime", new Timestamp(System.currentTimeMillis()));
        if(certification != null && certification.trim().equalsIgnoreCase("upload")){
            params.put("certification",1);
        }
        params.put("userId", this.getUser().getUserId());
        return params;
    }
    

    @RequestMapping(value="/update_dev_account_info.html",method=RequestMethod.POST)
    public String submitAccountInfo(HttpServletRequest request, ModelMap model){
        
        Map<String,Object> params = this.getAccountParams(request);
        devUserService.updateUserInfo(params);
        return jumpToAccountInfo(request, model);
    }
    
    

    @RequestMapping(value="/dev_acount_info.html",method=RequestMethod.GET)
    public String jumpToAccountInfo(HttpServletRequest request, ModelMap model){
        model.put("userinfo", devUserService.findById(getUser().getUserId()));
        return AD_HTML + FLODER + "/account_info";
    }
    

    private Map<String,Object> getParams(HttpServletRequest request){
        Map<String,Object> params = new HashMap<String, Object>();
        String name = request.getParameter("nick");
        String bankUserName = request.getParameter("bankUserName");
        String idcard = request.getParameter("idcard");
        String phone = request.getParameter("phone");
        String qq = request.getParameter("qq");
        
        params.put("name", name);
        params.put("idcard", idcard);
        params.put("phone", phone);
        params.put("qq", qq);
        params.put("bankUserName", bankUserName);
        params.put("userId", this.getUser().getUserId());
        
        return params;
    }
    

    private boolean validateInfo(Map<String,Object> params){
        Map<String,Object> map = new HashMap<String, Object>();
        if(params.get("name") == null || String.valueOf(params.get("name")).trim().equalsIgnoreCase("")){
            return false;
        }
        if(params.get("idcard") == null || String.valueOf(params.get("idcard")).trim().equalsIgnoreCase("") ||  String.valueOf(params.get("idcard")).trim().length() > 20){
            return false;
        }
        if(params.get("phone") == null || String.valueOf(params.get("phone")).trim().equalsIgnoreCase("") || String.valueOf(params.get("phone")).trim().length() > 11 ){
            return false;
        }
        if(params.get("qq") == null || String.valueOf(params.get("qq")).trim().equalsIgnoreCase("") || String.valueOf(params.get("qq")).trim().length() > 11 ){
            return false;
        }
        return true;
    }
    

    @RequestMapping(value="/update_dev_user_info.html",method=RequestMethod.POST)
    public String submitInfo(HttpServletRequest request, ModelMap model){
        
        Map<String,Object> params = this.getParams(request);
        
        //boolean isvalidate = this.validateInfo(params);
        boolean isvalidate = true;
        
        if(isvalidate){
            devUserService.updateUserInfo(params);
            SysUsers users = this.getUser();
            if(params.get("name") != null){
                users.setNick(String.valueOf(params.get("name")));
            }
            if(params.get("idcard") != null){
                users.setCertificate(String.valueOf(params.get("idcard")));
            }
            if(params.get("phone") != null){
                users.setPhone(String.valueOf(params.get("phone")));
            }
            if(params.get("qq") != null){
                users.setQq(String.valueOf(params.get("qq")));
            }
            model.put("userinfo", devUserService.findById(this.getUser().getUserId()));
            model.put("message", "保存成功");
        }else{
            model.put("userinfo", devUserService.findById(this.getUser().getUserId()));
            model.put("message", "<span style='color:red'>请检查你的输入是否合法</span>");
        }
        
        return AD_HTML + FLODER + "/user_info";
    }
    

    @RequestMapping(value="/dev_user_info.html",method=RequestMethod.GET)
    public String jumpToUserInfo(HttpServletRequest request, ModelMap model){
        model.put("userinfo", devUserService.findById(getUser().getUserId()));
        return AD_HTML + FLODER + "/user_info";
    }


    public DevUserService getDevUserService() {
        return devUserService;
    }


    public void setDevUserService(DevUserService devUserService) {
        this.devUserService = devUserService;
    }

}
