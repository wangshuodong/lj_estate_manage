/**
 * 
 */
package com.ym.iadpush.manage.services.saleuser;

import java.util.List;

import com.ym.iadpush.manage.entity.SaleUser;

/**
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: lixingbiao 2017年8月1日 上午10:59:39 
 * @Version: $Id$
 * @Desc: <p></p>
 */
public interface SaleUserService {
    public List<SaleUser> getALlSaleUser();
    public int insertSaleUser(SaleUser saleUser);
}
