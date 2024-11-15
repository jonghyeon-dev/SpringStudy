package com.sarang.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sarang.mapper.HeadContentMapper;
import com.sarang.model.HeadContentVO;
import com.sarang.service.impl.HeadContentServiceImpl;

@Transactional
@Service
public class HeadContentService implements HeadContentServiceImpl{
    @Autowired
    HeadContentMapper headContentMapper;

    @Override
    public List<HeadContentVO> getMainHeadContents(HashMap<String,Object> reqMap){
        return headContentMapper.getMainHeadContents(reqMap);
    }

    @Override
    public List<HeadContentVO> getHeadContents(HashMap<String,Object> reqMap){

        return headContentMapper.getHeadContents(reqMap);
    }

    @Override
    public HashMap<String,Object> getHeadContentsPageInfo(HashMap<String,Object> reqMap){
         return headContentMapper.getHeadContentsPageInfo(reqMap);
    }

    @Override
    public Integer insertHeadContent(HeadContentVO headContentVO){
        return headContentMapper.insertHeadContent(headContentVO);
    }

    @Override
    public HeadContentVO getHeadContentDetail(HashMap<String,Object> reqMap){
        return headContentMapper.getHeadContentDetail(reqMap);
    }

    @Override
    public Integer updateHeadContent(HeadContentVO headContentVO){
        return headContentMapper.updateHeadContent(headContentVO);
    }

    @Override
    public Integer deleteHeadContent(Integer contentSeq){
        return headContentMapper.deleteHeadContent(contentSeq);
    }
}
