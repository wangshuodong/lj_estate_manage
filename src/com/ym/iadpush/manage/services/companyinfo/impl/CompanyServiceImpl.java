/**
 * 
 */
package com.ym.iadpush.manage.services.companyinfo.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.CompanyInfo;
import com.ym.iadpush.manage.entity.CompanyInfo_old;
import com.ym.iadpush.manage.mapper.CompanyInfoMapper;
import com.ym.iadpush.manage.mapper.CompanyInfo_oldMapper;
import com.ym.iadpush.manage.services.companyinfo.ICompanyService;

@Service
public class CompanyServiceImpl implements ICompanyService {
    @Autowired
    private CompanyInfoMapper companyInfoMapper;
    @Autowired
    private CompanyInfo_oldMapper companyInfo_oldMapper;

    @Override
    public Integer insertCompany(CompanyInfo companyInfo) {
        Integer in = companyInfoMapper.insertCompany(companyInfo);
        return in;
    }

    @Override
    public List<CompanyInfo> getAllSales(Map<String, Object> paramMap) {
        List<CompanyInfo> list = companyInfoMapper.getAllSales(paramMap);
        return list;
    }

    @Override
    public List<CompanyInfo> getAllCustomers(Map<String, Object> paramMap) {
        List<CompanyInfo> list = companyInfoMapper.getAllCustomers(paramMap);
        return list;
    }

    @Override
    public Integer getAllSalesCount(Map<String, Object> paramMap) {
        Integer in = companyInfoMapper.getAllSalesCount(paramMap);
        return in;
    }

    @Override
    public Integer getAllCustomersCount(Map<String, Object> paramMap) {
        Integer in = companyInfoMapper.getAllCustomersCount(paramMap);
        return in;
    }

    @Override
    public CompanyInfo getCompanyInfoById(Integer companyId) {
        CompanyInfo companyInfo = companyInfoMapper.getCompanyInfoById(companyId);
        return companyInfo;
    }

    @Override
    public List<CompanyInfo> getAllProxyFactorys(Map<String, Object> paramMap) {
        List<CompanyInfo> list = companyInfoMapper.getAllProxyFactorys(paramMap);
        return list;
    }

    @Override
    public Integer getAllProxyFactorysCount(Map<String, Object> paramMap) {
        Integer in = companyInfoMapper.getAllProxyFactorysCount(paramMap);
        return in;
    }

    @Override
    public CompanyInfo getCompanyInfoByDepartmentId(Map<String, Object> paramMap) {
        CompanyInfo companyInfo = companyInfoMapper.getCompanyInfoByDepartmentId(paramMap);
        return companyInfo;
    }

    @Override
    public CompanyInfo getCompanyInfoByUsername(Map<String, Object> paramMap) {
        return companyInfoMapper.getCompanyInfoByUsername(paramMap);
    }

    @Override
    public Integer deleteCompanyInfoByUsername(String username) {
        return companyInfoMapper.deleteCompanyInfoByUsername(username);
    }

    @Override
    public Integer updateCompanyInfo(CompanyInfo companyInfo, Integer companyId) {
        return companyInfoMapper.updateCompanyInfo(companyInfo, companyId);
    }

    @Override
    public CompanyInfo getCompanyInfoByName(Map<String, Object> paramMap) {
        return companyInfoMapper.getCompanyInfoByName(paramMap);
    }

    @Override
    public List<CompanyInfo> getAllFactorys(Map<String, Object> paramMap) {
        return companyInfoMapper.getAllFactorys(paramMap);
    }

    @Override
    public int getAllFactorysCount(Map<String, Object> paramMap) {
        return companyInfoMapper.getAllFactorysCount(paramMap);
    }

    @Override
    public Integer insertCompanyInfo_old(CompanyInfo_old companyInfo_old) {
        return companyInfo_oldMapper.insertCompanyInfo_old(companyInfo_old);
    }
}
