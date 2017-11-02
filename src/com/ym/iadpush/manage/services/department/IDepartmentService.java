/**
 * 
 */
package com.ym.iadpush.manage.services.department;

import java.util.List;
import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Department;

public interface IDepartmentService {
    /**
     * 通过当前登录部门code查询下属部门
     * 
     * @Author lixingbiao 2017-6-11 下午7:08:41
     * @param paramMap
     * @return
     */
    List<Department> getDepartmentByCode(Map<String, Object> paramMap);

    Department getDepartmentById(Map<String, Object> paramMap);

    QueryResult query(Map<String, Object> paramMap);

    int insert(Department d);

    int update(Department d);

    int delete(int id);

    String selectMaxByParentCode(Integer id);
}
