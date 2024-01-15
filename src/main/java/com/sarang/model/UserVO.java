package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/** 
 * @Class UserVO
 * @Description 관리자정보 객체
 * 
 * @author 박종현
 * 
 */
@Getter
@Setter
@ToString
public class UserVO {
    private Integer seq;         // 순차번호
    @NonNull
    private String userId;      // 사용자아이디
    @NonNull
    private String userPw;      // 사용자비밀번호
    @NonNull
    private String userNm;      // 사용자별칭
    private String celph;       // 전화번호
    private String email;       // 이메일
    private String cretDate;    // 생성일자
    private String cretTime;    // 생성시간
    private String chgDate;     // 수정일자
    private String chgTime;     // 수정시간
}
