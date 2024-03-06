package com.sarang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.sarang.service.BoardService;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BoardService boardService;

    @GetMapping(value={"/main.do","/"})
    public String mainPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
            logger.info("Main Page View");
            HashMap<String,Object> reqMap = new HashMap<>();
            reqMap.put("boardCate","notice");
            reqMap.put("size",5);
            reqMap.put("start",0);
            List<BoardVO> noticeBoardList = boardService.getBoardList(reqMap);

            reqMap = new HashMap<>();
            reqMap.put("boardCate","news");
            reqMap.put("size",3);
            reqMap.put("start",0);
            List<BoardVO> newsBoardList = boardService.getBoardList(reqMap);
            List<HashMap<String,Object>> newsList = new ArrayList<>();
            if(!newsBoardList.isEmpty()){
                  for(int i=0;i<newsBoardList.size();i++){
                        HashMap<String,Object> result = new HashMap<>();
                        String boardCntnt = newsBoardList.get(i).getBoardCntnt();
                        System.out.println("boardCntnt: "+boardCntnt);
                        String thumbPath = "";
                        if(boardCntnt.indexOf("img alt=&quot;&quot; src=&quot;") > -1){
                              thumbPath = boardCntnt.substring(boardCntnt.indexOf("img alt=&quot;&quot; src=&quot;")).replace("img alt=&quot;&quot; src=&quot;","");
                              thumbPath = thumbPath.substring(0,thumbPath.indexOf("&quot;")).replace("&quot;","");
                        }
                        result.put("thumbPath",thumbPath);
                        result.put("title",newsBoardList.get(i).getBoardTitle());
                        result.put("cretDate",newsBoardList.get(i).getCretDate());
                        result.put("boardId",newsBoardList.get(i).getBoardId());
                        newsList.add(result);
                  }
            }
            model.addAttribute("noticeBoardList", noticeBoardList);
            model.addAttribute("newsList", newsList);
            return "main/mainPage";
    }

    @GetMapping(value="/about.do")
    public String aboutPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
            logger.info("About Page View");
            return "main/aboutPage";
    }
}