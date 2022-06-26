package com.yidian.registration.interceptors;

import com.yidian.registration.entity.SysUserEntity;
import com.yidian.registration.service.IUserService;
import com.yidian.registration.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright (C), 2018
 *
 * @author jintian
 * @ClassName: Interceptor
 * @Description: ${TODO}
 * @Date 2018-3-23
 */
public class LoginInterceptor implements HandlerInterceptor {
    public static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    public String defultLogin="/login.html";//默认登录页面

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                if("loginName".equals(cookie.getName())){
                    String loginName = cookie.getValue();
                    SysUserEntity userEntity = userService.getUserInfoByName(loginName);
                    if(Tools.isNotNull(userEntity)){
                        return  true;
                    }
                }
            }
        }
//        response.sendRedirect(defultLogin);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
