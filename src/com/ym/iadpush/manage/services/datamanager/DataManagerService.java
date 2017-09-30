package com.ym.iadpush.manage.services.datamanager;

import java.util.Map;

import com.ym.common.utils.QueryResult;


public interface DataManagerService {
    

    QueryResult queryDataStatement(Map<String,Object> params); 
    

    QueryResult queryDevStatement(Map<String,Object> params);
    

    QueryResult queryDevStatementDetail(Map<String,Object> params); 
    
    /**
     * 应用报表查询
     * @param params
     * @return
     */
    QueryResult queryAppStatement(Map<String,Object> params);

    

    int updateAppStatus(Map<String,Object> params);
    

    QueryResult dataListener(Map<String,Object> params);
    
}
