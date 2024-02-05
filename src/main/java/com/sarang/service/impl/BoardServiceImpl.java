package com.sarang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.sarang.mapper.BoardMapper;
import com.sarang.service.BoardService;

public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardMapper boardMapper;
    
}
