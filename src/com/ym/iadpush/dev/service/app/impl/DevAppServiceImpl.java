package com.ym.iadpush.dev.service.app.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.dev.mapper.DevAppMapper;
import com.ym.iadpush.dev.service.app.DevAppService;
import com.ym.iadpush.manage.entity.App;

@Service
public class DevAppServiceImpl implements DevAppService {
	
	@Autowired
	private DevAppMapper devAppMapper;

	@Override
	public List<App> queryByUsers(Map<String, Object> params) {
		return devAppMapper.queryByUsers(params);
	}

	@Override
	public List<App> queryByApps(Map<String, Object> params) {
		return devAppMapper.queryByApps(params);
	}
	

}
