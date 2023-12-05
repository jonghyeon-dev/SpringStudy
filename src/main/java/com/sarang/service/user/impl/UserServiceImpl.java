package com.sarang.service.user.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarang.mapper.user.UserMapper;
import com.sarang.model.user.EnoVO;
import com.sarang.service.user.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper; 

    @Override
    public HashMap<String,Object> getUserPageInfo(HashMap<String,Object> reqMap){
        return userMapper.getUserPageInfo(reqMap);
    }

    @Override
    public List<EnoVO> searchUserInfo(HashMap<String,Object> reqMap){
        return userMapper.searchUserInfo(reqMap);
    }
}
