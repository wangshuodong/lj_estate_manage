package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.NoiconActive;
import com.ym.iadpush.manage.entity.NoiconActiveRate;
import com.ym.iadpush.manage.entity.NoiconAd;
import com.ym.iadpush.manage.entity.NoiconCount;

public interface NoiconCountMapper {

	List<NoiconCount> getAllNoicon(Map<String, Object> params);

	List<NoiconCount> getCountNoicon(Map<String, Object> params);
	
	NoiconCount getTotallCountNoicon(Map<String, Object> params);

	NoiconAd select_noicon_config_data();

	Integer updateNoiconAd(Map<String, Object> paramMap);
	
	NoiconAd selectByKey(Integer id);

	List<NoiconActive> getAllNoiconActiveReport(Map<String, Object> params);

	Integer getCountAllNoiconActiveReport(Map<String, Object> params);
	
	NoiconActive getTotalIcountAndAcount(Map<String,Object> params);

	Integer getCountAllNoiconActiveRate(Map<String, Object> params);

	List<NoiconActiveRate> getAllNoiconActiveRate(Map<String, Object> params);

	NoiconActiveRate totalNoiconActiveRate(Map<String, Object> params);

}
