package com.sarang.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarang.mapper.UserMapper;
import com.sarang.model.EnoVO;
import com.sarang.service.UserService;

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

    @Override
    public Integer insertUserInfo(EnoVO enoVO){
        return userMapper.insertUserInfo(enoVO);
    }

    @Override
    public Integer deleteUserInfo(List<String> deleteList){
        return userMapper.deleteUserInfo(deleteList);
    }
}
