package com.sarang.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sarang.model.AdminVO;
import com.sarang.model.UserVO;


@Component
public class CertificationInterceptor implements HandlerInterceptor{
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        UserVO loginVO = (UserVO) session.getAttribute("userLogin");
        AdminVO adminVO = (AdminVO) session.getAttribute("adminLogin");
        if(request.getRequestURI().indexOf("/user/") >= 0){
            if(ObjectUtils.isEmpty(loginVO)){
                response.sendRedirect("/error");
                return false;
            }
        }
        if(request.getRequestURI().indexOf("/admin/") >= 0){
            if(ObjectUtils.isEmpty(adminVO)){
                response.sendRedirect("/error");
                return false;
            }
        }

        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        
    }
 
}