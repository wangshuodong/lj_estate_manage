/**
 * 
 */
package com.ym.iadpush.manage.services.version.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.iadpush.manage.entity.VersionSdk;
import com.ym.iadpush.manage.mapper.VersionSdkMapper;
import com.ym.iadpush.manage.services.version.IVersionSdkService;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class VersionSdkServiceImpl implements IVersionSdkService {

    private @Autowired
    VersionSdkMapper versionSdkMapper;

    @Override
    public List<Map<String, Object>> selectVersionSdkByParamMap(Map<String, Object> map) throws Exception {
        List<Map<String, Object>> list = versionSdkMapper.selectVersionSdkByParamMap(map);
        return list;
    }

    @Override
    public int deleteVersionSdkByPrimaryKey(Map<String, Object> paramMap) throws Exception {
    	return versionSdkMapper.deleteVersionSdkByPrimaryKey(paramMap);
    }

    @Override
    public int insertVersionSdk(VersionSdk versionSdk) throws Exception {
        versionSdkMapper.insert(versionSdk);
        return 0;
    }

    @Override
    public VersionSdk selectVersionSdkByPrimaryKey(Map<String, Object> param) throws Exception {
        VersionSdk versionSdk = versionSdkMapper.selectVersionSdkByPrimaryKey(param);
        return versionSdk;
    }

    @Override
    public List<VersionSdk> selectVersionSdkListByIds(String ids) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ym.iadpush.manage.services.version.IVersionSdkService#selectTotalRecord(java.util.Map)
     */
    @Override
    public int selectTotalRecord(Map<String, Object> map) throws Exception {
        int totalRecord = versionSdkMapper.selectTotalRecord(map);
        return totalRecord;
    }

    @Override
    public int updateVersionSdk(VersionSdk versionSdk) throws Exception {
        int sign = versionSdkMapper.updateVersionSdkByPrimaryKeySelective(versionSdk);
        return sign;
    }

	@Override
	public List<VersionSdk> selectAllVersionKit(Map<String, Object> paramMap) {
		return versionSdkMapper.selectAllVersionKit(paramMap);
	}


}
