package com.ym.iadpush.dev.service.user.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.MD5Encrypt;
import com.ym.common.utils.RandomUtil;
import com.ym.iadpush.common.utils.EmailUtil;
import com.ym.iadpush.common.utils.SysConfigPropertiesHelper;
import com.ym.iadpush.dev.mapper.DevUsersMapper;
import com.ym.iadpush.dev.service.user.DevUserService;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.emob.SysEmobService;

@Service
public class DevUserServiceImpl implements DevUserService {
    
    @Autowired
    private DevUsersMapper devUsersMapper;
    @Autowired
    private  SysEmobService emobService;
    

    public int updateUserInfo(Map<String, Object> map) {
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("userId", map.get("userId"));
    	params.put("id", map.get("userId"));
    	SysUsers user = devUsersMapper.queryUsers(params);
    	if(user == null){
    		return 0;
    	}else if(user.getBankName() != null && !user.getBankName().trim().equalsIgnoreCase("请选择") && !user.getBankName().trim().equalsIgnoreCase("")){
    		map.put("bankName", user.getBankName());
    	}else if(user.getProvince() != null && !user.getProvince().trim().equalsIgnoreCase("")){
    		map.put("province", user.getProvince());
    	}else if(user.getCity() != null && !user.getCity().trim().equalsIgnoreCase("")){
    		map.put("city", user.getCity());
    	}else if(user.getBankAddress() != null && !user.getBankAddress().trim().equalsIgnoreCase("")){
    		map.put("bankAddress", user.getBankAddress());
    	}else if(user.getBankName() != null && !user.getBankName().trim().equalsIgnoreCase("")){
    		map.put("bankName", user.getBankName());
    	}else if(user.getBankNo() != null && !user.getBankNo().trim().equalsIgnoreCase("")){
    		map.put("bankNo", user.getBankNo());
    	}else if(user.getCertificate() != null && !user.getCertificate().trim().equalsIgnoreCase("")){
    		map.put("certificate", user.getCertificate());
    	}else if(user.getCertification() != null && user.getCertification().trim().equalsIgnoreCase("2")){
    		map.put("certification", 2);
    	}else if(user.getBankUserName() != null && !user.getBankUserName().trim().equalsIgnoreCase("")){
    		map.put("bankUserName", user.getBankUserName());
    	}
        return devUsersMapper.updateUserInfo(map);
    }

    public DevUsersMapper getDevUsersMapper() {
        return devUsersMapper;
    }

    public void setDevUsersMapper(DevUsersMapper devUsersMapper) {
        this.devUsersMapper = devUsersMapper;
    }

    public SysUsers findById(int id) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return devUsersMapper.queryUsers(params);
    }

    public void register(SysUsers users,HttpServletRequest request) {
        users.setStatus(0);
        //插入用户
        devUsersMapper.register(users);
        SysUsers newuser = devUsersMapper.selectByUserName(users.getUsername());
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("userId", newuser.getUserId());
        params.put("roleId", 2);
        devUsersMapper.addToRole(params);
        
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
		String encode="";
		try {
		encode = URLEncoder.encode(users.getUsername(),  "utf-8");
		emobService.insertUser(users);
		} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
				LogFactory.getLog(DevUserServiceImpl.class).info("用户注册时异常", e1);
		}
        String message="尊敬的开发者"+users.getUsername()+"：<br/>   非常感谢您对iadpush广告平台的支持，您已成功注册成为我们的用户！<br/>您现在只需点击以下链接来验证您的邮箱, 激活您的帐户。<br/>" +
				"<a href='"+basePath+"user_active.html?username="+encode+"&email="+users.getEmail()+"'>"+basePath+"user_active.html?username="+encode+"&email="+users.getEmail()+"</a>";
        
		//注册时用户通知邮件
		try {
			EmailUtil.sendCommonEmail(users.getEmail(), "iadpush平台邮箱验证激活", message);
			EmailUtil.sendCommonEmail(SysConfigPropertiesHelper.getProperty("email_for_manager"), "iadpush新用户注册通知", managerMessage(users.getUsername(),users.getEmail(),users.getQq(),users.getPhone()));

		} catch (EmailException e) {
			// TODO Auto-generated catch block
			LogFactory.getLog(DevUserServiceImpl.class).info("邮件发送异常", e);
		}
		//注册时管理员通知邮件
    }
    
    private String managerMessage(String userName,String email,String qq,String phone){
		String content = SysConfigPropertiesHelper.getProperty("email_for_manager_content");
		return content.replaceAll("USERNAME_CONFIG", userName).replaceAll("EMAIL_CONFIG", email).replaceAll("QICQ_CONFIG", qq).replaceAll("PHONE_CONFIG", phone);
	}

    public boolean exists(String userName) {
        SysUsers user= devUsersMapper.selectByUserName(userName);
        if(user == null){
            return false;
        }else{
            return true;
        }
    }

	public SysUsers findByUserName(String userName) {
		return devUsersMapper.selectByUserName(userName);
	}

	public int updateUserByKey(SysUsers sysUsers) {
		return devUsersMapper.updateByPrimaryKeySelective(sysUsers);
	}

	public boolean resetpassword(SysUsers users,HttpServletRequest request) {
		
		String pwd = RandomUtil.getRandom(12);
		
		users.setPassword(MD5Encrypt.MD5(pwd));
		
		devUsersMapper.updateByPrimaryKeySelective(users);
		
		String basePath = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";
		  String message="尊敬的开发者"+users.getUsername()+"：<br/>   非常感谢您对iadpush广告平台的支持，您的密码已重置,请立即登录并修改密码！<br/>你的密码为:。<br/>" + pwd +",请点击如下地址登录并修改密码"+
					"<a href='"+basePath+"login.html'>"+basePath+"login.html"+"</a>";
	        
			//注册时用户通知邮件
			try {
				EmailUtil.sendCommonEmail(users.getEmail(), "iadpush平台密码重置邮件", message);

			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		return true;
	}

	
	public List<SysUsers> queryAllUsers() {
		return devUsersMapper.queryAllUsers();
	}

	@Override
	public int updatePtPwd(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return devUsersMapper.updatePtPwd(map);
	}
    
    

}
