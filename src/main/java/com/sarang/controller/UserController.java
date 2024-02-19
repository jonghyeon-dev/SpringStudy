package com.sarang.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import com.sarang.config.SecureUtil;
import com.sarang.model.AdminVO;
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

    // private static final int pageSize = 10;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping(value="/login.do")
    public String loginPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model, @RequestParam(value="errorMsg", required=false) String errorMsg) {
        AdminVO adminVO =(AdminVO) session.getAttribute("adminLogin");
        UserVO loginVO = (UserVO) session.getAttribute("userLogin");
        if(!ObjectUtils.isEmpty(loginVO) || !ObjectUtils.isEmpty(adminVO)){
            return "redirect:/main.do";
        }
        logger.info("User Login Page View");
        if(errorMsg != null && !errorMsg.isEmpty()){
            model.addAttribute("errorMsg", errorMsg);
        }
        return "user/loginPage";
    }

    @GetMapping(value="/logout.do")
    public String logout(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model){
        logger.info("Logout Process");
        session.invalidate();
        return "redirect:/main.do";
    }

    @GetMapping(value="/createAccount.do")
    public String createAccount(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) {
        logger.info("createAccountPage View");
        AdminVO adminVO =(AdminVO) session.getAttribute("adminLogin");
        UserVO loginVO = (UserVO) session.getAttribute("userLogin");
        if(!ObjectUtils.isEmpty(loginVO) || !ObjectUtils.isEmpty(adminVO)){
            return "redirect:/main.do";
        }
        return "user/createAccountPage";
    }

    @PostMapping(value="/checkUser.do")
    public String checkLogin(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, RedirectAttributes redirectAttributes){
        logger.info("Check User Login Process");
        String userId = request.getParameter("userId").trim();
        String userPw = request.getParameter("userPw").trim();
        HashMap<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("userId",userId);
        try {
            reqMap.put("userPw",secureutil.encryptSHA256(userPw));
        } catch (NoSuchAlgorithmException e) {
            logger.error("I'm sorry, but SHA256 is not a valid message digest algorithm");
            reqMap.put("userPw","");
        }
        
        UserVO userVO = userService.checkUserLogin(reqMap);
        if(ObjectUtils.isEmpty(userVO)){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "등록되지 않은 아이디이거나 아이디 또는 비밀번호를 잘못 입력했습니다.");
            return "redirect:/login.do";
        }
        session.setAttribute("userLogin", userVO);
        return "redirect:/main.do";
    }

    @PostMapping(value="/insertUser.do")
    public String addUser(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, RedirectAttributes redirectAttributes) {
        logger.info("Add User Process");
        String userId = request.getParameter("userId").trim();
        String userPw = request.getParameter("userPw").trim();
        String userNm = request.getParameter("userNm").trim();
        String celph = request.getParameter("celph").trim();
        String email = request.getParameter("email").trim();

        if(userId == null || "".equals(userId)){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "ID는 필수 값 입니다.");
            return "redirect:/createAccount.do";
        }else if(userPw == null || "".equals(userPw) ){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "패스워드는 필수 값 입니다.");
            return "redirect:/createAccount.do";
        }else if(userNm == null || "".equals(userNm) ){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "닉네임은 필수 값 입니다.");
            return "redirect:/createAccount.do";
        }

        String checkDup = userService.checkUserDuplication(userId);
        if(checkDup != null || !"".equals(checkDup)){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "중복되는 ID가 있습니다.");
            return "redirect:/createAccount.do";
        }

        String regPhone = "/^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/"; // 한국 휴대폰 번호 정규식
        if(celph != null && !"".equals(celph)){
            if(Pattern.matches(regPhone, celph) == false){
                redirectAttributes.addFlashAttribute("errorMsg"
                , "휴대폰번호를 형식에 맞게 정확히 입력해 주세요.");

                return "redirect:/createAccount.do";
            }
            celph = celph.replaceAll("-", "");// celph 안에 하이픈 전체 공백으로 치환
        }
        
        UserVO userVO = new UserVO();
        userVO.setUserId(userId);
        try {
            userVO.setUserPw(secureutil.encryptSHA256(userPw).toString());
        }catch(NoSuchAlgorithmException e){
            logger.error("I'm sorry, but SHA256 is not a valid message digest algorithm");
            redirectAttributes.addFlashAttribute("errorMsg"
                , "500 서버 오류가 발생하였습니다.");
            return "redirect:/login.do";
        }
        userVO.setUserNm(userNm);
        userVO.setCelph(celph);
        userVO.setEmail(email);
        try{
            userService.insertUserInfo(userVO);
        }catch(Exception e){
            logger.error("DataBase Insert Error");
            redirectAttributes.addFlashAttribute("errorMsg"
                , "500 서버 오류가 발생하였습니다.");
            return "redirect:/login.do";
        }
        
        return "redirect:/login.do";
    }
    
}
