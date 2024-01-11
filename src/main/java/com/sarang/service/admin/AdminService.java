package com.sarang.service.admin;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.admin.AdminVO;

public interface AdminService{
    List<AdminVO> searchAdminInfo(HashMap<String,Object> reqMap);
}

