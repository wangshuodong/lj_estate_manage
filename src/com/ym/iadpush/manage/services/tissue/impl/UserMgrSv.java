package com.ym.iadpush.manage.services.tissue.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ym.common.base.Constants;
import com.ym.common.base.Pager;
import com.ym.common.utils.MD5Encrypt;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.SysUserRoleKey;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.SysUsers_old;
import com.ym.iadpush.manage.mapper.SysUserRoleMapper;
import com.ym.iadpush.manage.mapper.SysUsersMapper;
import com.ym.iadpush.manage.mapper.SysUsers_oldMapper;
import com.ym.iadpush.manage.services.emob.SysEmobService;
import com.ym.iadpush.manage.services.tissue.IUserMgr;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Exception.class)
public class UserMgrSv implements IUserMgr {

    private @Autowired
    SysUsersMapper userDao;
    private @Autowired
    SysUserRoleMapper userRoleMapper;
    private @Autowired
    SysEmobService emobService;

    private @Autowired
    SysUsers_oldMapper sysUsers_oldMapper;

    public int addUser(SysUsers user, Integer roleId) {
        // 设置默认密码
        user.setPassword(MD5Encrypt.MD5(Constants.DEFAULT_PASSWORD));
        user.setPtpwd(Constants.DEFAULT_PASSWORD);
        user.setStatus(1);
        user.setCreateDate(new Date());

        if (userDao.insert(user) != 0) {
            // 分配角色
            SysUserRoleKey userRole = new SysUserRoleKey();
            userRole.setRoleId(roleId);
            userRole.setUserId(user.getUserId());

            if (userRoleMapper.insert(userRole) != 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public SysUsers getByName(String username) {
        return userDao.selectByUsername(username);
    }

    public SysUsers getByUserId(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    public boolean updateUser(SysUsers user) {
        if (user.getPassword() == null) {
            user.setUpdateDate(new Date());
        }
        int r = userDao.updateByPrimaryKeySelective(user);
        return r > 0;
    }

    public boolean updateUser(SysUsers user, Integer roleId) {
        userRoleMapper.deleteByUserId(user.getUserId());

        // 分配角色
        SysUserRoleKey userRole = new SysUserRoleKey();
        userRole.setRoleId(roleId);
        userRole.setUserId(user.getUserId());
        userRoleMapper.insert(userRole);

        user.setUpdateDate(new Date());
        int r = userDao.updateByPrimaryKeySelective(user);
        return r > 0;
    }

    public void getAllUser(Pager pager) {
        pager.setResult(userDao.selectPager(pager.getParamsMap()));
        pager.setTotalRecord(userDao.selectPagerCount(pager.getParamsMap()));
    }

    public int deleteUser(Integer userId) {
        return userDao.deleteByPrimaryKey(userId);
    }

    public int batchDeleteUser(String userIds) {
        List<String> ids = Arrays.asList(userIds.split("\\|"));
        return userDao.batchDeleteUser(ids);
    }

    public int changeUserRole(SysUserRoleKey userRole, Boolean checked) {
        if (checked) {
            return userRoleMapper.insert(userRole);
        } else {
            return userRoleMapper.deleteByPrimaryKey(userRole);
        }
    }

    public List<SysUserRoleKey> getAllRole(Integer userId) {
        return userRoleMapper.selectByUserId(userId);
    }

    public boolean resetPassword(Integer userId) {
        SysUsers user = new SysUsers();
        user.setUserId(userId);
        user.setPassword(MD5Encrypt.MD5(Constants.DEFAULT_PASSWORD));
        user.setPtpwd(Constants.DEFAULT_PASSWORD);
        int count = userDao.updateByPrimaryKeySelective(user);
        return count == 1 ? true : false;
    }

    public List<SysUsers> selectRoleChild(Integer roleId) {
        return userDao.selectRoleChild(RoleMgrSv.getRoleChild(roleId), roleId);
    }

    public List<SysUsers> queryUsers(Map<String, Object> params) {
        return userDao.queryUsers(params);
    }

    public int countQueryUsers(Map<String, Object> params) {
        return userDao.countQueryUsers(params);
    }

    public int updateStatus(Integer userId, Integer status) {
        return userDao.updateStatus(userId, status);
    }

    public List<Map<String, String>> selectAuthorityRole(Map<String, Object> params) {
        return userDao.selectAuthorityRole(params);
    }

    public int selectAuthorityRoleCount(Map<String, Object> params) {
        return userDao.selectAuthorityRoleCount(params);
    }

    public List<Map<String, String>> selectByType(String type) {
        return userDao.selectByType(type);
    }

    @Override
    public QueryResult queryAll(Map<String, Object> paramas) {
        QueryResult result = new QueryResult();
        List<SysUsers> list = userDao.queryAll(paramas);
        result.setData(list);
        result.setCount(list.size());
        return result;
    }

    @Override
    public int deleteByUserRole(Integer userId) {
        return userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public SysUsers getUserByCompanyId(Integer companyId) {
        return userDao.getUserByCompanyId(companyId);
    }

    @Override
    public Integer insertSysUsers_old(SysUsers_old sysUsers_old) {
        return sysUsers_oldMapper.insertSysUsers_old(sysUsers_old);
    }

    /* (non-Javadoc)
     * @see com.ym.iadpush.manage.services.tissue.IUserMgr#querySales()
     */
    @Override
    public List<SysUsers> querySales() {
        // TODO Auto-generated method stub
        return userDao.querySales();
    }
}
