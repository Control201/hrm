package com.hrm.service.impl;


import com.hrm.bean.Department;
import com.hrm.bean.Employee;
import com.hrm.dao.DepartmentMapper;
import com.hrm.dao.EmployeeMapper;
import com.hrm.dto.emp.empDepSearchDTO;
import com.hrm.service.EmployeeService;
import com.hrm.util.MyTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<empDepSearchDTO> queryAllEmpInfo(){return employeeMapper.queryAll();}

    @Override
    public String checkEmpName(Employee employee) {
        if(employeeMapper.queryByName(employee.getEmpname())!=null){
            return "empnameExist";
        }else {
            return "empnameNotExist";
        }
    }

    @Override
    public List<empDepSearchDTO> queryEmpInfo(Employee employee) {
        return employeeMapper.queryEmpInfo(employee);
    }

    @Override
    public void deleteOneEmp(Integer empId) {
        employeeMapper.deleteByPrimaryKey(empId);
    }

    @Override
    public void deleteManyEmps(List<Integer> empsId) {
        for(Integer empid : empsId){
            deleteOneEmp(empid);
        }
    }

    @Override
    public String insertEmp(empDepSearchDTO empDepSearchDTO) {
        Department department = departmentMapper.queryByName(empDepSearchDTO.getDepname());
        empDepSearchDTO.setDepid(department.getDepid());
        empDepSearchDTO.setHiredate(MyTimeUtil.strToDate(empDepSearchDTO.getHiredateStr()));
        employeeMapper.insertEmp(empDepSearchDTO);
        return "insertSuccess";
    }

    @Override
    public String updateEmp(empDepSearchDTO empDepSearchDTO) {
        Department  department  =  departmentMapper.queryByName(empDepSearchDTO.getDepname());
        empDepSearchDTO.setDepid(department.getDepid());
        employeeMapper.updateEmp(empDepSearchDTO);
        return "updateSuccess";
    }



}
