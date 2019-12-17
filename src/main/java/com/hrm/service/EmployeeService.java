package com.hrm.service;

import com.hrm.bean.Employee;
import com.hrm.dto.emp.empDepSearchDTO;

import java.util.List;

public interface EmployeeService {

    /**
     *
     * 功能描述: 查询所有职员的信息分页结果
     *
     * @param: myPage,employee
     * @return: PageInfo<Employee>
     */
    List<empDepSearchDTO> queryAllEmpInfo();

    String checkEmpName(Employee employee);

    List<empDepSearchDTO> queryEmpInfo(Employee employee);

    void deleteOneEmp(Integer empId);

    void deleteManyEmps(List<Integer> empsId);

    String insertEmp(empDepSearchDTO empDepSearchDTO);

    String updateEmp(empDepSearchDTO empDepSearchDTO);

}
