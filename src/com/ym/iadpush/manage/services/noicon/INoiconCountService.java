package com.ym.iadpush.manage.services.noicon;

import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.NoiconActive;
import com.ym.iadpush.manage.entity.NoiconActiveRate;
import com.ym.iadpush.manage.entity.NoiconAd;
import com.ym.iadpush.manage.entity.NoiconCount;

public interface INoiconCountService {

	QueryResult queryDataStatement(Map<String, Object> params);

	NoiconCount getTotallCountNoicon(Map<String, Object> params);

	NoiconAd select_noicon_config_data();

	Integer updateNoiconAd(Map<String, Object> paramMap);

	/**
	 * 新增活跃报表
	 * @param params
	 * @return
	 */
	QueryResult queryNoticeActiveData(Map<String, Object> params);

	NoiconActive getTotalIcountAndAcount(Map<String, Object> params);

	QueryResult queryNoiconActiveRate(Map<String, Object> params);

	NoiconActiveRate totalNoiconActiveRate(Map<String, Object> params);

}
