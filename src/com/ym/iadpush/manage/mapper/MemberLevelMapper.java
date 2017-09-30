/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.MemberLevel;

public interface MemberLevelMapper {
    
    List<MemberLevel> query(Map<String,Object> params);
    
    int countByQuery(Map<String,Object> params);
    
    List<MemberLevel> queryAll();
    
    int insert(MemberLevel m);
    
    int updte(MemberLevel m);
    
    MemberLevel findById(@Param("id")int id);
    
    int deleteById(@Param("id")int id);

}
