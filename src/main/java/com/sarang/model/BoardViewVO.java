package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardViewVO {
    private Integer viewId;     //조회ID
    @NonNull
    private Integer boardId;    //게시판ID
    private String cretUser;    //생성자
    private String cretDate;    //생성일자
    private String cretTime;    //생성시간

    //SubQuery
    private String cretUserNm;  //생성자명
}
