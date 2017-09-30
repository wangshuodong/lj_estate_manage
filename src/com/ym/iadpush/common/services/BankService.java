package com.ym.iadpush.common.services;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.common.entity.Bank;


public interface BankService {
    
    List<Bank> query(Map<String,Object> params);

}
