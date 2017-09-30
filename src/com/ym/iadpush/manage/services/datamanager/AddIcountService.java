package com.ym.iadpush.manage.services.datamanager;

import java.util.Map;

import com.ym.common.utils.QueryResult;


public interface AddIcountService {
	

	QueryResult query(Map<String,Object> params);
	

	void addAmount(String[] ids,String type,String startDate,String endDate,String[] hours,String amount,int cuid,String addType);
	
}
