/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Department;


public interface DepartmentMapper {

    List<Department> getDepartmentByCode(Map<String, Object> paramMap);

    Department getDepartmentById(Map<String, Object> paramMap);

    Integer countByParam(Map<String, Object> paramMap);

    List<Department> queryByParam(Map<String, Object> paramMap);

    int insert(Department d);

    int update(Department d);

    int delById(@Param("id") int id);

    Department selectMaxByParentCode(@Param("code") String code);
}
