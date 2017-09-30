package com.ym.iadpush.dev.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.dev.entity.DevApp;
import com.ym.iadpush.dev.entity.DevChannel;
import com.ym.iadpush.manage.entity.App;

public interface DevApplyMapper {

	int updateAppStatus(Map<String, Object> params);

	List<DevChannel> selectChannel();

	DevApp selectAppById(Map<String, Object> params);

	Integer insertApp(Map<String, Object> params);

	Integer updatetApp(Map<String, Object> params);
}
