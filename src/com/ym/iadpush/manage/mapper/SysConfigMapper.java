package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.SysConfig;

public interface SysConfigMapper {
    
    String selectByKey(String key);

	List<SysConfig> getAllSysConfig();

	int updateSysConfig(Map<String, Object> params);
	
	String getValuesByName(String name);
}
