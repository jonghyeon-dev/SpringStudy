package com.sarang.mapper.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.user.EnoVO;

@Mapper
public interface UserMapper {
    List<EnoVO> getUserInfo();
    List<EnoVO> searchUserInfo(HashMap<String,Object> reqMap);
}
