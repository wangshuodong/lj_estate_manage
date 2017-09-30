package com.ym.iadpush.manage.services.sysconfig.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.iadpush.manage.entity.SysConfig;
import com.ym.iadpush.manage.mapper.SysConfigMapper;
import com.ym.iadpush.manage.services.sysconfig.ISysConfig;

@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
public class SysConfigImpl implements ISysConfig {
	
	@Autowired
    private SysConfigMapper sysConfigMapper;
	
	@Override
	public List<SysConfig> getAllSysConfig() {
		return sysConfigMapper.getAllSysConfig();
	}

	@Override
	public int updateSysConfig(Map<String, Object> params) {
		return sysConfigMapper.updateSysConfig(params);
	}

	public String getValueByName(String name) {
		return sysConfigMapper.getValuesByName(name);
	}
	

}
