/**
 * 
 */
package com.ym.iadpush.manage.services.account;

import java.util.List;
import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Account;


public interface AccountService {
    
    QueryResult query(Map<String,Object> params);
    
    int countByQuery(Map<String,Object> params);
    
    int insert(Account a);
    
    int update(Account a);
    
    int delById(int id);
    
    Account findById(int id);
    
    List<Account> queryAll();

}
