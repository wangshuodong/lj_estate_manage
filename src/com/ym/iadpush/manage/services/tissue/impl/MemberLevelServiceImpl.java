/**
 * 
 */
package com.ym.iadpush.manage.services.tissue.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.MemberLevel;
import com.ym.iadpush.manage.mapper.MemberLevelMapper;
import com.ym.iadpush.manage.services.tissue.MemberLevelService;

@Service
public class MemberLevelServiceImpl implements MemberLevelService {
    
    @Autowired
    private MemberLevelMapper memberLevelMapper;

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.tissue.MemberLevelService#query(java.util.Map)
     */
    @Override
    public QueryResult query(Map<String, Object> params) {
        // TODO Auto-generated method stub
        QueryResult result = new QueryResult();
        List<MemberLevel> list = memberLevelMapper.query(params);
        if(list != null && list.size() > 0){
            result.setData(list);
            int count = memberLevelMapper.countByQuery(params);
            result.setCount(count);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.tissue.MemberLevelService#getAll()
     */
    @Override
    public List<MemberLevel> getAll() {
        // TODO Auto-generated method stub
        return memberLevelMapper.queryAll();
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.tissue.MemberLevelService#insert(com.ym.iadpush.manage.entity.MemberLevel)
     */
    @Override
    public int insert(MemberLevel m) {
        // TODO Auto-generated method stub
        return memberLevelMapper.insert(m);
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.tissue.MemberLevelService#update(com.ym.iadpush.manage.entity.MemberLevel)
     */
    @Override
    public int update(MemberLevel m) {
        // TODO Auto-generated method stub
        return memberLevelMapper.updte(m);
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.tissue.MemberLevelService#findById(int)
     */
    public MemberLevel findById(int id) {
        // TODO Auto-generated method stub
        return memberLevelMapper.findById(id);
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.tissue.MemberLevelService#deleteById(int)
     */
    @Override
    public int deleteById(int id) {
        return memberLevelMapper.deleteById(id);
    }

}
