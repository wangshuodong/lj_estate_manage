package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;


public interface KVReportMapper {
	

	public List<Map<String,String>> queryKVReport(Map<String, Object> paramsMap);
	

	public int countWithKVReport(Map<String, Object> paramsMap);

	public Map<String,String> collectKVReport(Map<String, Object> paramsMap);

}
