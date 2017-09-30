package com.ym.iadpush.dev.service.app;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.App;


public interface DevAppService {
	
	/**
	 * 查询所有用户下未暂停广告的App
	 * @Author TULIANGCHENG 2014-5-13 上午11:55:59
	 * @return
	 */
	List<App> queryByUsers(Map<String,Object> params);
	
	/**
	 * 根据AppId 查询出App
	 * @Author TULIANGCHENG 2014-5-13 上午11:57:45
	 * @param params
	 * @return
	 */
	List<App> queryByApps(Map<String,Object> params);

}
