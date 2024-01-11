package com.sarang.service;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.EnoVO;

public interface UserService{
    HashMap<String,Object> getUserPageInfo(HashMap<String,Object> reqMap);
    List<EnoVO> searchUserInfo(HashMap<String,Object> reqMap);
    Integer insertUserInfo(EnoVO enoVO);
    Integer deleteUserInfo(List<String> deleteList);
}
