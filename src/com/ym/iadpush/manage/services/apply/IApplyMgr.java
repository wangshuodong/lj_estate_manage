package com.ym.iadpush.manage.services.apply;

import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.App;


public interface IApplyMgr {

	/***
	 * 查询所有的记录和总条数
	 * @param params
	 * @return 
	 */
	QueryResult queryDataStatement(Map<String, Object> params);
	
	/**
	 * 修改应用的状态
	 * @param params
	 * @return
	 */
	int updateAppStatus(Map<String, Object> params);
	

	QueryResult queryAllApp(Map<String,Object> params);
	/**
	 * 汇总
	 * @param params
	 * @return
	 */
	App getTotalEarnAndIcount(Map<String, Object> params);
	

	App getTotalEarnAndIcountForDev(Map<String, Object> params);
	
	/***
	 * 查询所有的记录和总条数
	 * @param params
	 * @return 
	 */
	QueryResult queryDataStatementForDev(Map<String, Object> params);

	Integer updateGreenYellow(Map<String, Object> params);
	
}
