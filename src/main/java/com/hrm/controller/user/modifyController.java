package com.hrm.controller.user;

import com.hrm.bean.User;
import com.hrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zy
 * @Description:
 */

@RequestMapping("modify")
@Controller
public class modifyController {

    @Autowired
    UserService userService;

    /**
     *
     * 功能描述: 跳转到用户信息修改页面
     *
     * @param: model,session
     * @return: String
     */
    @RequestMapping("toModifyInfo.html")
    public String toInfo(Model model, HttpSession session){
        User user =(User) session.getAttribute("user");
        model.addAttribute("userInfo",user);
        return "set/user/userInfo";
    }

    /**
     *
     * 功能描述: 跳转到用户密码修改界面
     *
     * @param: null
     * @return: String
     */
    @RequestMapping("toModifyPwd.html")
    public String toPwd(){ return "set/user/userPwd";}

    /**
     *
     * 功能描述: 跳转到用户邮箱修改界面
     *
     * @param: null
     * @return: String
     */
    @RequestMapping("toModifyEmail.html")
    public String toEmail(Model model, HttpSession session){
        User user = (User)session.getAttribute("user");
        model.addAttribute("userInfo",user);
        return "set/user/userEmail";
    }


    /**
     *
     * 功能描述: 上传用户头像
     *
     * @param: MultipartFile file,session
     * @return: map
     */
    @RequestMapping("uploadUserIcon.html")
    @ResponseBody
    public Map<String,Object> uploadUserIcon(@RequestParam(value = "file",required = false)MultipartFile file, HttpSession session, HttpServletRequest request){
            Map<String,Object> map1 = new HashMap<>();
            Map<String,Object> map2 = new HashMap<>();
            User user =(User) session.getAttribute("user");

            String relaFilePath = userService.uploadUserIcon(file,request,user.getId());
            System.out.println(relaFilePath);

            map1.put("code",0);
            map1.put("msg", "");
            map1.put("data", map2);
            map2.put("src", relaFilePath);

            return map1;
    }

    /**
     *
     * 功能描述: 验证邮箱验证码是否正确
     *
     * @param: code,user,session
     * @return: map
     */
    @RequestMapping("emailCodeTest.html")
    @ResponseBody
    public Map<String,Object> emailCodeTest(@RequestParam(value = "code") String code,User user,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        if(userService.getUserByEmail(user.getEmail()) == null){
            map.put("data","emailNotExist");
            return map;
        }
        else if(!userService.testEmailCode(code,session)){
            map.put("data","codeWrong");
            return map;
        }else {
            map.put("data","codeCorrect");
            return map;
        }
    }

    @RequestMapping("setUserInfo.html")
    @ResponseBody
    public Map<String,Object> setUserInfo(@RequestBody User user,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        map.put("data",userService.setUserInfo(user,session));
        return map;
    }

    @RequestMapping("resetPassword.html")
    @ResponseBody
    public Map<String,Object> resetPwdByOld(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,HttpSession session){
            Map<String,Object> map = new HashMap<>();
            User user = (User) session.getAttribute("user");
            map.put("data",userService.resetPwdByOld(user,oldPassword,newPassword));
            return map;
    }

    @RequestMapping("resetEmail.html")
    @ResponseBody
    public Map<String,Object> resetEmail(@RequestParam("oldEmail")String oldEmail,@RequestParam("newEmail")String newEmail,HttpSession session){
            Map<String,Object> map = new HashMap<>();
            User user = (User)session.getAttribute("user");
            map.put("data",userService.resetEmail(user,oldEmail,newEmail));
            return map;
    }
}
