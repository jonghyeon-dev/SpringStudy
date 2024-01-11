package com.sarang.controller.main;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping(value="/user")
public class UserController {

    // @Autowired
    // private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecureUtil secureutil;

    private static final int pageSize = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping(value="/userMain.do")
	public String userMainPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
		LOGGER.info("Check User Main Page View");
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("start",0);
        reqMap.put("size",pageSize);
        List<EnoVO> userInfoList = userService.searchUserInfo(reqMap);
        HashMap<String,Object> pageInfo = userService.getUserPageInfo(reqMap);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("userInfoList", userInfoList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));
        model.addAttribute("totalContents", totalContents);
        return "user/userPage";
	}

    @GetMapping(value="/addUser.do")
    public String addEnoPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
		LOGGER.info("addUser Page View");
       
        return "user/addUserPage";
	}

    @PostMapping(value="/insertUser.do")
    public String insertEno(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("insertUser Data");
        EnoVO enoVO = new EnoVO();
        String eno = request.getParameter("eno").trim();
        String enoPw = request.getParameter("enoPw").trim();
        String name = request.getParameter("name").trim();
        String celph = request.getParameter("celph").trim();
        String email = request.getParameter("email").trim();

        if(eno.trim().isEmpty() || eno == null){
            model.addAttribute("errorMsg","아이디는 필수 값입니다.");
            return "redirect:/addUser.do";
        }else if(enoPw.trim().isEmpty() || eno == null){
            model.addAttribute("errorMsg","패스워드는 필수 값입니다.");
            return "redirect:/addUser.do";
        }else if(name.trim().isEmpty() || name == null){
             model.addAttribute("errorMsg","이름은 필수 값입니다.");
            return "redirect:/addUser.do";
        }
        //아이디 중복체크 체크루틴 추가 할 것 Start

        //아이디 중복체크 체크루틴 추가 할 것 End
        enoVO.setEno(eno);
        enoVO.setEnoPw(secureutil.encryptSHA256(enoPw));
        enoVO.setName(name);
        enoVO.setCelph(celph);
        enoVO.setEmail(email);
        userService.insertUserInfo(enoVO);

        // HashMap<String,Object> resMap = new HashMap<>();
        // model.addAttribute("responseData",resMap);
        return "redirect:/userMain.do";
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
    @RequestMapping(value="/deleteUserInfo.do", method={RequestMethod.GET,RequestMethod.POST})
    public  ResponseEntity deleteEnoInfo(HttpSession session, HttpServletRequest request
        , HttpServletResponse response, Model model
        , @RequestParam(value="delList", required=false) List<String> delList
        ) throws Exception {
        ResponseEntity js = new ResponseEntity();
        userService.deleteUserInfo(delList);
        js.setSucceed(true);
        js.setMessage("1");
        return js;
    }
    
}
