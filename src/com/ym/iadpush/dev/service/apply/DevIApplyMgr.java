package com.ym.iadpush.dev.service.apply;

import java.util.List;
import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.dev.entity.DevApp;
import com.ym.iadpush.dev.entity.DevChannel;

/***
 * 应用管理接口
 * @author 陈贵勇
 *
 */
public interface DevIApplyMgr {

	/**
	 * 修改应用的状态
	 * @param params
	 * @return
	 */
	int updateAppStatus(Map<String, Object> params);

	List<DevChannel> selectChannel();

	DevApp selectAppById(Map<String, Object> params);

	Integer updatetApp(Map<String, Object> params);

	Integer insertApp(Map<String, Object> params);
	
}
