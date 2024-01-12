package com.sarang.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ErrorHandler implements ErrorController{
    
    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        // error로 들어온 에러의 status를 불러온다 (ex:404,500,403...)
        
        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "main/errorPage";
            } else {
                return "main/errorPage";
            }
        }

        return "main/errorPage";
    }
}