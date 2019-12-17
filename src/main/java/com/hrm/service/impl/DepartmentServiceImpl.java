package com.hrm.service.impl;

import com.hrm.bean.Department;
import com.hrm.dao.DepartmentMapper;
import com.hrm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAll() {
        return departmentMapper.getAll();
    }

    @Override
    public String checkDepName(String depName) {
        if(departmentMapper.queryByName(depName)!=null){
            return "depExist";
        }else return "depNotExist";
    }

    @Override
    public List<Department> queryDepInfo(String message) {
        System.out.println(message);
        return departmentMapper.queryDepInfo(message);
    }

    @Override
    public void deleteManyDeps(List<Integer> depsId) {
        for(Integer depid : depsId){
            departmentMapper.deleteByPrimaryKey(depid);
        }
    }

    @Override
    public String insertDep(Department department) {
        departmentMapper.insertSelective(department);
        return "insertSuccess";
    }

    @Override
    public String deleteOneDep(Integer depId) {
        departmentMapper.deleteByPrimaryKey(depId);
        return "deleteSuccess";
    }

    @Override
    public String updateDep(Department department) {
        departmentMapper.updateDep(department);
        return "updateSuccess";
    }

}
