package com.sarang.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardViewVO {
    private Integer seq;            //순차번호
    private Integer boardId;        //게시판ID
    private String cretUser;        //생성자
    private String cretDate;        //생성일자
    private String cretTime;        //생성시간
}
