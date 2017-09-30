package com.ym.iadpush.manage.services.bonus.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.Bonus;
import com.ym.iadpush.manage.mapper.BonusMapper;
import com.ym.iadpush.manage.services.bonus.IBonusService;

@Service
public class BonusServiceImpl implements IBonusService {
	
	 @Autowired
	    private BonusMapper bonusMapper;

	@Override
	public Integer getAllCountBonus(Map<String, Object> paramMap) {
		return bonusMapper.getAllCountBonus(paramMap);
	}

	@Override
	public List<Bonus> getAllBonus(Map<String, Object> paramMap) {
		return bonusMapper.getAllBonus(paramMap);
	}

	@Override
	public Integer insertBonus(Map<String, Object> paramMap) {
		return bonusMapper.insertBonus(paramMap);
	}

	@Override
	public Integer deleteBonus(Map<String, Object> paramMap) {
		return bonusMapper.deleteBonus(paramMap);
	}

	@Override
	public Integer updateBonus(Map<String, Object> paramMap) {
		return bonusMapper.updateBonus(paramMap);
	}

	@Override
	public Bonus selectBonusById(Map<String, Object> paramMap) {
		return bonusMapper.selectBonusById(paramMap);
	}
	 
    

}
