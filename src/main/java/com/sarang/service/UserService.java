package com.sarang.service;

import java.util.HashMap;

import com.sarang.model.UserVO;

public interface UserService{
    UserVO checkUserLogin(HashMap<String,Object> reqMap);
}

