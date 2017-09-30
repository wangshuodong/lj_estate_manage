/**
 * 
 */
package com.ym.iadpush.manage.services.f2fPay;
import com.ym.iadpush.manage.entity.FaceToFacePay;

/**
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: lixingbiao 2017年8月2日 下午2:04:37 
 * @Version: $Id$
 * @Desc: <p></p>
 */
public interface FaceToFacePayService {
    //新增扫码支付订单
    public void insertFaceToFacePay(FaceToFacePay f2fPay);
}
