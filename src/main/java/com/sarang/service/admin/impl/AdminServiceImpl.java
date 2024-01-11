package com.sarang.service.admin.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarang.mapper.admin.AdminMapper;
import com.sarang.model.admin.AdminVO;
import com.sarang.service.admin.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<AdminVO> searchAdminInfo(HashMap<String,Object> reqMap){
        return adminMapper.searchAdminInfo(reqMap);
    }
}

