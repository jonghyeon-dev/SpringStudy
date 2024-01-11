package com.sarang.mapper.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.user.EnoVO;

@Mapper
public interface UserMapper {
    HashMap<String,Object> getUserPageInfo(HashMap<String,Object> reqMap);
    List<EnoVO> searchUserInfo(HashMap<String,Object> reqMap);
    Integer insertUserInfo(EnoVO enoVO);
    Integer deleteUserInfo(List<String> deleteList);
}
