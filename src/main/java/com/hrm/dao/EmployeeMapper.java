package com.hrm.dao;

import com.hrm.bean.Employee;
import com.hrm.bean.EmployeeExample;
import java.util.List;

import com.hrm.dto.emp.empDepSearchDTO;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {

    //查询所有职员
    List<empDepSearchDTO> queryAll();

    List<empDepSearchDTO> queryEmpInfo(Employee employee);

    Employee queryByName(String empname);
    //根据部门id查询相关部门的员工
    Employee queryById(Integer depId);

    void insertEmp(empDepSearchDTO empDepSearchDTO);

    void updateEmp(empDepSearchDTO empDepSearchDTOm);

    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer empid);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Integer empid);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}