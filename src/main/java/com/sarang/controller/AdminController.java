package com.sarang.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sarang.config.FileUtils;
import com.sarang.config.SecureUtil;
import com.sarang.model.BoardVO;
import com.sarang.model.HeadContentVO;
import com.sarang.model.UserVO;
import com.sarang.model.common.FileVO;
import com.sarang.model.common.ResponseData;
import com.sarang.service.BoardService;
import com.sarang.service.HeadContentService;
import com.sarang.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private HeadContentService headContentService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private SecureUtil secureutil;

    private static final int pageSize = 10;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping(value="/adminLogin.do")
    public String adminLoginPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model, @RequestParam(value="errorMsg",required = false) String errorMsg) throws Exception{
        logger.info("adminLogin View");
        UserVO loginVO = (UserVO) session.getAttribute("userLogin");
        if(!ObjectUtils.isEmpty(loginVO)){
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
        logger.info("Check Admin Login Process");
        String userId = request.getParameter("userId").trim();
        String userPwd = request.getParameter("userPwd").trim();
        HashMap<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("userId",userId);
        try {
            reqMap.put("userPwd",secureutil.encryptSHA256(userPwd));
        } catch (NoSuchAlgorithmException e) {
            logger.error("I'm sorry, but SHA256 is not a valid message digest algorithm");
            redirectAttributes.addFlashAttribute("errorMsg"
                , "로그인 체크 도중 오류가 발생하였습니다.");
            return "redirect:adminLogin.do";
        }
        
        UserVO adminVO = userService.checkAdminLogin(reqMap);
        if(ObjectUtils.isEmpty(adminVO)){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "등록되지 않은 아이디이거나 아이디 또는 비밀번호를 잘못 입력했습니다.");
            return "redirect:/adminLogin.do";
        }
        session.setAttribute("userLogin", adminVO);
        return "redirect:/main.do";
    }

    @GetMapping(value="/admin/adminAccount.do")
	public String userMainPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
		logger.info("Check Admin AccountList Page View");
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("userGrant","0");
        reqMap.put("start",0);
        reqMap.put("size",pageSize);
        List<UserVO> adminInfoList = userService.getUserInfo(reqMap);
        HashMap<String,Object> pageInfo = userService.getUserPageInfo(reqMap);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("adminInfoList", adminInfoList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));
        model.addAttribute("totalContents", totalContents);
        return "admin/adminAccountPage";
	}

    @GetMapping(value="/admin/addAdmin.do")
    public String addAdminPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
		logger.info("addAdmin Page View");
       
        return "admin/addAdminPage";
	}

    @PostMapping(value="/admin/insertAdmin.do")
    public String insertAdmin(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
		logger.info("insertAdmin Data");
        UserVO UserVO = new UserVO();
        String userId = request.getParameter("userId").trim();
        String userPwd = request.getParameter("userPwd").trim();
        String userPwdCheck = request.getParameter("userPwdCheck").trim();
        String userNm = request.getParameter("userNm").trim();
        String celph = request.getParameter("celph").trim();
        String email = request.getParameter("email").trim();

        if(userId.trim().isEmpty() || userId == null){
            redirectAttributes.addFlashAttribute("errorMsg","아이디는 필수 값입니다.");
            return "redirect:/addAdmin.do";
        }else if(userPwd.trim().isEmpty() || userPwd == null){
            redirectAttributes.addFlashAttribute("errorMsg","패스워드는 필수 값입니다.");
            return "redirect:/addAdmin.do";
        }else if(userPwdCheck.trim().isEmpty() || userPwdCheck == null){
            redirectAttributes.addFlashAttribute("errorMsg","패스워드는 필수 값입니다.");
            return "redirect:/addAdmin.do";
        }else if(userNm.trim().isEmpty() || userNm == null){
            redirectAttributes.addFlashAttribute("errorMsg","이름은 필수 값입니다.");
            return "redirect:/addAdmin.do";
        }

        if(!userPwd.equals(userPwdCheck)){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "패스워드와 확인 값이 다릅니다.");
            return "redirect:/createAccount.do";
        }
        
        //아이디 중복체크 루틴
        String checkDup = userService.checkUserDuplication(userId);
        if(checkDup != null){
            redirectAttributes.addFlashAttribute("errorMsg"
                , "중복되는 ID가 있습니다.");
            return "redirect:/addAdmin.do";
        }
        String regPhone = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$"; // 한국 휴대폰 번호 정규식
        if(celph != null && !"".equals(celph)){
            if(Pattern.matches(regPhone, celph) == false){
                redirectAttributes.addFlashAttribute("errorMsg"
                , "휴대폰번호를 형식에 맞게 정확히 입력해 주세요.");

                return "redirect:/addAdmin.do";
            }
            celph = celph.replaceAll("-", "");// celph 안에 하이픈 전체 공백으로 치환
        }
        UserVO.setUserId(userId);
        UserVO.setUserPwd(secureutil.encryptSHA256(userPwd));
        UserVO.setUserNm(userNm);
        UserVO.setUserGrant("0");
        UserVO.setCelph(celph);
        UserVO.setEmail(email);
        userService.insertUserInfo(UserVO);

        return "redirect:/admin/adminAccount.do";
	}

    @ResponseBody
    @RequestMapping(value="/admin/getAccountInfo.do", method=RequestMethod.GET)
    public  ResponseData getAdminInfo(HttpSession session, HttpServletRequest request
        , HttpServletResponse response, Model model
        , String seq, String userId, String page) throws Exception {
        ResponseData js = new ResponseData();
        HashMap<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("seq",seq);
        reqMap.put("userId",userId);
        reqMap.put("userGrant","0");
        reqMap.put("start",Integer.parseInt(page));
        reqMap.put("size",pageSize);
        if(page != null){
            reqMap.put("start",Integer.parseInt(page)*10);
        }else{
            reqMap.put("start",0);
        }

        List<UserVO> adminInfoList = userService.getUserInfo(reqMap);
        HashMap<String,Object> pageInfo = userService.getUserPageInfo(reqMap);
        
        HashMap <String, Object> totalContents = new HashMap<>();
        totalContents.put("adminInfoList", adminInfoList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));

        js.setIsSucceed(true);
        js.setData(totalContents);
        return js;
    }

    @ResponseBody
    @RequestMapping(value="/admin/deleteAdminInfo.do", method=RequestMethod.POST)
    public  ResponseData deleteAdminInfo(HttpSession session, HttpServletRequest request
        , HttpServletResponse response, Model model
        , @RequestParam(value="delList", required=false) List<String> delList
        ) throws Exception {
        ResponseData js = new ResponseData();
        UserVO userVO = (UserVO)session.getAttribute("userLogin");

        if(delList.contains("0")){
            js.setIsSucceed(false);
            js.setMessage("삭제 실패");
        }else if(delList.contains(userVO.getSeq().toString())){
            js.setIsSucceed(false);
            js.setMessage("현재 자신을 삭제할 수 없습니다.");
        }else{
            userService.deleteUserInfo(delList);
            js.setIsSucceed(true);
            js.setMessage("삭제 성공");
        }
        return js;
    }

    @GetMapping(value="/admin/userAccount.do")
    public String userAccountPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model)throws Exception {
        logger.info("Check User AccountList Page View");
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("start",0);
        reqMap.put("size",pageSize);
        List<UserVO> userInfoList = userService.getUserInfo(reqMap);
        HashMap<String,Object> pageInfo = userService.getUserPageInfo(reqMap);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("userInfoList", userInfoList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));
        model.addAttribute("totalContents", totalContents);
        return "admin/userAccountPage";
    }

    @ResponseBody
    @GetMapping(value="/admin/getUserInfo.do")
    public  ResponseData getUserInfo(HttpSession session, HttpServletRequest request
        , HttpServletResponse response, Model model
        , String seq, String userId, String userNm, String page) throws Exception {
        ResponseData js = new ResponseData();
        HashMap<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("seq",seq);
        reqMap.put("userId",userId);
        reqMap.put("userNm",userNm);
        reqMap.put("start",Integer.parseInt(page));
        reqMap.put("size",pageSize);
        if(page != null){
            reqMap.put("start",Integer.parseInt(page)*10);
        }else{
            reqMap.put("start",0);
        }

        List<UserVO> userInfoList = userService.getUserInfo(reqMap);
        HashMap<String,Object> pageInfo = userService.getUserPageInfo(reqMap);
        
        HashMap <String, Object> totalContents = new HashMap<>();
        totalContents.put("userInfoList", userInfoList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));

        js.setIsSucceed(true);
        js.setData(totalContents);
        return js;
    }

    // @ResponseBody
    // @RequestMapping(value="/admin/deleteUserInfo.do",method=RequestMethod.POST)
    // public  ResponseData deleteUserInfo(HttpSession session, HttpServletRequest request
    //     , HttpServletResponse response, Model model
    //     , @RequestParam(value="delList", required=false) List<String> delList
    //     ) throws Exception {

    //     ResponseData js = new ResponseData();
    //     userService.deleteUserInfo(delList);
    //     js.setIsSucceed(true);
    //     js.setMessage("1");
    //     return js;
    // }
    
    @GetMapping(value="/admin/headContent.do")
    public String headContentMain(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
		logger.info("headContentMain Page View");
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("delYn","N");
        reqMap.put("start",0);
        reqMap.put("size",pageSize);
        List<HeadContentVO> headContentsList = headContentService.getHeadContents(reqMap);
        HashMap<String,Object> pageInfo = headContentService.getHeadContentsPageInfo(reqMap);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("headContentsList", headContentsList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));
        model.addAttribute("totalContents", totalContents);
       
        return "admin/headContentPage";
	}

    @ResponseBody
    @GetMapping(value="/admin/getHeadContents.do")
    public ResponseData getHeadContents(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Integer contentSeq, String title, String delYn, Integer page){
        logger.info("getHeadContents Ajax");
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("contentSeq", contentSeq);
        reqMap.put("title",title);
        reqMap.put("delYn",delYn);
        reqMap.put("start",page);
        reqMap.put("size",pageSize);

        List<HeadContentVO> headContentsList = headContentService.getHeadContents(reqMap);
        HashMap<String,Object> pageInfo = headContentService.getHeadContentsPageInfo(reqMap);
        
        HashMap<String,Object> resMap = new HashMap<>();
        resMap.put("headContentsList",headContentsList);
        resMap.put("totalPages", pageInfo.get("totalPage"));

        ResponseData js = new ResponseData();
        js.setIsSucceed(true);
        js.setMessage("Succeed");
        js.setData(resMap);
        return js;
    }

    @GetMapping(value="/admin/headContentWrite.do")
    public String headContentWrite(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
		logger.info("headContentWrite Page View");
        model.addAttribute("status","insert");
        return "admin/headContentWritePage";
	}

    @PostMapping(value="/admin/insertHeadContent.do")
    public String insertHeadContent(HttpSession session, MultipartHttpServletRequest request
    , HttpServletResponse response , RedirectAttributes redirectAttributes
    , HeadContentVO headContentVO, MultipartFile uploadImage, String createNotice) throws Exception {
		logger.info("insertHeadContent");
        UserVO userVO = (UserVO)session.getAttribute("userLogin");

        headContentVO.setStrDate(headContentVO.getStrDate().replaceAll("-", ""));
        headContentVO.setEndDate(headContentVO.getEndDate().replaceAll("-", ""));
        // 공지사항 자동생성 첨부파일은 같이 올라가지 않음 제목과 내용만 올라감
        if(createNotice != null && !createNotice.isEmpty()){
            BoardVO createBoardVO = new BoardVO();
            createBoardVO.setBoardTitle(headContentVO.getTitle());
            createBoardVO.setBoardCate("notice");
            createBoardVO.setBoardCntnt(headContentVO.getCntnt());
            createBoardVO.setCretUser(userVO.getSeq().toString());
            boardService.insertBoardDetailInfo(createBoardVO);
            headContentVO.setConnectUrl("/board/notice/detail/"+createBoardVO.getBoardId());
        }
        FileVO retFileVO = fileUtils.FileUpload(session, uploadImage);
        if(retFileVO.getFileId() == null){
            if (request.getHeader("Referer") != null) {
                redirectAttributes.addFlashAttribute("errorMsg","파일 등록에 실패하였습니다.");
                return "redirect:"+ request.getHeader("Referer");
            }else{
                redirectAttributes.addFlashAttribute("errorMsg","파일 등록에 실패하였습니다.");
                return "redirect:/admin/headContent.do";
            }
        }
        headContentVO.setImgFileId(retFileVO.getFileId());
        headContentVO.setCretUser(userVO.getSeq().toString());
        headContentVO.setChgUser(userVO.getSeq().toString());
        headContentService.insertHeadContent(headContentVO);

        return "redirect:/admin/headContent.do";
	}

    @GetMapping(value="/admin/headContentModify/{headContentId}")
    public String headContentModify(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model
    , @PathVariable("headContentId") Integer headContentId) throws Exception {
		logger.info("headContentWrite Page View");
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("contentSeq",headContentId);
        HeadContentVO headContentVO = headContentService.getHeadContentDetail(reqMap);
        model.addAttribute("headContentInfo", headContentVO);
        model.addAttribute("status","update");
        return "admin/headContentWritePage";
	}

    @PostMapping(value="/admin/updateHeadContent/{contentSeq}")
    public String updateHeadContent(HttpSession session, MultipartHttpServletRequest request
    , HttpServletResponse response , RedirectAttributes redirectAttributes
    , HeadContentVO headContentVO, MultipartFile uploadImage) throws Exception{
        logger.info("updateHeadContent");
        UserVO userVO = (UserVO)session.getAttribute("userLogin");
        headContentVO.setStrDate(headContentVO.getStrDate().replaceAll("-", ""));
        headContentVO.setEndDate(headContentVO.getEndDate().replaceAll("-", ""));
        if(uploadImage != null && !uploadImage.isEmpty()){
            FileVO retFileVO = fileUtils.FileUpload(session, uploadImage);
            if(retFileVO.getFileId() == null){
                if (request.getHeader("Referer") != null) {
                    redirectAttributes.addFlashAttribute("errorMsg","파일 등록에 실패하였습니다.");
                    return "redirect:"+ request.getHeader("Referer");
                }else{
                    redirectAttributes.addFlashAttribute("errorMsg","파일 등록에 실패하였습니다.");
                    return "redirect:/admin/headContent.do";
                }
            }
            headContentVO.setImgFileId(retFileVO.getFileId());
        }
        headContentVO.setChgUser(userVO.getSeq().toString());
        headContentService.updateHeadContent(headContentVO);

        return "redirect:/admin/headContent.do";
    }

    @GetMapping(value="/admin/deleteHeadContent/{contentSeq}")
    public String headContentDelete(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , RedirectAttributes redirectAttributes
    , @PathVariable("contentSeq") Integer contentSeq) throws Exception {
        logger.info("headContentDelete");
        headContentService.deleteHeadContent(contentSeq);
        redirectAttributes.addFlashAttribute("errorMsg","헤드콘텐츠를 삭제 하였습니다.");
        return "redirect:/admin/headContent.do";
    }
}
