package com.sarang.mapper.admin;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.admin.AdminVO;

@Mapper
public interface AdminMapper {
    List<AdminVO> searchAdminInfo(HashMap<String,Object> reqMap);
}
