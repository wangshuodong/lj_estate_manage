package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.DataReport;
import com.ym.iadpush.manage.entity.DataStatement;


public interface DataManagerMapper {

 
    List<Object> queryDataStatement(Map<String, Object> params);


    int countDataStatement(Map<String, Object> params);
    
 
    DataStatement sumDataStatement(Map<String, Object> params);
  
    List<DataReport> dataReportSelect(Map<String, Object> params);
    
 
    int dataReportSelectCount(Map<String, Object> params);
    

}
