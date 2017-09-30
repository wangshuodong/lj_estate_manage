/**
 * 
 */
package com.ym.iadpush.manage.services.version.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.iadpush.manage.entity.VersionKit;
import com.ym.iadpush.manage.services.version.IVersionKitService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class VersionKitServiceImpl implements IVersionKitService {

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.version.IVersionKitService#getAllCountVersionKit()
     */
    @Override
    public int getAllCountVersionKit() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.version.IVersionKitService#getAllVersionKit(java.util.Map)
     */
    @Override
    public List<VersionKit> getAllVersionKit(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.version.IVersionKitService#selectVersionKit_detail(java.util.Map)
     */
    @Override
    public VersionKit selectVersionKit_detail(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.version.IVersionKitService#updateVersionKit(java.util.Map)
     */
    @Override
    public int updateVersionKit(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.version.IVersionKitService#deleteVersionKit(java.util.Map)
     */
    @Override
    public int deleteVersionKit(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.version.IVersionKitService#insertVersionKit(java.util.Map)
     */
    @Override
    public int insertVersionKit(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.version.IVersionKitService#getMaxId()
     */
    @Override
    public Integer getMaxId() {
        // TODO Auto-generated method stub
        return null;
    }

   

    

}
