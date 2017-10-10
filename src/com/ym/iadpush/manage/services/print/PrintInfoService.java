/**
 * 
 */
package com.ym.iadpush.manage.services.print;

import java.util.List;

import com.ym.iadpush.manage.entity.PrintInfo;

/**
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: lixingbiao 2017年10月10日 上午11:07:37 
 * @Version: $Id$
 * @Desc: <p></p>
 */
public interface PrintInfoService {
    public PrintInfo selectBydepartmentId(Integer departmentId);
    
    public List<PrintInfo> selectBystatus(Integer status);
}
