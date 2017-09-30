/**
 * 
 */
package com.ym.iadpush.manage.services.version;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.VersionManage;


public interface IVersionManageService {

	List<VersionManage> getAllVersionManage(Map<String, Object> paramMap);

	int getAllCountVersionManage();

	int deleteVersionManage(Map<String, Object> paramMap);

	Integer insertVersionManage(Map<String, Object> paramMap);

	int deleteVersionManageVersionid(Map<String, Object> paramMap);

	VersionManage selectVersionManageByAppkey(Map<String, Object> paramMap);


}
