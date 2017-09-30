/**
 * 
 */
package com.ym.iadpush.manage.services.version;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.VersionSdk;


public interface IVersionSdkService {
    /**
     * 修改version_sdk
     * 
     * @Author LiXingBiao 2014-4-11 下午2:23:48
     * @param versionSdk
     * @return
     * @throws Exception
     */
    public int updateVersionSdk(VersionSdk versionSdk) throws Exception;

    /**
     * 
     * @Author LiXingBiao 2014-4-11 下午2:24:02
     * @param map
     * @return
     * @throws Exception
     */
    public int selectTotalRecord(Map<String, Object> map) throws Exception;

    /**
     * 通过主键ID删除VersionSdk(sdk版本记录)
     * 
     * @Author LiXingBiao 2014-4-10 下午2:06:07
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteVersionSdkByPrimaryKey(Map<String, Object> paramMap) throws Exception;

    /**
     * 保存versionSdk
     * 
     * @Author LiXingBiao 2014-4-10 下午2:07:04
     * @param versionSdk
     * @return
     * @throws Exception
     */
    public int insertVersionSdk(VersionSdk versionSdk) throws Exception;

    /**
     * 通过主键ID查询VersionSdk(sdk版本记录)
     * 
     * @Author LiXingBiao 2014-4-10 下午2:07:41
     * @param param
     * @return
     * @throws Exception
     */
    public VersionSdk selectVersionSdkByPrimaryKey(Map<String, Object> param) throws Exception;

    /**
     * 通过参数集合查询VersionSdk集合
     * 
     * @Author LiXingBiao 2014-4-10 下午2:07:58
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectVersionSdkByParamMap(Map<String, Object> map) throws Exception;

    /**
     * 通过ids 查询VersionSdk集合
     * 
     * @Author LiXingBiao 2014-4-10 下午2:08:41
     * @param ids
     * @return
     * @throws Exception
     */
    public List<VersionSdk> selectVersionSdkListByIds(@Param(value = "ids") String ids) throws Exception;

	public List<VersionSdk> selectAllVersionKit(Map<String, Object> paramMap);


}
