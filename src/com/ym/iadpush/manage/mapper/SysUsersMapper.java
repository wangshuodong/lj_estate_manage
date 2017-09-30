package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.SysUsers;

public interface SysUsersMapper {
    int deleteByPrimaryKey(Integer userId);

    int batchDeleteUser(@Param("ids") List<String> ids);

    int insert(SysUsers record);

    int insertSelective(SysUsers record);

    SysUsers selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(SysUsers record);

    int updateByPrimaryKey(SysUsers record);
    

    int updateStatus(@Param("userId") Integer userId, @Param("status") Integer status);

    SysUsers selectByUsername(String username);
    
    String findDevType(int userId);

    List<SysUsers> selectByParams(Map<String, Object> params);

    List<SysUsers> selectPager(Map<String, Object> paramsMap);

    int selectPagerCount(Map<String, Object> paramsMap);

    List<SysUsers> getAllUsers();

    List<SysUsers> selectRoleChild(@Param("childRole") String childRole, @Param("roleId") Integer roleId);
    
    List<SysUsers> queryUsers(Map<String, Object> params);
    
    int countQueryUsers(Map<String, Object> params);
    
    List<Map<String,String>> selectAuthorityRole(Map<String, Object> params);
    
    int selectAuthorityRoleCount(Map<String, Object> params);
    
    List<Map<String,String>> selectByType(String type);
    
    List<SysUsers> queryAll(Map<String,Object> params);
    
    /**
     * 查询所有业务员
     * @Author lixingbiao 2017-6-15 下午10:42:06
     * @return
     */
    List<SysUsers> querySales();

    SysUsers getUserByCompanyId(Integer companyId);
}
