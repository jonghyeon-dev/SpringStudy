package com.sarang.service;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.BoardVO;

public interface BoardService {
    List<BoardVO> getBoardList(HashMap<String,Object> reqMap);
    HashMap<String,Object> getBoardPageInfo(HashMap<String,Object> reqMap);
}
