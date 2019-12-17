package com.hrm.controller.main;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.bean.Employee;
import com.hrm.bean.MyPage;
import com.hrm.dto.emp.empDepSearchDTO;
import com.hrm.service.EmployeeService;
import com.hrm.util.Layui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     *
     * 功能描述: 查询所有员工
     *
     * @param: myPage
     * @return: ResponseEntity<Layui>分页结果
     */
    @RequestMapping("queryAllEmp.html")
    @ResponseBody
    public ResponseEntity<Layui> showAllEmp(MyPage myPage){
        PageHelper.startPage(myPage.getPageNum(),myPage.getPageSize());
        List<empDepSearchDTO> allEmpInfo = employeeService.queryAllEmpInfo();
        PageInfo pageInfo = new PageInfo(allEmpInfo,5);
        Layui layui = Layui.data(pageInfo.getTotal(),pageInfo.getList());
        return ResponseEntity.ok(layui);
    }


    /**
     *
     * 功能描述: 跳转编辑员工页面
     *
     * @param: model,empDepSearchDTO
     * @return: String
     */
    @RequestMapping("toEditEmp.html")
    public String toAddEmp(Model model,empDepSearchDTO empDepSearchDTO){
        model.addAttribute("empInfo",empDepSearchDTO);
        return "modify/employeeForm";
    }
    /**
     *
     * 功能描述: 检查员工姓名是否重复
     *
     * @param: employee
     * @return: Map<String,Object>
     */
    @RequestMapping("checkEmpName.html")
    @ResponseBody
    public Map<String,Object> checkEmpName(Employee employee){
        Map<String,Object> map = new HashMap<>();
        map.put("data",employeeService.checkEmpName(employee));
        return map;
    }
    
    /**
     *
     * 功能描述: 查询员工
     *
     * @param: myPage,employee
     * @return: ResponseEntity<Layui>分页结果
     */
    @RequestMapping("queryEmpInfo.html")
    @ResponseBody
    public ResponseEntity<Layui> queryEmp(MyPage myPage,Employee employee){
        PageHelper.startPage(myPage.getPageNum(),myPage.getPageSize());
        List<empDepSearchDTO> empInfo = employeeService.queryEmpInfo(employee);
        PageInfo pageInfo = new PageInfo(empInfo,5);
        Layui layui = Layui.data(pageInfo.getTotal(),pageInfo.getList());
        return ResponseEntity.ok(layui);
    }

    /**
     *
     * 功能描述: 更新员工信息
     *
     * @param: empDepSearchDTO
     * @return: Map<String,Object>
     */
    @RequestMapping("updateEmp.html")
    @ResponseBody
    public Map<String,Object> updateEmp(empDepSearchDTO empDepSearchDTO){
        Map<String,Object> map = new HashMap<>();
        map.put("data",employeeService.updateEmp(empDepSearchDTO));
        return map;
    }

    /**
     *
     * 功能描述: 删除某个指定的员工
     *
     * @param: empId
     * @return: Map<String,Object>
     */
    @RequestMapping("deleteOneEmp.html")
    @ResponseBody
    public Map<String,Object> deleteOneEmp(@RequestParam("empId") Integer empId){
        Map<String,Object> map = new HashMap<>();
        employeeService.deleteOneEmp(empId);
        map.put("data","successDelete");
        return map;
    }

    /**
     *
     * 功能描述: 批量删除员工信息
     *
     * @param: emps
     * @return: Map<String,Object>
     */
    @RequestMapping("deleteManyEmps.html")
    @ResponseBody
    public Map<String ,Object> deleteManyEmps(@RequestParam("emps") String emps){
        Map<String,Object> map = new HashMap<>();
        List<empDepSearchDTO> empDepSearchDTOList = JSON.parseArray(emps,empDepSearchDTO.class);
        List<Integer> empsId = new ArrayList<>();
        for(empDepSearchDTO empDepSearchDTO: empDepSearchDTOList){
            empsId.add(empDepSearchDTO.getEmpid());
        }
        employeeService.deleteManyEmps(empsId);
        map.put("data","deleteSuccess");
        return map;
    }


    /**
     *
     * 功能描述: 添加新员工
     *
     * @param: empDepSearchDTO
     * @return: Map<String,Object>
     */
    @RequestMapping("insertEmp.html")
    @ResponseBody
    public Map<String,Object> insertEmp(empDepSearchDTO empDepSearchDTO){
        HashMap<String,Object> map = new HashMap<>();
        map.put("data",employeeService.insertEmp(empDepSearchDTO));
        return map;
    }


}
