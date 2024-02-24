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

import java.util.HashMap;
import java.util.List;

import com.sarang.model.BoardVO;
import com.sarang.service.BoardService;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BoardService boardService;

    @GetMapping(value="/main.do")
    public String mainPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
          logger.info("Main Page View");
          HashMap<String,Object> reqMap = new HashMap<>();
          reqMap.put("boardCate","notice");
          reqMap.put("size",5);
          reqMap.put("start",0);
          List<BoardVO> noticeBoardList = boardService.getBoardList(reqMap);
          model.addAttribute("noticeBoardList", noticeBoardList);
          return "main/mainPage";
    }

    @GetMapping(value="/about.do")
    public String aboutPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
          logger.info("About Page View");
          return "main/aboutPage";
    }
}