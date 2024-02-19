package com.sarang.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sarang.model.AdminVO;
import com.sarang.model.UserVO;


@Component
public class CertificationInterceptor implements HandlerInterceptor{
 
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        UserVO loginVO = (UserVO) session.getAttribute("userLogin");
        AdminVO adminVO = (AdminVO) session.getAttribute("adminLogin");
        // 사용자만
        if(request.getRequestURI().indexOf("/user/") >= 0){
            if(ObjectUtils.isEmpty(loginVO)){
                response.sendRedirect("/error");
                return false;
            }
        }
        // 관리자만
        if(request.getRequestURI().indexOf("/admin/") >= 0){
            if(ObjectUtils.isEmpty(adminVO)){
                response.sendRedirect("/error");
                return false;
            }
        }
        // 둘다 포함
        if(request.getRequestURI().indexOf("/file/") >= 0){
            if(ObjectUtils.isEmpty(adminVO) && ObjectUtils.isEmpty(loginVO)){
                response.sendRedirect("/error");
                return false;
            }
        }

        return true;
    }
 
    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler,
    @Nullable ModelAndView modelAndView) throws Exception {

        
    }
 
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response
    , @NonNull Object handler, @Nullable Exception ex) throws Exception{

        
    }
 
}