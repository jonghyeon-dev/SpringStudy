package com.sarang.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sarang.model.common.FileVO;

@Mapper
public interface FileMapper {
    Integer insertUploadedFileInfo(FileVO fileVO);
    FileVO getDownloadFileInfo(Integer fileId);   
}
