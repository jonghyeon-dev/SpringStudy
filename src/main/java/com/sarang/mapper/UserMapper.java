package com.sarang.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.UserVO;

@Mapper
public interface UserMapper {
    UserVO checkUserLogin(HashMap<String,Object> reqMap);
}
