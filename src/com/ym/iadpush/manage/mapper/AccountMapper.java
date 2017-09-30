/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Account;


public interface AccountMapper {
    
    public List<Account> query(Map<String,Object> params);
    
    public int countByQuery(Map<String,Object> params);
    
    public int insert(Account a);
    
    public int update(Account a);
    
    public int delById(@Param("aid")int id);
    
    public Account findById(@Param("aid")int id);
    
    public List<Account> queryAll();
    
    public Map<String,Object> getCountAccountData(Map<String,Object> params);

}
