package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AwardMapper {
	List<Map<String, Object>> findUnSettleBonus(String date);
	
	int updateStatusByDate(@Param("uid") int uid, @Param("edate") String edate, @Param("status") int status);
}
