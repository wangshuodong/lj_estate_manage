/**
 * 
 */
package com.ym.iadpush.manage.services.print.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.PrintInfo;
import com.ym.iadpush.manage.mapper.PrintInfoMapper;
import com.ym.iadpush.manage.services.print.PrintInfoService;

/**
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: lixingbiao 2017年10月10日 上午11:09:42 
 * @Version: $Id$
 * @Desc: <p></p>
 */
@Service
public class PrintInfoServiceImpl implements PrintInfoService{
    @Autowired
    PrintInfoMapper printInfoMapper;

    @Override
    public PrintInfo selectBydepartmentId(Integer departmentId) {
        return printInfoMapper.selectBydepartmentId(departmentId);
    }
    
    @Override
    public List<PrintInfo> selectBystatus(Integer status) {
        return printInfoMapper.selectBystatus(status);
    }

}
