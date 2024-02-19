package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BoardRecommandVO {
    private Integer seq;        //순차번호
    @NonNull
    private Integer boardId;    //게시판ID
    private char upDown;        //추천1/비추천2/취소0
    private String cretUser;    //생성자
    private String cretDate;    //생성일자
    private String cretTime;    //생성시간
}
