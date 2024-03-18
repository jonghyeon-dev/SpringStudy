package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardComntVO {
    private Integer comntId;        //댓글ID
    @NonNull
    private Integer boardId;        //게시판ID
    private Integer parntId;        //부모댓글 ID
    private Integer originId;       //계층구조 최상위ID
    private Integer groupStep;      //계층구조 순서
    private Integer groupLayer;     //계층구조 단계(level)
    @NonNull
    private String boardComnt;      //댓글내용
    private String cretUser;        //생성자
    private String cretDate;        //생성일자
    private String cretTime;        //생성시간
    private String chgUser;         //수정자
    private String chgDate;         //수정일자
    private String chgTime;         //수정시간
    private String delYn;           //삭제여부

    //추가
    private String cretUserNm;      //생성자명
    private String chgUserNm;       //수정자명

}
