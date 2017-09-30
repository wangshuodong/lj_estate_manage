/**
 * 
 */
package com.ym.iadpush.manage.services.bonus;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.manage.entity.Bonus;



public interface IBonusService {

	Integer getAllCountBonus(Map<String, Object> paramMap);

	List<Bonus> getAllBonus(Map<String, Object> paramMap);

	Integer insertBonus(Map<String, Object> paramMap);

	Integer deleteBonus(Map<String, Object> paramMap);

	Integer updateBonus(Map<String, Object> paramMap);

	Bonus selectBonusById(Map<String, Object> paramMap);



}
