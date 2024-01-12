package com.sarang.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarang.mapper.AdminMapper;
import com.sarang.model.AdminVO;
import com.sarang.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminMapper adminMapper; 

    @Override
    public HashMap<String,Object> getAdminPageInfo(HashMap<String,Object> reqMap){
        return adminMapper.getAdminPageInfo(reqMap);
    }

    @Override
    public List<AdminVO> searchAdminInfo(HashMap<String,Object> reqMap){
        return adminMapper.searchAdminInfo(reqMap);
    }

    @Override
    public Integer insertAdminInfo(AdminVO enoVO){
        return adminMapper.insertAdminInfo(enoVO);
    }

    @Override
    public Integer deleteAdminInfo(List<String> deleteList){
        return adminMapper.deleteAdminInfo(deleteList);
    }

    @Override
    public AdminVO checkAdminLogin(HashMap<String,Object> reqMap){
        return adminMapper.checkAdminLogin(reqMap);
    }
}
