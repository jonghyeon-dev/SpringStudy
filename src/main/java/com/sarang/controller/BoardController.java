package com.sarang.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sarang.model.BoardVO;
import com.sarang.service.BoardService;

@Controller
public class BoardController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

    private static final int pageSize = 10;

    @Autowired
    private BoardService boardService;
    // MultipartHttpServletRequest
    @GetMapping(value="/board/{category}")
    public String adminLoginPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model, @PathVariable("category") String category) throws Exception{
        LOGGER.info("boardPage View");

        if(category.isEmpty() || category == null){
            category = "community";
        }

        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("start",0);
        reqMap.put("size",pageSize);
        reqMap.put("boardCate",category);
        List<BoardVO> boardList = boardService.getBoardList(reqMap);
        HashMap<String,Object> pageInfo = boardService.getBoardPageInfo(reqMap);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("boardList", boardList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));
        model.addAttribute("totalContents", totalContents);

        return "board/boardPage";
    }
}
