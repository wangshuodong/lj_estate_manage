/**
 * 
 */
package com.ym.iadpush.manage.services.companyinfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.CompanyInfo;
import com.ym.iadpush.manage.entity.CompanyInfo_old;

public interface ICompanyService {

    Integer insertCompany(CompanyInfo companyInfo);

    List<CompanyInfo> getAllSales(Map<String, Object> paramMap);

    Integer getAllSalesCount(Map<String, Object> paramMap);

    List<CompanyInfo> getAllCustomers(Map<String, Object> paramMap);

    Integer getAllCustomersCount(Map<String, Object> paramMap);

    CompanyInfo getCompanyInfoById(Integer companyId);

    List<CompanyInfo> getAllProxyFactorys(Map<String, Object> paramMap);

    Integer getAllProxyFactorysCount(Map<String, Object> paramMap);

    CompanyInfo getCompanyInfoByDepartmentId(Map<String, Object> paramMap);

    CompanyInfo getCompanyInfoByUsername(Map<String, Object> paramMap);

    Integer deleteCompanyInfoByUsername(@Param("username") String username);

    Integer updateCompanyInfo(CompanyInfo companyInfo, @Param("companyId") Integer companyId);

    CompanyInfo getCompanyInfoByName(Map<String, Object> paramMap);

    List<CompanyInfo> getAllFactorys(Map<String, Object> paramMap);

    int getAllFactorysCount(Map<String, Object> paramMap);

    Integer insertCompanyInfo_old(CompanyInfo_old companyInfo_old);
}
