package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.AppStatement;
import com.ym.iadpush.manage.entity.DevStatement;
import com.ym.iadpush.manage.entity.DevStatementDetail;


public interface DevStatementMapper {

  
    List<DevStatement> queryDevStatement(Map<String,Object> params);

    int countDevStatement(Map<String,Object> params);
    

    DevStatement sumDevStatement(Map<String,Object> params);
    

    List<DevStatementDetail> queryDevStatementDetail(Map<String,Object> params);
    

    int countDevStatementDetail(Map<String,Object> params);
    

    DevStatementDetail  sumDevStatementDetail(Map<String,Object> params);

    List<AppStatement> queryAppStatement(Map<String,Object> params);
    
 
    int countQueryAppStatement(Map<String,Object> params);
    
    /**
     * 应用报表数据汇总
     * @param params
     * @return
     */
    AppStatement sumAppStatement(Map<String,Object> params);
}
