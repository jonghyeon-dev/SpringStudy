package com.sarang.service.user;

import java.util.HashMap;
import java.util.List;

import com.sarang.model.user.EnoVO;

public interface UserService{
    List<EnoVO> getUserInfo();
    List<EnoVO> searchUserInfo(HashMap<String,Object> reqMap);
}
