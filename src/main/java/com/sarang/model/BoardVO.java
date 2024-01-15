package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {
    private String boardId;         //게시판ID
    @NonNull
    private String boardCate;       //게시판카테고리
    @NonNull
    private String boardTitle;      //게시판제목
    private String boardCntnt;      //게시판내용
    private String cretUser;        //생성자
    private String cretDate;        //생성일자
    private String cretTime;        //생성시간
    private String chgUser;         //수정자
    private String chgDate;         //수정일자
    private String chgTime;         //수정시간
}
