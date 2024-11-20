package com.sarang.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.UserVO;

@Mapper
public interface UserMapper {
    UserVO checkAdminLogin(HashMap<String,Object> reqMap);
    UserVO checkUserLogin(HashMap<String,Object> reqMap);
    
    List<UserVO> getUserInfo(HashMap<String,Object> reqMap);
    HashMap<String,Object> getUserPageInfo(HashMap<String,Object> reqMap);

    String checkUserDuplication(String userId);

    Integer insertUserInfo(UserVO userVO);
    Integer deleteUserInfo(List<String> deleteList);
    Integer updateUserInfo(UserVO userVO);

    UserVO checkUserPass(HashMap<String,Object> reqMap);
    Integer updateUserPass(HashMap<String,Object> reqMap);
}
