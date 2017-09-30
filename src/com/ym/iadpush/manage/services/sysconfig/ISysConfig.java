package com.ym.iadpush.manage.services.sysconfig;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.SysConfig;


public interface ISysConfig {

	/**
	 * 查询所有的配置信息
	 * @return
	 */
	List<SysConfig> getAllSysConfig();

	int updateSysConfig(Map<String, Object> params);
	
	String getValueByName(String name);
	
}
