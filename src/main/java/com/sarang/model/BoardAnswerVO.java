package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BoardAnswerVO {
    private Integer boardComntId;   //게시판댓글ID
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

}
