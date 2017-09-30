/**
 * 
 */
package com.ym.iadpush.manage.services.version;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.VersionKit;


public interface IVersionKitService {

	int getAllCountVersionKit();

	List<VersionKit> getAllVersionKit(Map<String, Object> paramMap);

	VersionKit selectVersionKit_detail(Map<String, Object> paramMap);

	int updateVersionKit(Map<String, Object> paramMap);

	int deleteVersionKit(Map<String, Object> paramMap);

	int insertVersionKit(Map<String, Object> paramMap);

	Integer getMaxId();

}
