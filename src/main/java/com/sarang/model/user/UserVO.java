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
    private String celph;       // 전화번호
    private String email;       // 이메일
    private String cretDate;    // 생성일자
    private String cretTime;    // 생성시간
    private String chgDate;     // 수정일자
    private String chgTime;     // 수정시간
}
