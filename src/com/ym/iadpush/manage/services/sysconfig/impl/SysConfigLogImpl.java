package com.ym.iadpush.manage.services.sysconfig.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.iadpush.manage.entity.SysConfigLog;
import com.ym.iadpush.manage.mapper.SysConfigLogMapper;
import com.ym.iadpush.manage.services.sysconfig.ISysConfigLog;
@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=java.lang.Exception.class)
public class SysConfigLogImpl implements ISysConfigLog {
    private @Autowired SysConfigLogMapper logMapper;
	
	public int insert(SysConfigLog confinLog) {
		return logMapper.insert(confinLog);
	}

	public List<SysConfigLog> getAllConfigLog(Map params) {
		return logMapper.getAllConfigLog(params);
	}

	public int totalCount(Map params) {
		return logMapper.totalCount(params);
	}

}
