/**
 * 
 */
package com.ym.iadpush.manage.services.tissue;

import java.util.List;
import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.MemberLevel;


public interface MemberLevelService {
    
    QueryResult query(Map<String,Object> params);
    
    List<MemberLevel> getAll();
    
    int insert(MemberLevel m);
    
    int update(MemberLevel m);
    
    MemberLevel findById(int id);
    
    int deleteById(int id);

}
