/**
 * 
 */
package com.ym.iadpush.manage.services.department.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Department;
import com.ym.iadpush.manage.mapper.DepartmentMapper;
import com.ym.iadpush.manage.services.department.IDepartmentService;


@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getDepartmentByCode(Map<String, Object> paramMap) {
        List<Department> list = departmentMapper.getDepartmentByCode(paramMap);
        return list;
    }

    public Department getDepartmentById(Map<String, Object> paramMap) {
        Department department = departmentMapper.getDepartmentById(paramMap);
        return department;
    }

    public QueryResult query(Map<String, Object> paramMap) {
        QueryResult result = new QueryResult();
        result.setData(departmentMapper.queryByParam(paramMap));
        result.setCount(departmentMapper.countByParam(paramMap));
        return result;
    }

    public int insert(Department d) {

        return departmentMapper.insert(d);
    }

    @Override
    public int update(Department d) {
        return departmentMapper.update(d);
    }

    @Override
    public int delete(int id) {

        return departmentMapper.delById(id);
    }

    @Override
    public String selectMaxByParentCode(Integer parentId) {
        return departmentMapper.selectMaxByParentCode(parentId);
    }
}
