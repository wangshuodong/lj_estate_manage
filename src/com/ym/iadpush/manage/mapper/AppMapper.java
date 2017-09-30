package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.App;

public interface AppMapper {
    List<App> getAllApp(Map<String, Object> params);
    
    List<App> getAllAppForDev(Map<String, Object> params);

	int getCountApp(Map<String, Object> params);
	
	int getCountAppForDev(Map<String, Object> params);

	int updateAppStatus(Map<String, Object> params);
	
	List<App> queryFormApp(Map<String, Object> params);
	
	int countFormApp(Map<String, Object> params);

	App getTotalEarnAndIcount(Map<String, Object> params);
	
	App getTotalEarnAndIcountForApp(Map<String, Object> params);

	Integer updateGreenYellow(Map<String, Object> params);
	
}
