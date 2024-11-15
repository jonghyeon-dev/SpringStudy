package com.sarang.service.impl;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.HeadContentVO;

public interface HeadContentServiceImpl {
    List<HeadContentVO> getHeadContents(HashMap<String,Object> reqMap);
    HashMap<String,Object> getHeadContentsPageInfo(HashMap<String,Object> reqMap);
    Integer insertHeadContent(HeadContentVO headContentVO);
    HeadContentVO getHeadContentDetail(HashMap<String,Object> reqMap);
    Integer updateHeadContent(HeadContentVO headContentVO);
    Integer deleteHeadContent(Integer contentSeq);
}
