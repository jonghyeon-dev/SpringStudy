package com.sarang.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.BoardVO;

@Mapper
public interface BoardMapper {
    List<BoardVO> getBoardList(HashMap<String,Object> reqMap);
    BoardVO getBoardDetail(BoardVO boardVO);
}
