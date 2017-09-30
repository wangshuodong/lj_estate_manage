package com.ym.iadpush.common.mapper;

import java.util.List;
import java.util.Map;

import com.ym.iadpush.common.entity.Bank;


public interface BankMapper {
    

    List<Bank> query(Map<String,Object> params);

}
