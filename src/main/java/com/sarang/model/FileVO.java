package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class FileVO {
    private String fileId;      //파일ID
    @NonNull
    private String boardId;     //게시판ID
    @NonNull
    private String filePath;    //파일경로
    @NonNull
    private String fileName;    //파일명칭
    @NonNull
    private String fileExt;     //파일확장자
    private String cretUser;    //생성자
    private String cretDate;    //생성일자
    private String cretTime;    //생성시간
}
