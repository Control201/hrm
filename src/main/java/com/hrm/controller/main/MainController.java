package com.hrm.controller.main;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.bean.Department;
import com.hrm.bean.User;
import com.hrm.service.DepartmentService;
import com.hrm.service.EmployeeService;
import com.hrm.service.UserService;
import com.hrm.util.JsonMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * @project : HRM
 * @description : 控制器-首页管理
 */
@Controller
@RequestMapping("main")
public class MainController {
    @Autowired
    UserService userService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;

    /**
     * @decription 更改个人信息
     * @param user
     * @param httpSession
     * @return
     */
    @RequestMapping("updateUser.html")
    @ResponseBody
    public JsonMsg updateUser(@RequestBody User user, HttpSession httpSession){
        int i = userService.updateUser(user);
        if(i!=0){
            httpSession.setAttribute("user",user.getId());
            return JsonMsg.success();
        }else {
            return JsonMsg.fail();
        }
    }

    @RequestMapping("toHome.html")
    public String toHome(){return "home/home";}

    /**
     * @decription 跳转员工列表页面
     * @return
     */
    @RequestMapping("toEmployeeList.html")
    public String toEmployeeList() {
        return "query/EmployeeList";
    }
    /**
     * @decription 跳转到添加员工页面
     * @return
     */
    @RequestMapping("toModifyEmp.html")
    public String toAddEmployee(){return "modify/modifyEmployee";}

    /**
     * @decription 查询所有部门信息并跳转到部门信息页面(分页查询)
     * @param model
     * @return
     */
    @RequestMapping("toDepartmentList.html")
    public String toDepartmentList(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model){
        //pn为当前页码,每页的大小为5
        PageHelper.startPage(pn,5);
        List<Department> departmentList = departmentService.getAll();
        //导航栏最多为5页
        PageInfo pageInfo = new PageInfo(departmentList,5);
        model.addAttribute("pageInfo",pageInfo);
        return "query/DepartmentList";}

    /**
     * @decription 跳转到添加部门页面
     * @return
     */
    @RequestMapping("toModifyDep.html")
    public String toAddDepartment(){return "modify/modifyDepartment";}

    /**
     *
     * 功能描述: 跳转到错误页面(权限不足)
     *
     * @param: null
     * @return: String
     */
    @RequestMapping("toError.html")
    public String toError(){return "error/error";}
}
