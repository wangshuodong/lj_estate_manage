package com.ym.iadpush.dev.service.apply.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.dev.entity.DevApp;
import com.ym.iadpush.dev.entity.DevChannel;
import com.ym.iadpush.dev.mapper.DevApplyMapper;
import com.ym.iadpush.dev.service.apply.DevIApplyMgr;
import com.ym.iadpush.manage.entity.App;
import com.ym.iadpush.manage.services.emob.SysEmobService;

@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
public class DevApplyMgrImpl implements DevIApplyMgr {

	@Autowired
    private DevApplyMapper devApplyMapper;
	@Autowired
	private SysEmobService emobService;

	@Override
	public int updateAppStatus(Map<String, Object> params) {
		return devApplyMapper.updateAppStatus(params);
	}

	@Override
	public List<DevChannel> selectChannel() {
		return devApplyMapper.selectChannel();
	}

	@Override
	public DevApp selectAppById(Map<String, Object> params) {
		return devApplyMapper.selectAppById(params);
	}

	@Override
	public Integer updatetApp(Map<String, Object> params) {
		return devApplyMapper.updatetApp(params);
	}

	@Override
	public Integer insertApp(Map<String, Object> params) {
		System.out.println(params.get("id"));
		int appid = devApplyMapper.insertApp(params);
		try {
			System.out.println(params.get("id"));
			//同步
			DevApp app = devApplyMapper.selectAppById(params);
			App eapp = new App();
			eapp.setAid(app.getAid());
			eapp.setAppname(app.getAppname());
			eapp.setUid(app.getUid());
			eapp.setQid(app.getQid());
			emobService.insertApp(eapp);
		} catch (Exception e) {
			// TODO: handle exception
			LogFactory.getLog(DevApplyMgrImpl.class).error("同步app数据到易盟异常", e);
		}
		return appid;
	}

}
