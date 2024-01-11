package com.sarang.controller.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping(value="/main.do")
    public String mainPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
          LOGGER.info("Main Page View");
          return "main/mainPage";
    }

    @GetMapping(value="/about.do")
    public String aboutPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model) throws Exception {
          LOGGER.info("About Page View");
          return "main/aboutPage";
    }
}