package com.hrm.service;

import com.hrm.bean.Department;

import java.util.List;

public interface DepartmentService {

    //查询所有
    List<Department> getAll();

    //检查部门名是否存在
    String checkDepName(String depName);

    //查询部门
    List<Department> queryDepInfo(String msg);

    //
    void deleteManyDeps(List<Integer> depsId);

    String insertDep(Department department);

    String deleteOneDep(Integer depId);

    String updateDep(Department department);
}
