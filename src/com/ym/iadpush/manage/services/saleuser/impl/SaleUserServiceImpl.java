/**
 * 
 */
package com.ym.iadpush.manage.services.saleuser.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.SaleUser;
import com.ym.iadpush.manage.mapper.SaleUserMapper;
import com.ym.iadpush.manage.services.saleuser.SaleUserService;

/**
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: lixingbiao 2017年8月1日 上午11:05:39 
 * @Version: $Id$
 * @Desc: <p></p>
 */
@Service
public class SaleUserServiceImpl implements SaleUserService {
    @Autowired
    private SaleUserMapper saleUserMap;
    @Override
    public List<SaleUser> getALlSaleUser() {
        return saleUserMap.getALlSaleUser();
    }

    @Override
    public int insertSaleUser(SaleUser saleUser) {
        return saleUserMap.insertSaleUser(saleUser);
    }

}
