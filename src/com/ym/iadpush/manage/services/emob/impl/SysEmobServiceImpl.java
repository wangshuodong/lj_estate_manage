package com.ym.iadpush.manage.services.emob.impl;

import java.sql.SQLException;

import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.iadpush.common.utils.JdbcUtil;
import com.ym.iadpush.manage.entity.App;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.services.emob.SysEmobService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class SysEmobServiceImpl implements SysEmobService {

	public int insertUser(SysUsers users) {
		// TODO Auto-generated method stub
		if(users.getServiceId() == null){
			users.setServiceId(0);
		}
		String sql = " insert into iadpush_user(username,uid,serviceName,serviceId) values('"+users.getUsername()+"',"+users.getUserId()+",'"+users.getServiceName()+"',"+users.getServiceId()+")";
		int count = 0;
		try {
			count = JdbcUtil.executeUpdate(sql);
		} catch (SQLException e) {
			LogFactory.getLog(SysEmobServiceImpl.class).error("同步用户异常", e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			LogFactory.getLog(SysEmobServiceImpl.class).error("同步用户异常", e);
		}
		return count;
	}

	@Override
	public int insertApp(App app) {
		// TODO Auto-generated method stub
		String sql = " insert into iadpush_app(appid,qid,uid,appname) values("+app.getAid()+","+app.getQid()+","+app.getUid()+",'"+app.getAppname()+"')";
		int count = 0;
		try {
			count = JdbcUtil.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			LogFactory.getLog(SysEmobServiceImpl.class).error("同步数据导易盟失败", e);
		}
		return count;
	}

	
}
