/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.CompanyInfo;

public interface CompanyInfoMapper {
    Integer insertCompany(CompanyInfo companyInfo);

    List<CompanyInfo> getAllSales(Map<String, Object> paramMap);

    List<CompanyInfo> getAllCustomers(Map<String, Object> paramMap);

    Integer getAllSalesCount(Map<String, Object> paramMap);

    Integer getAllCustomersCount(Map<String, Object> paramMap);

    CompanyInfo getCompanyInfoById(@Param("id") Integer companyId);

    List<CompanyInfo> getAllProxyFactorys(Map<String, Object> paramMap);

    Integer getAllProxyFactorysCount(Map<String, Object> paramMap);

    CompanyInfo getCompanyInfoByDepartmentId(Map<String, Object> paramMap);

    CompanyInfo getCompanyInfoByUsername(Map<String, Object> paramMap);

    CompanyInfo getCompanyInfoByName(Map<String, Object> paramMap);

    Integer deleteCompanyInfoByUsername(@Param("username") String username);

    Integer updateCompanyInfo(@Param("company") CompanyInfo companyInfo, @Param("id") Integer companyId);

    List<CompanyInfo> getAllFactorys(Map<String, Object> paramMap);

    int getAllFactorysCount(Map<String, Object> paramMap);

}
