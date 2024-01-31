package com.sarang.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class FileVO {
    private Integer fileId;     //파일ID
    @NonNull
    private Integer boardId;    //게시판ID
    @NonNull
    private String filePath;    //파일경로
    @NonNull
    private String fileName;    //원본 파일명칭
    @NonNull
    private String fileCutName; //확장자없는 파일명칭
    @NonNull
    private String saveFileName;//저장될 파일명칭
    @NonNull
    private String fileExt;     //파일확장자
    private String cretUser;    //생성자
    private String cretDate;    //생성일자
    private String cretTime;    //생성시간
}
