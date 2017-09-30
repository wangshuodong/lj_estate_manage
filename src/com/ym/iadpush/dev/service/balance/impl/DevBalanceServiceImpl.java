package com.ym.iadpush.dev.service.balance.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.dev.mapper.DevBalanceMapper;
import com.ym.iadpush.dev.service.balance.DevBalanceService;
import com.ym.iadpush.manage.entity.Balance;

@Service
public class DevBalanceServiceImpl implements DevBalanceService {
	private @Autowired DevBalanceMapper balanceMapper;

	public List<Map<String, String>> countEarn(Map<String, Object> paramsMap) {
		return balanceMapper.countEarn(paramsMap);
	}

	public Map<String, String> sumEarn(Map<String, Object> paramsMap) {
		return balanceMapper.sumEarn(paramsMap);
	}

	public int selectTotalRecord(Map<String, Object> paramsMap) {
		return balanceMapper.selectTotalRecord(paramsMap);
	}

	public List<Balance> findDevBalances(Map<String, Object> paramsMap) {
		return balanceMapper.findDevBalances(paramsMap);
	}

	public int SelectTotalDevBalances(Map<String, Object> paramsMap) {
		return balanceMapper.SelectTotalDevBalances(paramsMap);
	}
}
