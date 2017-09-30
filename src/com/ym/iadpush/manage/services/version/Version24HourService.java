package com.ym.iadpush.manage.services.version;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.VersionPie;

public interface Version24HourService {
    
    List<VersionPie> queryForPie(Map<String,Object> params);

}
