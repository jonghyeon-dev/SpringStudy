package com.sarang.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sarang.mapper.UserMapper;
import com.sarang.model.UserVO;
import com.sarang.repository.UserRepository;
import com.sarang.service.impl.UserServiceImpl;

@Transactional
@Service
public class UserService implements UserServiceImpl{
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserVO checkAdminLogin(HashMap<String,Object> reqMap){
        return userMapper.checkAdminLogin(reqMap);
    }

    @Override
    public UserVO checkUserLogin(HashMap<String,Object> reqMap){
        return userMapper.checkUserLogin(reqMap);
    }

    @Override
    public UserVO findByUserId(String userId){
        UserVO userVO = userRepository.findByUserId(userId);
        return userVO;
    }

    @Override
    public List<UserVO> getUserInfo(HashMap<String,Object> reqMap){
        return userMapper.getUserInfo(reqMap);
    }

    @Override
    public HashMap<String,Object> getUserPageInfo(HashMap<String,Object> reqMap){
        return userMapper.getUserPageInfo(reqMap);
    }

    @Override
    public String checkUserDuplication(String userId){
        return userMapper.checkUserDuplication(userId);
    }

    @Override
    public Integer insertUserInfo(UserVO userVO){
        return userMapper.insertUserInfo(userVO);
    }

    @Override
    public Integer deleteUserInfo(List<String> deleteList){
        return userMapper.deleteUserInfo(deleteList);
    }

    @Override
    public Integer updateUserInfo(UserVO userVO){
        return userMapper.updateUserInfo(userVO);
    }

    @Override
    public UserVO checkUserPass(HashMap<String,Object> reqMap){
        return userMapper.checkUserPass(reqMap);
    }
    
    @Override
    public Integer updateUserPass(HashMap<String,Object> reqMap){
        return userMapper.updateUserPass(reqMap);
    }
}

