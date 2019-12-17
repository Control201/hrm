package com.hrm.controller.user;


import com.hrm.bean.User;
import com.hrm.interceptor.Token;
import com.hrm.service.UserService;
import com.hrm.util.EmailVerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("forget")
public class ForgetController {

    @Autowired
    UserService userService;

    /**
     *
     * 功能描述: 跳转到忘记密码页面
     *
     * @param: null
     * @return: String
     */
    @RequestMapping("toForget.html")
    @Token(save = true)
    public String toForget(){return "user/forget";}

    /**
     *
     * 功能描述: 检验邮箱是否被注册
     *
     * @param: user
     * @return: map
     */
    @RequestMapping("emailCheck.html")
    @ResponseBody
    public Map<String,Object> mailCheck(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("data",userService.emailCheck(user.getEmail()));
        return map;
    }

    /**
     *
     * 功能描述: 向邮箱发送验证码
     *
     * @param: user , session
     * @return: map
     */
    @RequestMapping("sendVerifyCodeToEmail.html")
    @ResponseBody
    public Map<String,Object> sendCodeToEmail(User user, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        EmailVerifyCode emailVerifyCode = userService.sendCodeToEmail(user.getEmail());
        session.setAttribute("emailVerifyCode",emailVerifyCode);
        map.put("data","sendSuccess");
        return map;
    }
    /**
     *
     * 功能描述: 验证验证码是否正确并修改密码
     *
     * @param: code,user,session
     * @return: map
     */
    @RequestMapping("emailCodeTest.html")
    @Token(remove = true)
    @ResponseBody
    public Map<String,Object> emailCodeTest(@RequestParam(value = "code") String code, User user,HttpSession session){
            Map<String,Object> map = new HashMap<>();
            if(!userService.testEmailCode(code,session)){
                 map.put("data","codeWrong");
                 return map;
            }else {
                map.put("data",userService.resetPwdByEmail(user));
                return map;
            }
    }


}
