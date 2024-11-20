package com.sarang.service.impl;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.UserVO;

public interface UserServiceImpl{
    UserVO checkUserLogin(HashMap<String,Object> reqMap);
    UserVO findByUserId(String userId);
    UserVO checkAdminLogin(HashMap<String,Object> reqMap);
    List<UserVO> getUserInfo(HashMap<String,Object> reqMap);
    HashMap<String,Object> getUserPageInfo(HashMap<String,Object> reqMap);
    String checkUserDuplication(String userId);
    
    Integer insertUserInfo(UserVO userVO);
    Integer deleteUserInfo(List<String> deleteList);
    Integer updateUserInfo(UserVO userVO);

    UserVO checkUserPass(HashMap<String,Object> reqMap);
    Integer updateUserPass(HashMap<String,Object> reqMap);
}

