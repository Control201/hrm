package com.hrm.controller.main;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.bean.Department;
import com.hrm.bean.MyPage;
import com.hrm.service.DepartmentService;
import com.hrm.util.Layui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description : 控制器-部门管理页面
 */
@Controller
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    /**
     *
     * 功能描述: 查询所有的部门
     *
     * @return: ResponseEntity<Layui>分页信息
     */
    @RequestMapping("queryAllDep.html")
    @ResponseBody
    public ResponseEntity<Layui> queryAllDep(MyPage myPage){
        PageHelper.startPage(myPage.getPageNum(),myPage.getPageSize());
        List<Department> allDepInfo = departmentService.getAll();
        PageInfo pageInfo = new PageInfo(allDepInfo,5);
        Layui layui = Layui.data(pageInfo.getTotal(),pageInfo.getList());
        return ResponseEntity.ok(layui);
    }

    /**
     *
     * 功能描述: 检查部门是否存在
     *
     * @param: department
     * @return: Map<String,Object>
     */
    @RequestMapping("checkDepName.html")
    @ResponseBody
    public Map<String,Object> checkDepName(Department department){
        Map<String,Object> map = new HashMap<>();
        map.put("data",departmentService.checkDepName(department.getDepname()));
        return map;
    }

    /**
     *
     * 功能描述: 查询部门信息
     *
     * @param: myPage,Msg
     * @return: ResponseEntity<Layui>分页信息
     */
    @RequestMapping("queryDepInfo.html")
    @ResponseBody
    public ResponseEntity<Layui> queryDep(MyPage myPage,@RequestParam("Msg") String Msg){
        PageHelper.startPage(myPage.getPageNum(),myPage.getPageSize());
        List<Department> depInfo = departmentService.queryDepInfo(Msg);
        PageInfo pageInfo = new PageInfo(depInfo,5);
        Layui layui = Layui.data(pageInfo.getTotal(),pageInfo.getList());
        return ResponseEntity.ok(layui);
    }

    /**
     *
     * 功能描述: 删除指定的部门
     *
     * @param: department
     * @return: Map<String,Object>
     */
    @RequestMapping("deleteOneDep.html")
    public Map<String,Object> deleteOneDep(Department department){
        Map<String,Object> map = new HashMap<>();
        map.put("data",departmentService.deleteOneDep(department.getDepid()));
        return map;
    }

    /**
     *
     * 功能描述: 批量删除部门信息
     *
     * @param: deps
     * @return: Map<String,Object>
     */
    @RequestMapping("deleteManyDeps.html")
    @ResponseBody
    public Map<String,Object> deleteManyDeps(@RequestParam("deps")String deps){
        Map<String,Object> map = new HashMap<>();
        List<Department> departmentList = JSON.parseArray(deps,Department.class);
        List<Integer> depsId = new ArrayList<>();
        for (Department department : departmentList){
            depsId.add(department.getDepid());
        }
        departmentService.deleteManyDeps(depsId);
        map.put("data","deleteSuccess");
        return map;
    }

    /**
     *
     * 功能描述: 跳转编辑部门的页面
     *
     * @param: department
     * @return: String
     */
    @RequestMapping("toEditDep.html")
    public String toEditDep(Model model,Department department){
        model.addAttribute("depInfo",department);
        return "modify/departmentForm";
    }

    /**
     *
     * 功能描述: 添加部门
     *
     * @param: department
     * @return: Map<String,Object>
     */
    @RequestMapping("insertDep.html")
    @ResponseBody
    public Map<String,Object> insertDep(Department department){
        Map<String,Object> map = new HashMap<>();
        map.put("data",departmentService.insertDep(department));
        return map;
    }


    /**
     *
     * 功能描述: 更新部门信息
     *
     * @param: department
     * @return: Map<String,Object>
     */
    @RequestMapping("updateDep.html")
    @ResponseBody
    public Map<String,Object> updateDep(Department department){
        Map<String,Object> map = new HashMap<>();
        map.put("data",departmentService.updateDep(department));
        return map;
    }
}
