package com.sarang.service.impl;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.HeadContentVO;

public interface HeadContentServiceImpl {
    List<HeadContentVO> getHeadContents(HashMap<String,Object> reqMap);
    HashMap<String,Object> getHeadContentsPageInfo(HashMap<String,Object> reqMap);
}
