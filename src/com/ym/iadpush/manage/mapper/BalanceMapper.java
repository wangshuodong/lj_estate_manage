package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.Balance;
import com.ym.iadpush.manage.entity.BalanceCollect;
import com.ym.iadpush.manage.entity.UserBalance;

public interface BalanceMapper {
	int batchInsert(List<Balance> balances);
	
    List<UserBalance> findUserBlances(Map<String, Object> paramsMap);
    
    int selectTotalRecord(Map<String, Object> paramsMap);
    

    BalanceCollect collectUserBlances(Map<String, Object> paramsMap);
    
    int updateStatus(Map<String, Object> paramsMap);
    
    int updateStatusByBatch(Map<String, Object> paramsMap);
    
    
}
