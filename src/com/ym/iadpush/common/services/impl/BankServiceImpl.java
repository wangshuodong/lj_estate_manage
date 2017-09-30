package com.ym.iadpush.common.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.common.entity.Bank;
import com.ym.iadpush.common.mapper.BankMapper;
import com.ym.iadpush.common.services.BankService;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankMapper bankMapper;

    public List<Bank> query(Map<String, Object> params) {
        return bankMapper.query(params);
    }

    public BankMapper getBankMapper() {
        return bankMapper;
    }

    public void setBankMapper(BankMapper bankMapper) {
        this.bankMapper = bankMapper;
    }
    
    

}
