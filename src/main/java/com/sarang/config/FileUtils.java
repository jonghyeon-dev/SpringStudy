package com.sarang.config;

import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.UUID;

import java.io.File;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sarang.mapper.FileMapper;
import com.sarang.model.UserVO;
import com.sarang.model.common.FileVO;

@Component
public class FileUtils {
    private Logger logger = LoggerFactory.getLogger(FileUtils.class);

    // 파일d 경로를 지정한다.
    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    @Autowired
    FileMapper fileMapper;

    public boolean MultiFileUpload(HttpSession session, Integer boardId, List<MultipartFile> uploadFiles){
        UserVO loginVO = (UserVO) session.getAttribute("userLogin");
        if(ObjectUtils.isEmpty(loginVO)){
            logger.error("empty Login Info MuiltiFileUpload Error");
            return false;
        }
        
        try{
            // DB에 저장될 정보
            FileVO fileVO = new FileVO();

            // 읽어 올 요소가 있으면 true, 없으면 false를 반환한다.
            for(MultipartFile file : uploadFiles){

                // 원본 파일명(확장자 포함)
                String fileName = file.getOriginalFilename();
                
                // 확장자를 제외한 파일명
                String fileCutName = fileName.substring(0, fileName.lastIndexOf("."));

                // 확장자
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

                // 실제 서버에 저장될 파일명
                String saveFileName = UUID.randomUUID().toString();
                
                // 저장될 경로와 파일명
                String saveFilePath = filePath + File.separator + saveFileName + '.' + fileExt;

                // filePath에 해당되는 파일의 File 객체를 생성
                File fileFolder = new File(filePath);

                if(!fileFolder.exists()){
                    // 부모 폴더까지 포함하여 경로에 폴더 생성
                    if(fileFolder.mkdirs()){
                        logger.info("[file.mkdir] : Success");
                    }else{
                        logger.error("[file.mkdir] : Fail");
                    }
                }

                File saveFile = new File(saveFilePath);
                // checkFileMimeType(saveFile);
                // saveFile이 File이면 true, 아니면 false
                // 파일명이 중복일 경우 파일명(1).확장자, 파일명(2).확장자와 같은 형태로 생성한다.
                if(saveFile.isFile()){
                    boolean _exist = true;

                    int index = 0;

                    // 동일한 파일명이 존재하지 않을때가지 반복한다.
                    while(_exist){
                        index++;

                        String dictFile = filePath + File.separator + saveFileName + "("+ index+ ")." + fileExt;

                        _exist = new File(dictFile).isFile();

                        if(!_exist){
                            saveFilePath = dictFile;
                        }
                    }
                    saveFile = new File(saveFilePath);
                    // 생성한 파일 객체를 업로드 처리하지 않으면 임시파일에 저장된 파일이 자동적으로 삭제되기 때문에 transferTo(File f) 메서를 이용해서 업로드 처리한다.
                    file.transferTo(saveFile);
                } else{
                    file.transferTo(saveFile);
                }

                fileVO.setBoardId(boardId);
                fileVO.setFileName(fileName);
                fileVO.setFileCutName(fileCutName);
                fileVO.setSaveFileName(saveFileName);
                fileVO.setFileExt(fileExt);
                fileVO.setFilePath(filePath);
                fileVO.setCretUser(loginVO.getUserId());
                
                fileMapper.insertUploadedFileInfo(fileVO);
            }
            return true;
        } catch(Exception e){
            if(logger.isErrorEnabled()){
                logger.error("#Exception Message : {}", e.getMessage());
            }
            return false;
        }
    }

    public FileVO FileUpload(HttpSession session, MultipartFile uploadFile){
        UserVO loginVO = (UserVO) session.getAttribute("userLogin");
        // DB에 저장될 정보
        FileVO fileVO = new FileVO();
        if(ObjectUtils.isEmpty(loginVO)){
            return fileVO;
        }
        try{
            // 읽어 올 요소가 있으면 true, 없으면 false를 반환한다.
                // 원본 파일명(확장자 포함)
                String fileName = uploadFile.getOriginalFilename();
                
                // 확장자를 제외한 파일명
                String fileCutName = fileName.substring(0, fileName.lastIndexOf("."));

                // 확장자
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

                // 실제 서버에 저장될 파일명
                String saveFileName = UUID.randomUUID().toString();
                
                // 저장될 경로와 파일명
                String saveFilePath = filePath + File.separator + saveFileName + '.' + fileExt;

                // filePath에 해당되는 파일의 File 객체를 생성
                File fileFolder = new File(filePath);

                if(!fileFolder.exists()){
                    // 부모 폴더까지 포함하여 경로에 폴더 생성
                    if(fileFolder.mkdirs()){
                        logger.info("[file.mkdir] : Success");
                    }else{
                        logger.error("[file.mkdir] : Fail");
                    }
                }

                File saveFile = new File(saveFilePath);
                // checkFileMimeType(saveFile);
                // saveFile이 File이면 true, 아니면 false
                // 파일명이 중복일 경우 파일명(1).확장자, 파일명(2).확장자와 같은 형태로 생성한다.
                if(saveFile.isFile()){
                    boolean _exist = true;

                    int index = 0;

                    // 동일한 파일명이 존재하지 않을때가지 반복한다.
                    while(_exist){
                        index++;

                        String dictFile = filePath + File.separator + saveFileName + "("+ index+ ")." + fileExt;

                        _exist = new File(dictFile).isFile();

                        if(!_exist){
                            saveFilePath = dictFile;
                        }
                    }
                    saveFile = new File(saveFilePath);
                    // 생성한 파일 객체를 업로드 처리하지 않으면 임시파일에 저장된 파일이 자동적으로 삭제되기 때문에 transferTo(File f) 메서를 이용해서 업로드 처리한다.
                    uploadFile.transferTo(saveFile);
                } else{
                    uploadFile.transferTo(saveFile);
                }

                fileVO.setFileName(fileName);
                fileVO.setFileCutName(fileCutName);
                fileVO.setSaveFileName(saveFileName);
                fileVO.setFileExt(fileExt);
                fileVO.setFilePath(filePath);
                fileVO.setCretUser(loginVO.getUserId());

                fileMapper.insertUploadedFileInfo(fileVO);
            return fileVO;
        } catch(Exception e){
            if(logger.isErrorEnabled()){
                logger.error("#FileUpload Exception Message : {}", e.getMessage());
            }
            return fileVO;
        }
    }

    private static boolean checkFileMimeType(File file) throws Exception{
        final String[] PERMISSION_FILE_MIME_TYPE = {"image/gif", "image/jpeg", "image/png", "image/bmp", "application/pdf", "video/mp4"};
        boolean isPermision = false;
        String mimeType = new Tika().detect(file);
        for(int i=0;i<PERMISSION_FILE_MIME_TYPE.length;i++){
            if(PERMISSION_FILE_MIME_TYPE[i].equals(mimeType)){

                // isPermision = true;
                break;
            }
        }
        
        return isPermision;
    }
}
