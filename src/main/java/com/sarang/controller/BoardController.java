package com.sarang.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sarang.config.FileUtils;
import com.sarang.model.AdminVO;
import com.sarang.model.BoardVO;
import com.sarang.model.UserVO;
import com.sarang.model.common.FileVO;
import com.sarang.model.common.PageVO;
import com.sarang.model.common.ResponseData;
import com.sarang.service.BoardService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private static final int pageSize = 10;

    @Autowired
    private BoardService boardService;

	@Autowired
	private FileUtils fileUtils;
    
    @GetMapping(value="/board/{category}")
    public String boardMainPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model, @PathVariable("category") String category) throws Exception{
        logger.info("boardPage View");
		String searchOption = request.getParameter("searchOption");
		String searchWord = request.getParameter("searchWord");

        if(category.isEmpty() || category == null){
            category = "community";
        }

        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("start",0);
        reqMap.put("size",pageSize);
        reqMap.put("boardCate",category);
		if(searchOption != null && searchOption.trim() != "" 
		   && searchWord != null && searchWord.trim() !=""){
			reqMap.put("searchOption",searchOption);
			reqMap.put("searchWord",searchWord);
		}

        List<BoardVO> boardList = boardService.getBoardList(reqMap);
        Integer totalPage = boardService.getBoardPageInfo(reqMap);

		PageVO pageVO = new PageVO(totalPage,1);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("boardList", boardList);
		totalContents.put("pageInfo", pageVO);

        model.addAttribute("totalContents", totalContents);
		model.addAttribute("category",category);

        return "board/boardPage";
    }

	@GetMapping(value="/board/{category}/{page}")
    public String boardNumPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model, @PathVariable("category") String category, @PathVariable("page") int page) throws Exception{
        logger.info("boardPage View");
		String searchOption = request.getParameter("searchOption");
		String searchWord = request.getParameter("searchWord");

        if(category.isEmpty() || category == null){
            category = "community";
        }

        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("start",(page < 0 ? 0 : (page-1))*10);
        reqMap.put("size",pageSize);
        reqMap.put("boardCate",category);
		if(searchOption != null && searchOption.trim() != "" 
		   && searchWord != null && searchWord.trim() !=""){
			reqMap.put("searchOption",searchOption);
			reqMap.put("searchWord",searchWord);
		}

        List<BoardVO> boardList = boardService.getBoardList(reqMap);
        Integer totalPage = boardService.getBoardPageInfo(reqMap);
		PageVO pageVO = new PageVO(totalPage,page);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("boardList", boardList);
		totalContents.put("pageInfo", pageVO);

        model.addAttribute("totalContents", totalContents);
		model.addAttribute("category",category);
		model.addAttribute("page",page);

        return "board/boardPage";
    }

	@ResponseBody
	@RequestMapping(value="/board/{category}/getBoardList", method=RequestMethod.GET)
	public ResponseData getMethodName(HttpSession session, HttpServletRequest request, HttpServletResponse response 
	,  @PathVariable("category") String category, String page, String searchOption, String searchWord) throws Exception{
		ResponseData js = new ResponseData();
		if(category.isEmpty() || category == null){
            category = "community";
        }

        HashMap<String,Object> reqMap = new HashMap<>();
        if(page != null){
            reqMap.put("start",Integer.parseInt(page)*10);
        }else{
            reqMap.put("start",0);
        }
        reqMap.put("size",pageSize);
        reqMap.put("boardCate",category);
		if((searchOption != null && searchOption.trim() != "")
		   && (searchWord != null && searchWord.trim() !="")){
			reqMap.put("searchOption",searchOption);
			reqMap.put("searchWord",searchWord);
		}

        List<BoardVO> boardList = boardService.getBoardList(reqMap);
        Integer totalPage = boardService.getBoardPageInfo(reqMap);

		PageVO pageVO = new PageVO(totalPage,Integer.parseInt(page));

		HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("boardList", boardList);
		totalContents.put("pageInfo", pageVO);
		js.setIsSucceed(true);
		js.setData(totalContents);
		return js;
	}
	

	@GetMapping(value="/board/{category}/boardWrite")
	public String boardInsertPage(HttpSession session, HttpServletRequest request
	, HttpServletResponse response , Model model, @PathVariable("category") String category) throws Exception{
		logger.info("boardWritePage View");
		UserVO loginVO = (UserVO) session.getAttribute("userLogin");
		AdminVO adminVO = (AdminVO) session.getAttribute("adminLogin");
		if(checkBoardLogin(loginVO,adminVO,category)){
			return "redirect:/board"+category;
		};
		model.addAttribute("middle", category);
		model.addAttribute("category", category);
		model.addAttribute("status","insert");
		return "board/boardWritePage";
	}

	@GetMapping(value="/boardDetail/{category}/{boardId}")
	public String boardDetailPage(HttpSession session, HttpServletRequest request
	, HttpServletResponse response , Model model, @PathVariable("category") String category
	, @PathVariable("boardId") Integer boardId) throws Exception{
		logger.info("boardDetailPage View");
		
		HashMap<String,Object> reqMap = new HashMap<>();
		reqMap.put("boardId",boardId);

		BoardVO boardVO = boardService.getBoardDetailInfo(reqMap);
		List<FileVO> fileList = boardService.getBoardFileList(reqMap);
		String boardCntnt = boardVO.getBoardCntnt();
		boardVO.setBoardCntnt(StringEscapeUtils.unescapeHtml4(boardCntnt));

		model.addAttribute("boardInfo", boardVO);
		model.addAttribute("boardFileList", fileList);
		return "board/boardDetailPage";
	}

	@RequestMapping(value="/board/{category}/insertBoard", method=RequestMethod.POST)
	public String insertBoardInfo(HttpSession session, MultipartHttpServletRequest request, HttpServletResponse response
	, List<MultipartFile> uploadFiles, @PathVariable("category") String category) throws Exception {
		UserVO loginVO = (UserVO) session.getAttribute("userLogin");
		AdminVO adminVO = (AdminVO) session.getAttribute("adminLogin");
		if(checkBoardLogin(loginVO,adminVO,category)){
			return "redirect:/board"+category;
		};
		String boardTitle = request.getParameter("boardTitle");
		String boardCntnt = request.getParameter("boardCntnt");
		String boardCate = category;
		String cretUser ="";
		if(ObjectUtils.isEmpty(loginVO)){
			cretUser = adminVO.getEno();
		}else{
			cretUser = loginVO.getUserId();
		}
		BoardVO boardVO = new BoardVO();
		boardVO.setBoardCate(boardCate);
		boardVO.setBoardTitle(boardTitle);
		boardVO.setBoardCntnt(escapeQuoatHtmlTag(boardCntnt));
		boardVO.setCretUser(cretUser);
		boardVO.setChgUser(cretUser);

		boardService.insertBoardDetailInfo(boardVO);
		Integer boardId = boardVO.getBoardId();
		if(!CollectionUtils.isEmpty(uploadFiles)){
			try{
				fileUtils.MultiFileUpload(session, boardId, uploadFiles);
			}catch(Exception e){
				logger.error("Board File Upload Error : {}",e.getMessage());
				return "redirect:/error";
			}
		}
		return "redirect:/board/"+category;
	}
	
	@GetMapping(value="/board/{category}/boardModify/{boardId}")
	public String boardModifyPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model, @PathVariable("category") String category
	, @PathVariable("boardId") String boardId) throws Exception{
		// 로그인 체크
		UserVO loginVO = (UserVO) session.getAttribute("userLogin");
		AdminVO adminVO = (AdminVO) session.getAttribute("adminLogin");
		if(checkBoardLogin(loginVO,adminVO,category)){
			return "redirect:/board/"+category;
		};
		HashMap<String,Object> reqMap = new HashMap<>();
		reqMap.put("boardId",boardId);
		reqMap.put("boardCate",category);

		BoardVO boardVO = boardService.getBoardDetailInfo(reqMap);
		// 게시물 존재 여부 확인
		if(ObjectUtils.isEmpty(boardVO)){
			return "redirect:/board/"+category;
		}
		// 작성자 여부 확인
		String modifyUser = "";
		if(ObjectUtils.isEmpty(loginVO)){
			modifyUser = adminVO.getEno();
		}else{
			modifyUser = loginVO.getUserId();
		}
		if(!boardVO.getCretUser().equals(modifyUser)){
			return "redirect:/board/"+category;
		}
		String boardCntnt = boardVO.getBoardCntnt();
		boardVO.setBoardCntnt(StringEscapeUtils.unescapeHtml4(boardCntnt));
		List<FileVO> boardFileList = boardService.getBoardFileList(reqMap);
		String middle = category;
		model.addAttribute("boardInfo", boardVO);
		model.addAttribute("boardFileList",boardFileList);
		if(!"community".equals(category)){
			middle = "/admin/" + category;
		}
		model.addAttribute("middle", middle);
		model.addAttribute("status","update");
		return "board/boardWritePage";
	}

	@PostMapping(value="/board/{category}/modifyBoard/{boardId}")
	public String modifyBoardInfo(HttpSession session, MultipartHttpServletRequest request
    , HttpServletResponse response, List<MultipartFile> uploadFiles, @PathVariable("category") String category
	, @PathVariable("boardId") String boardId) throws Exception {
		UserVO loginVO = (UserVO) session.getAttribute("userLogin");
		AdminVO adminVO = (AdminVO) session.getAttribute("adminLogin");
		if(checkBoardLogin(loginVO,adminVO,category)){
			return "redirect:/board"+category;
		};
		String boardTitle = request.getParameter("boardTitle");
		String boardCntnt = request.getParameter("boardCntnt");
		String boardCate = category;

		HashMap<String,Object> reqMap = new HashMap<>();
		reqMap.put("boardId",boardId);
		reqMap.put("boardCate",boardCate);

		BoardVO boardVO = boardService.getBoardDetailInfo(reqMap);
		// 게시물 존재 여부 확인
		if(ObjectUtils.isEmpty(boardVO)){
			return "redirect:/board/"+category;
		}
		String chgUser = "";
		if(ObjectUtils.isEmpty(loginVO)){
			chgUser = adminVO.getEno();
		}else{
			chgUser = loginVO.getUserId();
		}
		// 작성자 여부 확인
		if(!boardVO.getCretUser().equals(chgUser)){
			return "redirect:/board/"+category;
		}
		boardVO.setBoardCate(boardCate);
		boardVO.setBoardTitle(boardTitle);
		boardVO.setBoardCntnt(escapeQuoatHtmlTag(boardCntnt));
		boardVO.setChgUser(chgUser);
		
		boardService.updateBoardDetailInfo(boardVO);
		if(!CollectionUtils.isEmpty(uploadFiles)){
			try{
				boardService.resetBoardFileList(boardVO);
				fileUtils.MultiFileUpload(session, Integer.parseInt(boardId), uploadFiles);
			}catch(Exception e){
				logger.error("Board File Upload Error : {}",e.getMessage());
				return "redirect:/error";
			}
		}
		return "redirect:/boardDetail/"+category+"/"+boardId;
	}

	/**
	 * 게시판 등록, 수정 시 사용자 권한 체크
	 * @param loginVO
	 * @param adminVO
	 * @param category
	 * @return boolean
	 */
	private boolean checkBoardLogin(UserVO loginVO, AdminVO adminVO, String category){
		// 자유게시판 사용자 체크
		if(ObjectUtils.isEmpty(loginVO) && ObjectUtils.isEmpty(adminVO)){ 
			return true;
		}
		// 그 외 게시판 관리자 체크
		if(!"community".equals(category) && ObjectUtils.isEmpty(adminVO)){
			return true;
		}
		return false;
	}

	// 에디터 업로드 시 2중 escape된 태그 삭제 처리
	// 프록시 툴을 사용해서 입력 시 어떻게 처리해야 하는지?
	private String escapeQuoatHtmlTag(String args){
		return args.replaceAll("&amp;lt;","").replaceAll("&amp;gt;","")
		.replaceAll("&amp;quot;","").replaceAll("&amp;#034;","")
		.replaceAll("&amp;#039;","").replaceAll("&amp;#39;","");
	}
}