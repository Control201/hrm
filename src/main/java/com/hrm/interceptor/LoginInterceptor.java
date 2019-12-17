package com.hrm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @project : HRM
 * @description : 用户登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    //该方法会在控制器方法前执行,判断是否继续执行后续操作.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        if(request.getSession().getAttribute("user")!=null){
            return true;
        }
            request.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(request, response);
            return false;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
