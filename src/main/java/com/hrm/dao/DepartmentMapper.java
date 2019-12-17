package com.hrm.dao;

import com.hrm.bean.Department;
import com.hrm.bean.DepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentMapper {

    //获取所有部门
    List<Department> getAll();

    //通过模糊查找获取相关的部门信息
    List<Department> queryDepInfo(@Param("message") String message);

    //通过部门名得到部门
    Department queryByName(String depname);

    void updateDep(Department department);

    long countByExample(DepartmentExample example);

    int deleteByExample(DepartmentExample example);

    int deleteByPrimaryKey(Integer depid);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByExample(DepartmentExample example);

    Department selectByPrimaryKey(Integer depid);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}