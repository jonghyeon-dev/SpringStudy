package com.sarang.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarang.mapper.BoardMapper;
import com.sarang.model.BoardVO;
import com.sarang.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService{
    @Autowired
    BoardMapper boardMapper;

    @Override
    public List<BoardVO> getBoardList(HashMap<String,Object> reqMap){
        return boardMapper.getBoardList(reqMap);
    }

    @Override
    public HashMap<String,Object> getBoardPageInfo(HashMap<String,Object> reqMap){
        return boardMapper.getBoardPageInfo(reqMap);
    }
}
