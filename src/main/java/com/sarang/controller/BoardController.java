package com.sarang.controller;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
import com.sarang.config.SecureUtil;
import com.sarang.model.BoardComntVO;
import com.sarang.model.BoardRecomVO;
import com.sarang.model.BoardVO;
import com.sarang.model.BoardViewVO;
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

	@Autowired
	private SecureUtil secureUtil;
    
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

	@GetMapping(value="/board/{category}/detail/{boardId}")
	public String boardDetailPage(HttpSession session, HttpServletRequest request
	, HttpServletResponse response , Model model, @PathVariable("category") String category
	, @PathVariable("boardId") Integer boardId) throws Exception{
		logger.info("boardDetailPage View");
		UserVO userVO = (UserVO)session.getAttribute("userLogin");

		HashMap<String,Object> reqMap = new HashMap<>();
		reqMap.put("boardId",boardId);
		reqMap.put("boardCate",category);
		BoardVO boardVO = boardService.getBoardDetailInfo(reqMap);
		List<FileVO> fileList = boardService.getBoardFileList(reqMap);
		String boardCntnt = boardVO.getBoardCntnt();
		boardVO.setBoardCntnt(StringEscapeUtils.unescapeHtml4(boardCntnt));

		BoardViewVO viewVO = new BoardViewVO();
		viewVO.setBoardId(boardId);
		if(ObjectUtils.isEmpty(userVO)){
			viewVO.setCretUser("guest:"+secureUtil.getClientIP(request));
		}else{
			viewVO.setCretUser(userVO.getSeq().toString());
		}
		boardService.insertBoardViewInfo(viewVO);
		
		if(!ObjectUtils.isEmpty(userVO)){
			BoardRecomVO recomVO = new BoardRecomVO();
			recomVO.setBoardId(boardId);
			recomVO.setCretUser(userVO.getSeq().toString());
			BoardRecomVO retRecomVO = boardService.checkDupRecom(recomVO);
			model.addAttribute("retRecomInfo", retRecomVO);
		}

		Integer recomCnt = boardService.getBoardRecomCount(reqMap);

		List<BoardComntVO> comntList = boardService.getBoardComntList(reqMap);

		model.addAttribute("boardInfo", boardVO);
		model.addAttribute("boardFileList", fileList);
		model.addAttribute("recomCnt", recomCnt);
		model.addAttribute("comntList",comntList);

		return "board/boardDetailPage";
	}

	@GetMapping(value="/board/{category}/boardWrite")
	public String boardInsertPage(HttpSession session, HttpServletRequest request
	, HttpServletResponse response , Model model, @PathVariable("category") String category) throws Exception{
		logger.info("boardWritePage View");
		UserVO userVO = (UserVO) session.getAttribute("userLogin");
		if(checkBoardLogin(userVO,category)){
			return "redirect:/board/"+category;
		};
		model.addAttribute("middle", category);
		model.addAttribute("category", category);
		model.addAttribute("status","insert");
		return "board/boardWritePage";
	}

	@PostMapping(value="/board/{category}/boardInsert")
	public String insertBoardInfo(HttpSession session, MultipartHttpServletRequest request, HttpServletResponse response
	, List<MultipartFile> uploadFiles, @PathVariable("category") String category) throws Exception {
		UserVO userVO = (UserVO) session.getAttribute("userLogin");
		if(checkBoardLogin(userVO,category)){
			return "redirect:/board/"+category;
		};
		String boardTitle = request.getParameter("boardTitle");
		String boardCntnt = request.getParameter("boardCntnt");
		String boardCate = category;
		String cretUser ="";
		cretUser = userVO.getSeq().toString();
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
		UserVO userVO = (UserVO) session.getAttribute("userLogin");
		if(checkBoardLogin(userVO,category)){
			return "redirect:/board/"+category;
		};
		HashMap<String,Object> reqMap = new HashMap<>();
		reqMap.put("boardId",boardId);
		reqMap.put("boardCate",category);
		if(userVO.getUserGrant() != "0"){
			reqMap.put("cretUser",userVO.getSeq());
		}

		BoardVO boardVO = boardService.getBoardDetailInfo(reqMap);
		// 게시물 존재 여부 확인
		if(ObjectUtils.isEmpty(boardVO)){
			return "redirect:/board/"+category;
		}
		// 작성자 여부 확인
		String modifyUser = "";
		modifyUser = userVO.getSeq().toString();
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

	@PostMapping(value="/board/{category}/boardUpdate/{boardId}")
	public String modifyBoardInfo(HttpSession session, MultipartHttpServletRequest request
    , HttpServletResponse response, List<MultipartFile> uploadFiles, @PathVariable("category") String category
	, @PathVariable("boardId") String boardId) throws Exception {
		UserVO userVO = (UserVO) session.getAttribute("userLogin");
		if(checkBoardLogin(userVO,category)){
			return "redirect:/board"+category;
		};
		String boardTitle = request.getParameter("boardTitle");
		String boardCntnt = request.getParameter("boardCntnt");
		String[] fileIds = request.getParameterValues("fileIds");
		String boardCate = category;
		System.out.println("fileIds Length:"+fileIds.length);
		if(fileIds.length>0){
			for(int i=0;fileIds.length>i;i++){
				System.out.println("fileId:"+fileIds[i]);
			}
		}
		HashMap<String,Object> reqMap = new HashMap<>();
		reqMap.put("boardId",boardId);
		reqMap.put("boardCate",boardCate);

		BoardVO boardVO = boardService.getBoardDetailInfo(reqMap);
		// 게시물 존재 여부 확인
		if(ObjectUtils.isEmpty(boardVO)){
			return "redirect:/board/"+category;
		}
		String chgUser = "";
		chgUser = userVO.getSeq().toString();
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
				if(fileIds.length>0){
					HashMap<String,Object> fileReqMap = new HashMap<>();
					fileReqMap.put("fileIds",fileIds);
					fileReqMap.put("boardId",boardId);
					boardService.setBasicFileList(fileReqMap);
				}
				fileUtils.MultiFileUpload(session, Integer.parseInt(boardId), uploadFiles);
			}catch(Exception e){
				logger.error("Board File Upload Error : {}",e.getMessage());
				return "redirect:/error";
			}
		}
		return "redirect:/board/"+category+"/detail/"+boardId;
	}

	@ResponseBody
	@RequestMapping(value="/board/{category}/recom",method=RequestMethod.POST)
	public ResponseData putBoardRecom(HttpSession session, HttpServletRequest request, HttpServletResponse response 
	,  @PathVariable("category") String category, String boardId, String likeChu)throws Exception{
		ResponseData js = new ResponseData();
		UserVO userVO = (UserVO)session.getAttribute("userLogin");
		if(checkBoardLogin(userVO,category)){
			js.setIsSucceed(false);
			js.setMessage("로그인을 해야 가능한 기능입니다.");
			return js;
		}
		try{
			BoardRecomVO recomVO = new BoardRecomVO();
			recomVO.setBoardId(Integer.parseInt(boardId));
			recomVO.setLikeChu(Integer.parseInt(likeChu));
			recomVO.setCretUser(userVO.getSeq().toString());
			BoardRecomVO retRecomVO = boardService.checkDupRecom(recomVO);
			if(ObjectUtils.isEmpty(retRecomVO)){
				boardService.insertBoardRecomInfo(recomVO);
			}else{
				boardService.updateBoardRecomInfo(recomVO);
			}
			HashMap<String,Object> reqMap = new HashMap<>();
			reqMap.put("boardId",boardId);
			Integer recomCnt = boardService.getBoardRecomCount(reqMap);

			HashMap<String,Object> retMap = new HashMap<>();
			retMap.put("recomCnt",recomCnt);
			js.setIsSucceed(true);
			js.setData(retMap);
		}catch(Exception e){
			logger.error("Error Is : {}",e.getMessage());
			js.setIsSucceed(false);
			js.setMessage("추천 등록 중 오류가 발생 하였습니다.");
		}
		return js;
	}

	@PostMapping(value="/board/{category}/boardComnt")
	public String insertBoardComment(HttpSession session, HttpServletRequest request, HttpServletResponse response 
	,  @PathVariable("category") String category, Integer boardId, String boardComnt, Integer parntId
	, Integer originId, Integer groupStep, Integer groupLayer)throws Exception{
		logger.info("POST insertBoardComment");
		BoardComntVO comntVO = new BoardComntVO();
		try{
			UserVO loginVO = (UserVO)session.getAttribute("userLogin");
			comntVO.setBoardId(boardId);
			if(parntId != null && parntId != 0){
				comntVO.setParntId(parntId);
			}
			if(originId != null && originId != 0){
				comntVO.setOriginId(originId);
				comntVO.setGroupStep(groupStep);
				comntVO.setGroupLayer(groupLayer);
			}
			comntVO.setBoardComnt(boardComnt);
			comntVO.setCretUser(loginVO.getSeq().toString());
			comntVO.setChgUser(loginVO.getSeq().toString());
			boardService.insertBoardComment(comntVO);
		}catch(Exception e){
			logger.error("Error Is : {}",e.getMessage());
		}
		return "redirect:/board/"+category+"/detail/"+boardId;
	}

	@ResponseBody
	@RequestMapping(value="/board/{category}/deleteComnt",method=RequestMethod.DELETE)
	public ResponseData deleteComntInfo (HttpSession session, HttpServletRequest request, HttpServletResponse response 
	,  @PathVariable("category") String category, Integer boardId, Integer comntId)throws Exception{
		ResponseData js = new ResponseData();
		UserVO userVO = (UserVO)session.getAttribute("userLogin");
		if(checkBoardLogin(userVO,category)){
			js.setIsSucceed(false);
			js.setMessage("로그인을 해야 가능한 기능입니다.");
			return js;
		}
		try{
			BoardComntVO comntVO = new BoardComntVO();
			comntVO.setBoardId(boardId);
			comntVO.setComntId(comntId);
			if(!"0".equals(userVO.getUserGrant())){
				comntVO.setCretUser(userVO.getSeq().toString());
			}
			boardService.deleteBoardComment(comntVO);
			js.setIsSucceed(true);
			js.setMessage("삭제 성공");
		}catch(Exception e){
			logger.error("Error Is : {}",e.getMessage());
			js.setIsSucceed(false);
			js.setMessage("Delete Comment Error");
		}

		return js;
	}

	@PostMapping(value="/board/{category}/boardModifyComnt")
	public String modifyBoardComnt(HttpSession session, HttpServletRequest request, HttpServletResponse response 
	,  @PathVariable("category") String category, Integer boardId, Integer comntId, String boardComnt)throws Exception{
		UserVO userVO = (UserVO)session.getAttribute("userLogin");
		try{
			BoardComntVO comntVO = new BoardComntVO();
			comntVO.setBoardId(boardId);
			comntVO.setComntId(comntId);
			comntVO.setBoardComnt(boardComnt);
			comntVO.setCretUser(userVO.getSeq().toString());
			comntVO.setChgUser(userVO.getSeq().toString());
			boardService.updateBoardComment(comntVO);
		}catch(Exception e){
			logger.error("Error Is : {}",e.getMessage());
		}
		return "redirect:/board/"+category+"/detail/"+boardId;
	}

	/**
	 * 게시판 등록, 수정 시 사용자 권한 체크
	 * @param loginVO
	 * @param adminVO
	 * @param category
	 * @return boolean
	 */
	private boolean checkBoardLogin(UserVO userVO, String category){
		// 자유게시판 사용자 체크
		if(ObjectUtils.isEmpty(userVO)){ 
			return true;
		}
		// 그 외 게시판 관리자 체크
		System.out.println(category);
		System.out.println(userVO.getUserGrant());
		if(!"community".equals(category) && !"0".equals(userVO.getUserGrant())){
			return true;
		}
		return false;
	}

	// 에디터 업로드 시 2중 escape된 태그 삭제 처리
	// 프록시 툴을 사용해서 입력 시 어떻게 처리해야 하는지? ckeditor를 쓰지 말아야하나?
	private String escapeQuoatHtmlTag(String args){
		return args.replaceAll("&amp;lt;","").replaceAll("&amp;gt;","")
		.replaceAll("&amp;quot;","").replaceAll("&amp;#034;","")
		.replaceAll("&amp;#039;","").replaceAll("&amp;#39;","");
	}
}