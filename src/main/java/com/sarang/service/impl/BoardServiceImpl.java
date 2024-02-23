package com.sarang.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarang.mapper.BoardMapper;
import com.sarang.model.BoardVO;
import com.sarang.model.common.FileVO;
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
    public Integer getBoardPageInfo(HashMap<String,Object> reqMap){
        return boardMapper.getBoardPageInfo(reqMap);
    }

    @Override
    public BoardVO getBoardDetailInfo(HashMap<String,Object> reqMap){
        return boardMapper.getBoardDetailInfo(reqMap);
    }

    @Override
    public List<FileVO> getBoardFileList(HashMap<String,Object> reqMap){
        return boardMapper.getBoardFileList(reqMap);
    }

    @Override
    public Integer insertBoardDetailInfo(BoardVO boardVO){
        return boardMapper.insertBoardDetailInfo(boardVO);

    }

    @Override
    public Integer updateBoardDetailInfo(BoardVO boardVO){
        return boardMapper.updateBoardDetailInfo(boardVO);

    }

    @Override
    public Integer deleteBoardDetailInfo(BoardVO boardVO){
        return boardMapper.deleteBoardDetailInfo(boardVO);

    }

    @Override
    public Integer resetBoardFileList(BoardVO boardVO){
        return boardMapper.resetBoardFileList(boardVO);

    }
}
