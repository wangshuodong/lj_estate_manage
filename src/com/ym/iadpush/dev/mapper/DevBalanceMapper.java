package com.ym.iadpush.dev.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.Balance;


public interface DevBalanceMapper {
	
	
    List<Map<String,String>> countEarn(Map<String, Object> paramsMap);
    
  
    Map<String,String> sumEarn(Map<String, Object> paramsMap);
    
    int selectTotalRecord(Map<String, Object> paramsMap);
    
    List<Balance> findDevBalances(Map<String, Object> paramsMap);
    
    int SelectTotalDevBalances(Map<String, Object> paramsMap);
}
