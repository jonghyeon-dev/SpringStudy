package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BoardComntVO {
    private Integer comntId;        //댓글ID
    @NonNull
    private Integer boardId;        //게시판ID
    private Integer parntComntId;   //부모댓글ID
    @NonNull
    private String boardComnt;      //댓글내용
    private String cretUser;        //생성자
    private String cretDate;        //생성일자
    private String cretTime;        //생성시간
    private String chgUser;         //수정자
    private String chgDate;         //수정일자
    private String chgTime;         //수정시간

    //SubQuery
    private String cretUserNm;      //생성자명
    private String chgUserNm;       //수정자명

}
