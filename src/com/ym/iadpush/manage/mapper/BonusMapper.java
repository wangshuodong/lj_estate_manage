package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.App;
import com.ym.iadpush.manage.entity.Bonus;
import com.ym.iadpush.manage.entity.News;

public interface BonusMapper {

	List<Bonus> getAllBonus(Map<String, Object> paramMap);

	Integer getAllCountBonus(Map<String, Object> paramMap);
	
	Integer insertBonus(Map<String, Object> paramMap);

	Integer deleteBonus(Map<String, Object> paramMap);

	Integer updateBonus(Map<String, Object> paramMap);
	
	Bonus selectBonusById(Map<String, Object> paramMap);
}
