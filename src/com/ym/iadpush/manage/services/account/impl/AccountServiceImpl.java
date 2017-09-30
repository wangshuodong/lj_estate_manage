/**
 * 
 */
package com.ym.iadpush.manage.services.account.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Account;
import com.ym.iadpush.manage.mapper.AccountMapper;
import com.ym.iadpush.manage.services.account.AccountService;


@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public QueryResult query(Map<String, Object> params) {
        // TODO Auto-generated method stub
        QueryResult result = new QueryResult();
        List<Account> list = accountMapper.query(params);
        if(list != null && list.size() > 0){
            result.setData(list);
            int count = accountMapper.countByQuery(params);
            result.setCount(count);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.account.AccountService#countByQuery(java.util.Map)
     */
    @Override
    public int countByQuery(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return accountMapper.countByQuery(params);
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.account.AccountService#insert(com.ym.iadpush.manage.entity.Account)
     */
    @Override
    public int insert(Account a) {
        // TODO Auto-generated method stub
        return accountMapper.insert(a);
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.account.AccountService#update(com.ym.iadpush.manage.entity.Account)
     */
    @Override
    public int update(Account a) {
        // TODO Auto-generated method stub
        return accountMapper.update(a);
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.account.AccountService#delById(int)
     */
    @Override
    public int delById(int id) {
        // TODO Auto-generated method stub
        return accountMapper.delById(id);
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.account.AccountService#findById(int)
     */
    @Override
    public Account findById(int id) {
        // TODO Auto-generated method stub
        return accountMapper.findById(id);
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.account.AccountService#queryAll()
     */
    @Override
    public List<Account> queryAll() {
        // TODO Auto-generated method stub
        return accountMapper.queryAll();
    }

}
