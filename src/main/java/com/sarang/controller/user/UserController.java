package com.sarang.controller.user;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sarang.config.SecureUtil;
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

    @Autowired
    private SecureUtil secureutil;

    private static final int pageSize = 10;

    @GetMapping(value="/main.do")
	public String mainPage(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("Check Main Page View");
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("start",0);
        reqMap.put("size",pageSize);
        List<EnoVO> userInfoList = userService.searchUserInfo(reqMap);
        HashMap<String,Object> pageInfo = userService.getUserPageInfo(reqMap);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("userInfoList", userInfoList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));
        model.addAttribute("totalContents", totalContents);
        return "main/mainPage";
	}

    @GetMapping(value="/addEno.do")
    public String addEnoPage(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("addEno Page View");
       
        return "main/addEnoPage";
	}

    @PostMapping(value="/insertEno.do")
    public String insertEno(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("insertEno Data");
        EnoVO enoVO = new EnoVO();
        String eno = request.getParameter("eno");
        String enoPw = request.getParameter("enoPw");
        String celph = request.getParameter("celph");
        String email = request.getParameter("email");

        if(eno.trim().isEmpty() || eno == null){
            model.addAttribute("errorMsg","사원번호는 필수 값입니다.");
            return "redirect:/addEno.do";
        }
        if(enoPw.trim().isEmpty() || eno == null){
            model.addAttribute("errorMsg","사원 비밀번호는 필수 값입니다.");
            return "redirect:/addEno.do";
        }
        enoVO.setEno(eno);
        enoVO.setEnoPw(secureutil.encryptSHA256(enoPw));
        enoVO.setCelph(celph);
        enoVO.setEmail(email);
        userService.insertEnoInfo(enoVO);

        // HashMap<String,Object> resMap = new HashMap<>();
        // model.addAttribute("responseData",resMap);
        return "redirect:/main.do";
	}

    @ResponseBody
    @RequestMapping(value="/getUserInfo.do", method=RequestMethod.GET)
    public  ResponseEntity getUserInfo(HttpSession session, HttpServletRequest request
        , HttpServletResponse response, Model model
        , String seq, String eno, String page) throws Exception {
        ResponseEntity js = new ResponseEntity();

        HashMap<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("seq",seq);
        reqMap.put("eno",eno);
        reqMap.put("start",Integer.parseInt(page));
        reqMap.put("size",pageSize);
        if(page != null){
            reqMap.put("start",Integer.parseInt(page)*10);
        }else{
            reqMap.put("start",0);
        }

        List<EnoVO> userInfoList = userService.searchUserInfo(reqMap);
        HashMap<String,Object> pageInfo = userService.getUserPageInfo(reqMap);
        
        HashMap <String, Object> totalContents = new HashMap<>();
        totalContents.put("userInfoList", userInfoList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));

        js.setSucceed(true);
        js.setData(totalContents);
        return js;
    }

    @ResponseBody
    @RequestMapping(value="/deleteEnoInfo.do", method={RequestMethod.GET,RequestMethod.POST})
    public  ResponseEntity deleteEnoInfo(HttpSession session, HttpServletRequest request
        , HttpServletResponse response, Model model
        , @RequestParam(value="delList", required=false) List<String> delList
        ) throws Exception {
        ResponseEntity js = new ResponseEntity();
        userService.deleteEnoInfo(delList);
        js.setSucceed(true);
        js.setMessage("1");
        return js;
    }
}
