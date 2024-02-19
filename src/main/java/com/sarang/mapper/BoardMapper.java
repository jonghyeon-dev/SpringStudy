package com.sarang.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.BoardVO;
import com.sarang.model.FileVO;

@Mapper
public interface BoardMapper {
    List<BoardVO> getBoardList(HashMap<String,Object> reqMap);
    Integer getBoardPageInfo(HashMap<String,Object> reqMap);
    BoardVO getBoardDetailInfo(HashMap<String,Object> reqMap);
    List<FileVO> getBoardFileList(HashMap<String,Object> reqMap);

    Integer insertBoardDetailInfo(BoardVO boardVO);
    Integer updateBoardDetailInfo(BoardVO boardVO);
    Integer deleteBoardDetailInfo(BoardVO boardVO);
    Integer resetBoardFileList(BoardVO boardVO);
}