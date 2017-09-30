package com.ym.iadpush.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Earn;


public interface EarnMapper {
	List<Earn> findByEdate(@Param("edate") String edate, @Param("status") int status);
	
	int updateStatusByEdate(@Param("uid") int uid, @Param("edate") String edate,@Param("status") int status);
}
