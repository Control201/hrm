package com.hrm.service.impl;

import com.hrm.bean.User;
import com.hrm.dao.UserMapper;
import com.hrm.service.UserService;
import com.hrm.util.EmailVerifyCode;
import com.hrm.util.MailUtil;
import com.hrm.util.MyTimeUtil;
import com.hrm.util.VerifyCode;
import com.hrm.util.MySecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User loginCheck(User user) {
        User userTemp = userMapper.getUserByName(user.getName());
        if(userTemp == null){
            return null;
        }else if(userTemp.getPassword().equals(MySecurity.encryptUserPassword(user.getPassword(),userTemp.getId()))){
            return userTemp;
        }else return null;
    }

    @Override
    public void updateUserSession(String id,HttpSession session) {
        User user = userMapper.getUserById(id);
        session.setAttribute("user",user);
    }

    @Override
    public String userRegiste(User user) {
        if(userMapper.getUserByName(user.getName())!=null){
            return "userExist";
    }
        if(userMapper.getUserByEmail(user.getEmail())!=null){
        return "emailExist";
    }
        if(userMapper.getUserByPhone(user.getPhone())!=null){
        return "phoneExist";
    }
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setPassword(MySecurity.encryptUserPassword(user.getPassword(),user.getId()));
        user.setIcon("/imgs/icon/user_default_icon.jpg");
        user.setAddress("这是默认地址,请及时修改");
        userMapper.insertUser(user);
        return "regSuccess";
    }

    @Override
    public String emailCheck(String email) {
        if(userMapper.getUserByEmail(email)!=null){
            return "eamilExist";
        }else return "emailNotExist";
    }

    @Override
    public String uploadUserIcon(MultipartFile file, HttpServletRequest request, String userId) {
        {
            String prefix = "";

            String uploadDir ="/imgs/icon";
            String absoluFilePath = "";
            String relaFilePath = "";

            DateFormat format1 = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
            String dateStr = format1.format(new Date());

            //保存上传
            OutputStream out = null;
            InputStream fileInput = null;
            try {
                if (file != null) {
                    String originalName = file.getOriginalFilename();
                    prefix = originalName.substring(originalName.lastIndexOf(".") + 1);
                    String filepath = request.getServletContext().getRealPath(uploadDir); //文件存放文件夹路径名
                    filepath = filepath.replace("\\", "/");
                    String fileName = userId + "_icon_" + dateStr + "." + prefix; //文件名
                    absoluFilePath = filepath + "/" + fileName;//绝对路径
                    relaFilePath = uploadDir + "/" + fileName;
                    System.out.println(relaFilePath);
                    File files = new File(absoluFilePath);
                    //打印查看上传路径
                    System.out.println(filepath);
                    if (!files.getParentFile().exists()) {
                        files.getParentFile().mkdirs();
                    }
                    file.transferTo(files);
                }
            } catch (Exception e) {
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (fileInput != null) {
                        fileInput.close();
                    }
                } catch (IOException e) {
                }
            }

            return relaFilePath;
        }

    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public EmailVerifyCode sendCodeToEmail(String email) {
        String emailaddress = email;
        String code = VerifyCode.randomCode();
        String msg = "收到来自人力资源管理系统的验证码:\n" + code + "\n 有效时间:" + EmailVerifyCode.validTime/(1000 * 60) + "分钟";

        try {
            MailUtil.SendMail(emailaddress,msg);
            return new EmailVerifyCode(email,code, MyTimeUtil.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean testEmailCode(String code, HttpSession session) {
        EmailVerifyCode emailVerifyCode = (EmailVerifyCode) session.getAttribute("emailVerifyCode");
        if(code == null || !code.equals(emailVerifyCode.getCode()) || !emailVerifyCode.isValid() ){
            return false;
        }else  return true;
    }

    @Override
    public String setUserInfo(User user,HttpSession session) {
            userMapper.updateUser(user);
            session.setAttribute("user",userMapper.getUserById(user.getId()));
            return "successReset";
    }

    @Override
    public String resetPwdByEmail(User user) {
        User userTemp = userMapper.getUserByEmail(user.getEmail());
        userTemp.setPassword(MySecurity.encryptUserPassword(user.getPassword(),userTemp.getId()));
        System.out.println(userTemp.getPassword());
        userMapper.resetPwdByEmail(userTemp);
        return "resetSuccess";
    }

    @Override
    public String resetPwdByOld(User user, String oldPassword, String newPassword) {
        if(!user.getPassword().equals(MySecurity.encryptUserPassword(oldPassword,user.getId()))){
            return "wrongPwd";
        }else {
            user.setPassword(MySecurity.encryptUserPassword(newPassword,user.getId()));
            userMapper.updateUser(user);
            return "successReset";
        }
    }

    @Override
    public String resetEmail(User user, String oldEmail, String newEmail) {
        if(userMapper.getUserByEmail(newEmail)!=null){
            return "newEmailExist";
        }else{
            user.setEmail(newEmail);
            userMapper.updateUser(user);
            return "successReset";
        }

    }

    @Override
    public User getUser(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public boolean ifUserIsAdmin(HttpSession session) {
        User userTemp = (User)session.getAttribute("user");
        if(userTemp.getAuthority().equals(2)){
            return true;
        }else return false;
    }
}
