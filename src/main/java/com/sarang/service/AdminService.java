package com.sarang.service;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.AdminVO;

public interface AdminService{
    List<AdminVO> searchAdminInfo(HashMap<String,Object> reqMap);
}

