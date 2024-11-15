package com.sarang.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.HeadContentVO;

@Mapper
public interface HeadContentMapper {
    List<HeadContentVO> getHeadContents(HashMap<String,Object> reqMap);
    HashMap<String,Object> getHeadContentsPageInfo(HashMap<String,Object> reqMap);
    Integer insertHeadContent(HeadContentVO headContentVO);
    HeadContentVO getHeadContentDetail(HashMap<String,Object> reqMap);
    Integer updateHeadContent(HeadContentVO headContentVO);
    Integer deleteHeadContent(Integer contentSeq);
}
