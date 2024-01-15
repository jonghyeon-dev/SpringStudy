package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardRecommandVO {
    private Integer seq;        //순차번호
    @NonNull
    private Integer boardId;    //게시판ID
    private char upDown;        //추천/비추천
    private String cretUser;    //생성자
    private String cretDate;    //생성일자
    private String cretTime;    //생성시간
}