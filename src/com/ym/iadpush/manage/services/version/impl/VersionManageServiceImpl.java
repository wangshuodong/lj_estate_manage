/**
 * 
 */
package com.ym.iadpush.manage.services.version.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.iadpush.manage.entity.VersionManage;
import com.ym.iadpush.manage.mapper.VersionManageMapper;
import com.ym.iadpush.manage.services.version.IVersionManageService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class VersionManageServiceImpl implements IVersionManageService {
	
	private @Autowired
    VersionManageMapper versionManageMapper;
	
	@Override
	public List<VersionManage> getAllVersionManage(Map<String, Object> paramMap) {
		return versionManageMapper.getAllVersionManage(paramMap);
	}

	@Override
	public int getAllCountVersionManage() {
		return versionManageMapper.getAllCountVersionManage();
	}

	@Override
	public int deleteVersionManage(Map<String, Object> paramMap) {
		return versionManageMapper.deleteVersionManage(paramMap);
	}

	@Override
	public Integer insertVersionManage(Map<String, Object> paramMap) {
		return versionManageMapper.insertVersionManage(paramMap);
	}

	@Override
	public int deleteVersionManageVersionid(Map<String, Object> paramMap) {
		return versionManageMapper.deleteVersionManageVersionid(paramMap);
	}

	@Override
	public VersionManage selectVersionManageByAppkey(
			Map<String, Object> paramMap) {
		return versionManageMapper.selectVersionManageByAppkey(paramMap);
	}


    

}
