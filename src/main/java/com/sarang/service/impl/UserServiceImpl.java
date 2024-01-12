package com.sarang.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarang.mapper.UserMapper;
import com.sarang.model.UserVO;
import com.sarang.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public UserVO checkUserLogin(HashMap<String,Object> reqMap){
        return userMapper.checkUserLogin(reqMap);
    }
}

