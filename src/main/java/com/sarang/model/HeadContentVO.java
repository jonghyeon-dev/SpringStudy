package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HeadContentVO {
    private Integer contentSeq;
    @NonNull
    private String title;           //제목
    private String cntnt;           //내용
    private String imgFileId;       //이미지파일ID
    @NonNull
    private String connectUrl;      //연결URL
    private String delYn;           //삭제여부
    private String strDate;         //시작일자
    private String endDate;         //종료시간
    private String cretUser;        //생성자
    private String cretDate;        //생성일자
    private String cretTime;        //생성시간
    private String chgUser;         //수정자
    private String chgDate;         //수정일자
    private String chgTime;         //수정시간

}
