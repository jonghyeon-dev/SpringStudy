package com.sarang.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sarang.model.BoardVO;
import com.sarang.model.HeadContentVO;
import com.sarang.model.common.FileVO;
import com.sarang.service.BoardService;
import com.sarang.service.HeadContentService;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BoardService boardService;

    @Autowired
    private HeadContentService headContentService;

    @GetMapping(value={"/main.do","/"})
    public String mainPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
            logger.info("Main Page View");
            //공지사항
            HashMap<String,Object> reqMap = new HashMap<>();
            reqMap.put("boardCate","notice");
            reqMap.put("size",5);
            reqMap.put("start",0);
            List<BoardVO> noticeBoardList = boardService.getBoardList(reqMap);

            //최신소식
            reqMap = new HashMap<>();
            reqMap.put("boardCate","news");
            reqMap.put("size",3);
            reqMap.put("start",0);
            List<BoardVO> newsBoardList = boardService.getBoardList(reqMap);
            List<HashMap<String,Object>> newsList = new ArrayList<>();
            if(!newsBoardList.isEmpty()){
                  for(int i=0;i<newsBoardList.size();i++){
                        HashMap<String,Object> result = new HashMap<>();
                        Integer boardId = newsBoardList.get(i).getBoardId();
                        String thumbPath = "";
                        HashMap<String,Object> reqFileMap = new HashMap<>();
                        reqFileMap.put("boardId",boardId);
                        List<FileVO> fileList = boardService.getBoardFileList(reqFileMap);
                        for(FileVO file : fileList){
                              if("jpg".equals(file.getFileExt().toLowerCase()) 
                                  || "jpeg".equals(file.getFileExt().toLowerCase()) 
                                  || "png".equals(file.getFileExt().toLowerCase())){
                                    thumbPath = "/image/display/" + file.getFileId();
                              }
                        }
                        result.put("thumbPath",thumbPath);
                        result.put("title",newsBoardList.get(i).getBoardTitle());
                        result.put("cretDate",newsBoardList.get(i).getCretDate());
                        result.put("boardId",newsBoardList.get(i).getBoardId());
                        result.put("asNew",newsBoardList.get(i).getAsNew());
                        newsList.add(result);
                  }
            }

            //헤드콘텐츠
            reqMap = new HashMap<>();
            List<HeadContentVO> headContentsList = headContentService.getMainHeadContents(reqMap);


            model.addAttribute("noticeBoardList", noticeBoardList);
            model.addAttribute("newsList", newsList);
            model.addAttribute("headContentsList",headContentsList);
            return "main/mainPage";
    }

    @GetMapping(value="/about.do")
    public String aboutPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
            logger.info("About Page View");
            return "main/aboutPage";
    }

    @GetMapping(value="/thymeleafTest")
    public String hellothymeleaf(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception{
      logger.info("thymeleaf Test Start");
      
      model.addAttribute("name", "thymeleaf Study");
      return "thymeleaf/hello";
    }
} 