package com.hrm.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class setCookie {
        public static final String USERNAME_COOKIE ="loginAccount";

        public static final String USERPASSWORD_COOKIE="loginPassword";

        public static final String REMEMBER="remember";

        /**
         * @Function TODO
         * @param    name, password, remember, request, response
         * @return   void
         */
        public static void setUserLoginCookie(String name, String password, String remember
                , HttpServletRequest request, HttpServletResponse response) {
            Cookie loginAccountCookie = new Cookie(USERNAME_COOKIE, name);
            Cookie loginPasswordCookie = new Cookie(USERPASSWORD_COOKIE, password);
            Cookie rememberCookie = new Cookie(REMEMBER, "true");
            //设置Cookie的父路经
            loginAccountCookie.setPath(request.getContextPath() + "/");
            loginPasswordCookie.setPath(request.getContextPath() + "/");
            rememberCookie.setPath(request.getContextPath() + "/");
            //获取是否保存Cookie（例如：复选框的值）
            if (remember == null) {
                //不保存Cookie
                loginAccountCookie.setMaxAge(0);
                loginPasswordCookie.setMaxAge(0);
                rememberCookie.setMaxAge(0);
            } else {
                //保存Cookie的时间长度，单位为秒
                loginAccountCookie.setMaxAge(7 * 24 * 60 * 60);
                loginPasswordCookie.setMaxAge(7 * 24 * 60 * 60);
                rememberCookie.setMaxAge(7 * 24 * 60 * 60);
            }
            //加入Cookie到响应头
            response.addCookie(loginAccountCookie);
            response.addCookie(loginPasswordCookie);
            response.addCookie(rememberCookie);
        }

}
