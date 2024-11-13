package com.sarang.service.impl;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.BoardComntVO;
import com.sarang.model.BoardRecomVO;
import com.sarang.model.BoardVO;
import com.sarang.model.BoardViewVO;
import com.sarang.model.common.FileVO;


public interface BoardServiceImpl {
    List<BoardVO> getBoardList(HashMap<String,Object> reqMap);
    Integer getBoardPageInfo(HashMap<String,Object> reqMap);
    BoardVO getBoardDetailInfo(HashMap<String,Object> reqMap);
    List<FileVO> getBoardFileList(HashMap<String,Object> reqMap);
    Integer getBoardRecomCount(HashMap<String,Object> reqMap);
    BoardRecomVO checkDupRecom(BoardRecomVO recomVO);
    List<BoardComntVO> getBoardComntList(HashMap<String,Object> reqMap);

    Integer insertBoardDetailInfo(BoardVO boardVO);
    Integer updateBoardDetailInfo(BoardVO boardVO);
    Integer deleteBoardDetailInfo(BoardVO boardVO);

    Integer resetBoardFileList(BoardVO boardVO);
    Integer setBasicFileList(HashMap<String,Object> reqMap);

    Integer insertBoardViewInfo(BoardViewVO viewVO);
    
    Integer insertBoardRecomInfo(BoardRecomVO recomVO);
    Integer updateBoardRecomInfo(BoardRecomVO recomVO);

    Integer insertBoardComment(BoardComntVO comntVO);
    Integer updateBoardComment(BoardComntVO comntVO);
    Integer deleteBoardComment(BoardComntVO comntVO);
}
