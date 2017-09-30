package com.ym.iadpush.manage.services.tissue;

import java.util.List;
import java.util.Map;

import com.ym.common.base.Pager;
import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.SysUserRoleKey;
import com.ym.iadpush.manage.entity.SysUsers;
import com.ym.iadpush.manage.entity.SysUsers_old;

public interface IUserMgr {
    /**
     * 查询所有业务员
     * @Author lixingbiao 2017-6-15 下午10:42:42
     * @return
     */
    List<SysUsers> querySales();
    
    public int addUser(SysUsers user, Integer roleId);

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名称
     * @return
     */
    public SysUsers getByName(String username);

    /**
     * 根据用户编号查询用户
     * 
     * @param userId 用户编号
     * @return
     */
    public SysUsers getByUserId(Integer userId);

    public boolean updateUser(SysUsers user);

    public boolean updateUser(SysUsers user, Integer roleId);

    public void getAllUser(Pager pager);

    public int deleteUser(Integer userId);

    public int deleteByUserRole(Integer userId);

    public int changeUserRole(SysUserRoleKey userRole, Boolean checked);

    public List<SysUserRoleKey> getAllRole(Integer userId);

    public int batchDeleteUser(String userIds);

    boolean resetPassword(Integer userId);

    public List<SysUsers> selectRoleChild(Integer roleId);

    public int updateStatus(Integer userId, Integer status);

    public List<SysUsers> queryUsers(Map<String, Object> params);

    public int countQueryUsers(Map<String, Object> params);

    public List<Map<String, String>> selectAuthorityRole(Map<String, Object> params);

    public int selectAuthorityRoleCount(Map<String, Object> params);

    public List<Map<String, String>> selectByType(String type);

    public QueryResult queryAll(Map<String, Object> paramas);

    public SysUsers getUserByCompanyId(Integer companyId);

    Integer insertSysUsers_old(SysUsers_old sysUsers_old);
}
