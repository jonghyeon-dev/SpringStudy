package com.sarang.controller.user;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sarang.model.common.ResponseEntity;
import com.sarang.model.user.EnoVO;
import com.sarang.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value="/main.do")
	public String mainPage(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("Check Main Page View");
        List<EnoVO> userInfoList = userService.getUserInfo();
        model.addAttribute("userInfoList", userInfoList);
        return "main/mainPage";
	}

    @ResponseBody
    @RequestMapping(value="/getUserInfo.do", method=RequestMethod.GET)
    public  ResponseEntity getUserInfo(HttpSession session, HttpServletRequest request
        , HttpServletResponse response, Model model
        , String seq, String eno) throws Exception {
        ResponseEntity js = new ResponseEntity();

        HashMap<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("seq",seq);
        reqMap.put("eno",eno);

        List<EnoVO> userInfoList = userService.searchUserInfo(reqMap);
        for(EnoVO userInfo : userInfoList){
            System.out.println(userInfo.toHashMap().toString());
            System.out.println(userInfo.toJsonObj().toString());
            System.out.println(userInfo.toString());
        }

        js.setSucceed(true);
        js.setData(userInfoList);
        return js;
    }
}
