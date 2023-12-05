package com.sarang.service.user;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.user.EnoVO;

public interface UserService{
    HashMap<String,Object> getUserPageInfo(HashMap<String,Object> reqMap);
    List<EnoVO> searchUserInfo(HashMap<String,Object> reqMap);
}
