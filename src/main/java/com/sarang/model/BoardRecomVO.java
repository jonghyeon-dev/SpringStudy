package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardRecomVO {
    private Integer recomId;    //추천ID
    @NonNull
    private Integer boardId;    //게시판ID
    private Integer likeChu;    //추천1/취소0
    private String cretUser;    //생성자
    private String cretDate;    //생성일자
    private String cretTime;    //생성시간
    private String chgDate;     //수정일자
    private String chgTime;     //수정시간

    //SubQuery
    private String cretUserNm;  //생성자명
}
