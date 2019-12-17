package com.hrm.controller.user;


import com.hrm.bean.User;
import com.hrm.service.UserService;
import com.hrm.util.JsonMsg;
import com.hrm.util.setCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @project : HRM
 * @description : 控制器-管理登录页面
 */

@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    UserService userService;

    /**
     *
     * 功能描述: 跳转到登录页面
     *
     * @param: null
     * @return: String
     */
    @RequestMapping("toLogin.html")
    public String toLogin(){return "user/login";}
    
    /**
     *
     * 功能描述: 登录验证
     *
     * @param: user,remember,HttpSession,HttpServletRequest,HttpServletResponse
     * @return: JsonMsg
     */
    @RequestMapping("loginCheck.html")
    @ResponseBody
    public JsonMsg loginCheck(User user,@RequestParam(value = "remember",required = false)String remember, HttpSession session
    ,HttpServletRequest request,HttpServletResponse response){
            User userSessionInfo = userService.loginCheck(user);  //验证用户名是否存在
            if(userSessionInfo != null){
                session.setAttribute("user",userSessionInfo);
                System.out.println(remember);
                setCookie.setUserLoginCookie(user.getName(),user.getPassword(),remember,request,response);
                return JsonMsg.success();
            }else {
                return JsonMsg.fail().addInfo("login_error","输入的账号或者密码错误,请重新输入!");
            }
    }

    /**
     *
     * 功能描述: 跳转到主页
     *
     * @param: null
     * @return: String
     */
    @RequestMapping("toMain.html")
    public String toMain(){return "index";}

    /**
     *
     * 功能描述: 账号登出
     *
     * @param: HttpSession,HttpServletResponse
     * @return: void
     */
    @RequestMapping("loginOut.html")
    public void loginOut(HttpSession session, HttpServletResponse response){
        session.removeAttribute("user");

        //注销后重定向到登录页面
        try {
            response.sendRedirect("/login/toLogin.html");
        } catch (IOException e) {
            System.out.println("跳转错误");
        }
    }
}
