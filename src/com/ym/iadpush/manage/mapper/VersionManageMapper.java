package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.App;
import com.ym.iadpush.manage.entity.VersionKit;
import com.ym.iadpush.manage.entity.VersionManage;

public interface VersionManageMapper {

	int getAllCountVersionManage();

	List<VersionManage> getAllVersionManage(Map<String, Object> paramMap);

	int deleteVersionManage(Map<String, Object> paramMap);

	Integer insertVersionManage(Map<String, Object> paramMap);
	
	Integer deleteVersionManageVersionid(Map<String, Object> paramMap);

	VersionManage selectVersionManageByAppkey(Map<String, Object> paramMap);

}
