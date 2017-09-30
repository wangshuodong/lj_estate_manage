package com.ym.iadpush.manage.services.datamanager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.mapper.KVReportMapper;
import com.ym.iadpush.manage.services.datamanager.KVReportService;

@Service
public class KVReportServiceImpl implements KVReportService {
	
	@Autowired
	private KVReportMapper kvReportMapper;

	@Override
	public List<Map<String, String>> queryKVReport(Map<String, Object> paramsMap) {
		return kvReportMapper.queryKVReport(paramsMap);
	}

	@Override
	public int countWithKVReport(Map<String, Object> paramsMap) {
		return kvReportMapper.countWithKVReport(paramsMap);
	}

	@Override
	public Map<String,String> collectKVReport(
			Map<String, Object> paramsMap) {
		return kvReportMapper.collectKVReport(paramsMap);
	}

}
