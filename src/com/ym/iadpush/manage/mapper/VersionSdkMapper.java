/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.VersionPie;
import com.ym.iadpush.manage.entity.VersionSdk;


public interface VersionSdkMapper {

    List<Map<String, Object>> selectVersionSdkByParamMap(Map<String, Object> map) throws Exception;

    int insert(VersionSdk record) throws Exception;

    int insertSelective(VersionSdk record) throws Exception;

    int selectTotalRecord(Map<String, Object> map) throws Exception;


    VersionSdk selectVersionSdkByPrimaryKey(Map<String, Object> param) throws Exception;

    int updateVersionSdkByPrimaryKeySelective(VersionSdk record);
    

    List<VersionSdk> getVersionSdk(Map<String, Object> paramMap);

    List<VersionSdk> selectAllVersionKit(Map<String, Object> paramMap);

    List<VersionPie> queryForPie(Map<String, Object> map);
    
    Integer deleteVersionSdk(Map<String, Object> map);

	int deleteVersionSdkByPrimaryKey(Map<String, Object> paramMap);
}
