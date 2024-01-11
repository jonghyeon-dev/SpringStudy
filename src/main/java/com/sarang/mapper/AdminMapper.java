package com.sarang.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.AdminVO;

@Mapper
public interface AdminMapper {
    List<AdminVO> searchAdminInfo(HashMap<String,Object> reqMap);
}
