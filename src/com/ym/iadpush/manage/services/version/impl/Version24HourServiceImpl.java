package com.ym.iadpush.manage.services.version.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.VersionPie;
import com.ym.iadpush.manage.mapper.VersionSdkMapper;
import com.ym.iadpush.manage.services.version.Version24HourService;

@Service
public class Version24HourServiceImpl implements Version24HourService {
    
    @Autowired
    private VersionSdkMapper versionSdkMapper;

    public VersionSdkMapper getVersionSdkMapper() {
        return versionSdkMapper;
    }

    public void setVersionSdkMapper(VersionSdkMapper versionSdkMapper) {
        this.versionSdkMapper = versionSdkMapper;
    }

    @Override
    public List<VersionPie> queryForPie(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return versionSdkMapper.queryForPie(params);
    }
    

}
