package com.sarang.service;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.AdminVO;

public interface AdminService{
    HashMap<String,Object> getAdminPageInfo(HashMap<String,Object> reqMap);
    List<AdminVO> searchAdminInfo(HashMap<String,Object> reqMap);
    Integer insertAdminInfo(AdminVO enoVO);
    Integer deleteAdminInfo(List<String> deleteList);
    AdminVO checkAdminLogin(HashMap<String,Object> reqMap);
}
