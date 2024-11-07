package com.sarang.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarang.mapper.HeadContentMapper;
import com.sarang.model.HeadContentVO;
import com.sarang.service.impl.HeadContentServiceImpl;

@Service
public class HeadContentService implements HeadContentServiceImpl{
    @Autowired
    HeadContentMapper headContentMapper;

    @Override
    public List<HeadContentVO> getHeadContents(HashMap<String,Object> reqMap){

        return headContentMapper.getHeadContents(reqMap);
    }

    @Override
    public HashMap<String,Object> getHeadContentsPageInfo(HashMap<String,Object> reqMap){
         return headContentMapper.getHeadContentsPageInfo(reqMap);
    }
}
