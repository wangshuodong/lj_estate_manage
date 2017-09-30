/**
 * 
 */
package com.ym.iadpush.manage.services.f2fPay.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.iadpush.manage.entity.FaceToFacePay;
import com.ym.iadpush.manage.mapper.FaceToFacePayMapper;
import com.ym.iadpush.manage.services.f2fPay.FaceToFacePayService;

/**
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: lixingbiao 2017年8月2日 下午2:07:03 
 * @Version: $Id$
 * @Desc: <p></p>
 */
@Service
public class FaceToFacePayServiceImpl implements FaceToFacePayService {
    @Autowired
    private FaceToFacePayMapper f2fPayMapper;
    @Override
    public void insertFaceToFacePay(FaceToFacePay f2fPay) {
           f2fPayMapper.insertFaceToFacePay(f2fPay);
    }

}
