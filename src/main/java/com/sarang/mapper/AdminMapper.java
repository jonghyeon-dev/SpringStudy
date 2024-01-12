package com.sarang.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.AdminVO;

@Mapper
public interface AdminMapper {
    HashMap<String,Object> getAdminPageInfo(HashMap<String,Object> reqMap);
    List<AdminVO> searchAdminInfo(HashMap<String,Object> reqMap);
    Integer insertAdminInfo(AdminVO enoVO);
    Integer deleteAdminInfo(List<String> deleteList);
    AdminVO checkAdminLogin(HashMap<String,Object> reqMap);
}
