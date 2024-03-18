package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BoardVO {
    private Integer boardId;         //게시판ID
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

    //SubQuery
    private String asNew;           //24시간내 게시여부
    private String viewCnt;         //조회수
    private String likeChuCnt;      //추천수
    private String cretUserNm;      //생성자명
    private String chgUserNm;       //수정자명
}
