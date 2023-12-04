package com.sarang.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** 
 * @Class UserVO
 * @Description 사용자정보 객체
 * 
 * @author 박종현
 * 
 */
@Getter
@Setter
@ToString
public class UserVO {
    private String userId;      // 아이디
    private String userPw;      // 비밀번호
    private String userTel;     // 전화번호
    private String userEmail;   // 이메일
    private String userBod;     //
    private String userSxds;


}
