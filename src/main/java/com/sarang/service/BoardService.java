package com.sarang.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarang.mapper.BoardMapper;
import com.sarang.model.BoardComntVO;
import com.sarang.model.BoardRecomVO;
import com.sarang.model.BoardVO;
import com.sarang.model.BoardViewVO;
import com.sarang.model.common.FileVO;
import com.sarang.service.impl.BoardServiceImpl;

@Service
public class BoardService implements BoardServiceImpl{
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
    public Integer getBoardRecomCount(HashMap<String,Object> reqMap){
        return boardMapper.getBoardRecomCount(reqMap);
    }

    @Override
    public BoardRecomVO checkDupRecom(BoardRecomVO recomVO){
        return boardMapper.checkDupRecom(recomVO);
    }

    @Override
    public List<BoardComntVO> getBoardComntList(HashMap<String,Object> reqMap){
        return boardMapper.getBoardComntList(reqMap);
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

    @Override
    public Integer insertBoardViewInfo(BoardViewVO viewVO){
        return boardMapper.insertBoardViewInfo(viewVO);
    }

    @Override
    public Integer insertBoardRecomInfo(BoardRecomVO recomVO){
        return boardMapper.insertBoardRecomInfo(recomVO);
    }

    @Override
    public Integer updateBoardRecomInfo(BoardRecomVO recomVO){
        return boardMapper.updateBoardRecomInfo(recomVO);
    }

    @Override
    public Integer insertBoardComment(BoardComntVO comntVO){
        if(comntVO.getOriginId() != null && comntVO.getOriginId() != 0){
            Integer groupStep = boardMapper.getComntGroupLayerStep(comntVO);
            if(groupStep != 0){
                comntVO.setGroupStep(groupStep);
            }
            boardMapper.updateBoardComntGroupStep(comntVO);
        }
        return boardMapper.insertBoardComment(comntVO);
    }

    @Override
    public Integer updateBoardComment(BoardComntVO comntVO){
        return boardMapper.updateBoardComment(comntVO);
    }

    @Override
    public Integer deleteBoardComment(BoardComntVO comntVO){
        return boardMapper.deleteBoardComment(comntVO);
    }
}
