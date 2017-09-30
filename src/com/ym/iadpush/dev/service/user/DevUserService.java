package com.ym.iadpush.dev.service.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ym.iadpush.manage.entity.SysUsers;


public interface DevUserService {
    
    /**
     * 修改用户个人信息
     * @Author TULIANGCHENG 2014-4-21 下午2:10:27
     * @param map
     * @return
     */
    int updateUserInfo(Map<String,Object> map);
    
    /**
     * 根据用户ID查询出用户信息
     * @Author TULIANGCHENG 2014-4-23 下午4:53:25
     * @param id
     * @return
     */
    SysUsers findById(int id);
    
    /**
     * 用户注册接口
     * @Author TULIANGCHENG 2014-4-28 下午4:47:38
     * @param users
     */
    void register(SysUsers users,HttpServletRequest request);
    
    /**
     * 查询用户是否存在
     * @Author TULIANGCHENG 2014-4-28 下午6:13:39
     * @param userName
     * @return
     */
    boolean exists(String userName);
    
    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    SysUsers findByUserName(String userName);
    
    /**
     * 根据主键更新用户
     * @param sysUsers
     * @return
     */
    int updateUserByKey(SysUsers sysUsers);
    
    /**
     * 密码重置
     * @Author TULIANGCHENG 2014-5-8 下午12:04:35
     * @param user
     * @return
     */
    boolean resetpassword(SysUsers user,HttpServletRequest request);
    
    /**
	 * 查询所有用户
	 * @Author TULIANGCHENG 2014-5-12 下午6:08:39
	 * @return
	 */
	List<SysUsers> queryAllUsers();
	
	/**
	 * 记录用户明文密码
	 * @Author tuliangcheng 2014-7-1 下午3:48:35
	 * @param map
	 * @return
	 */
	int updatePtPwd(Map<String,Object> map);
    
    

}
