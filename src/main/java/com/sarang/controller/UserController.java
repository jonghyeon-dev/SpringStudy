package com.sarang.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sarang.config.SecureUtil;
import com.sarang.model.UserVO;
import com.sarang.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecureUtil secureutil;

    private static final int pageSize = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @GetMapping(value="/login.do")
    public String loginPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model, @RequestParam(value="errorMsg", required=false) String errorMsg) {
        LOGGER.info("User Login Page View");
        if(errorMsg != null && !errorMsg.isEmpty()){
            model.addAttribute("errorMsg", errorMsg);
        }
        return "user/loginPage";
    }

    @GetMapping(value="/logout.do")
    public String logout(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model){
        LOGGER.info("Logout Process");
        session.invalidate();
        return "redirect:/main.do";
    }

    @PostMapping(value="/checkUserLogin.do")
    public String checkLogin(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, RedirectAttributes redirectAttributes){
        LOGGER.info("Check User Login Process");
        String userId = request.getParameter("userId").trim();
        String userPw = request.getParameter("userPw").trim();
        HashMap<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("userId",userId);
        try {
            reqMap.put("userPw",secureutil.encryptSHA256(userPw));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("I'm sorry, but SHA256 is not a valid message digest algorithm");
            reqMap.put("userPw","");
        }
        
        UserVO userVO = userService.checkUserLogin(reqMap);
        if(ObjectUtils.isEmpty(userVO)){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "등록되지 않은 아이디이거나 아이디 또는 비밀번호를 잘못 입력했습니다.");
            return "redirect:/user/login.do";
        }
        session.setAttribute("userLogin", userVO);
        return "redirect:/main.do";
    }
}
