package com.sarang.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.FileVO;

@Mapper
public interface FileMapper {
    Integer insertUploadedFileInfo(FileVO fileVO);
    FileVO getDownloadFileInfo(Integer fileId);   
}
