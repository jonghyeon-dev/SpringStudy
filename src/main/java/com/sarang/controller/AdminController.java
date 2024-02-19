package com.sarang.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sarang.config.SecureUtil;
import com.sarang.model.AdminVO;
import com.sarang.model.UserVO;
import com.sarang.model.common.ResponseEntity;
import com.sarang.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SecureUtil secureutil;

    private static final int pageSize = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @GetMapping(value="/adminLogin.do")
    public String adminLoginPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model, @RequestParam(value="errorMsg",required = false) String errorMsg) throws Exception{
        LOGGER.info("adminLogin View");
        AdminVO adminVO = (AdminVO) session.getAttribute("adminLogin");
        UserVO loginVO = (UserVO) session.getAttribute("userLogin");
        if(!ObjectUtils.isEmpty(adminVO) || !ObjectUtils.isEmpty(loginVO)){
            return "redirect:/main.do";
        }
        if(errorMsg != null && !errorMsg.isEmpty()){
            model.addAttribute("errorMsg", errorMsg);
        }
        return "admin/adminLoginPage";
    }

    @PostMapping(value="/checkAdmin.do")
    public String checkAdmin(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
        LOGGER.info("Check Admin Login Process");
        String eno = request.getParameter("eno").trim();
        String enoPw = request.getParameter("enoPw").trim();
        HashMap<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("eno",eno);
        try {
            reqMap.put("enoPw",secureutil.encryptSHA256(enoPw));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("I'm sorry, but SHA256 is not a valid message digest algorithm");
            reqMap.put("enoPw","");
        }
        
        AdminVO adminVO = adminService.checkAdminLogin(reqMap);
        if(ObjectUtils.isEmpty(adminVO)){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "등록되지 않은 아이디이거나 아이디 또는 비밀번호를 잘못 입력했습니다.");
            return "redirect:/adminLogin.do";
        }
        session.setAttribute("adminLogin", adminVO);
        return "redirect:/main.do";
    }

    @GetMapping(value="/admin/adminAccountMain.do")
	public String userMainPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
		LOGGER.info("Check Admin AccountList Page View");
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("start",0);
        reqMap.put("size",pageSize);
        List<AdminVO> adminInfoList = adminService.searchAdminInfo(reqMap);
        HashMap<String,Object> pageInfo = adminService.getAdminPageInfo(reqMap);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("adminInfoList", adminInfoList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));
        model.addAttribute("totalContents", totalContents);
        return "admin/adminAccountPage";
	}

    @GetMapping(value="/admin/addAdmin.do")
    public String addEnoPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
		LOGGER.info("addAdmin Page View");
       
        return "admin/addAdminPage";
	}

    @PostMapping(value="/admin/insertAdmin.do")
    public String insertAdmin(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("insertAdmin Data");
        AdminVO adminVO = new AdminVO();
        String eno = request.getParameter("eno").trim();
        String enoPw = request.getParameter("enoPw").trim();
        String name = request.getParameter("name").trim();
        String celph = request.getParameter("celph").trim();
        String email = request.getParameter("email").trim();

        if(eno.trim().isEmpty() || eno == null){
            model.addAttribute("errorMsg","아이디는 필수 값입니다.");
            return "redirect:/addAdmin.do";
        }else if(enoPw.trim().isEmpty() || eno == null){
            model.addAttribute("errorMsg","패스워드는 필수 값입니다.");
            return "redirect:/addAdmin.do";
        }else if(name.trim().isEmpty() || name == null){
             model.addAttribute("errorMsg","이름은 필수 값입니다.");
            return "redirect:/addAdmin.do";
        }
        
        //아이디 중복체크 루틴 추가 할 것 

        adminVO.setEno(eno);
        adminVO.setEnoPw(secureutil.encryptSHA256(enoPw));
        adminVO.setName(name);
        adminVO.setCelph(celph);
        adminVO.setEmail(email);
        adminService.insertAdminInfo(adminVO);

        // HashMap<String,Object> resMap = new HashMap<>();
        // model.addAttribute("responseData",resMap);
        return "redirect:/adminAccountMain.do";
	}

    @ResponseBody
    @RequestMapping(value="/admin/getAdminInfo.do", method=RequestMethod.GET)
    public  ResponseEntity getAdminInfo(javax.servlet.http.HttpSession session, javax.servlet.http.HttpServletRequest request
        , javax.servlet.http.HttpServletResponse response, Model model
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

        List<AdminVO> adminInfoList = adminService.searchAdminInfo(reqMap);
        HashMap<String,Object> pageInfo = adminService.getAdminPageInfo(reqMap);
        
        HashMap <String, Object> totalContents = new HashMap<>();
        totalContents.put("adminInfoList", adminInfoList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));

        js.setSucceed(true);
        js.setData(totalContents);
        return js;
    }

    @ResponseBody
    @RequestMapping(value="/admin/deleteAdminInfo.do", method={RequestMethod.GET,RequestMethod.POST})
    public  ResponseEntity deleteEnoInfo(HttpSession session, HttpServletRequest request
        , HttpServletResponse response, Model model
        , @RequestParam(value="delList", required=false) List<String> delList
        ) throws Exception {
        ResponseEntity js = new ResponseEntity();
        // 관리자 아이디 1개 이하일 시 삭제 불가 체크루틴 추가 
        adminService.deleteAdminInfo(delList);
        js.setSucceed(true);
        js.setMessage("1");
        return js;
    }
    
}
