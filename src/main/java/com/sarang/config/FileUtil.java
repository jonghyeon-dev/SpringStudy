package com.sarang.config;

import javax.servlet.http.HttpSession;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sarang.mapper.FileMapper;
import com.sarang.model.AdminVO;
import com.sarang.model.FileVO;
import com.sarang.model.UserVO;

@Component
public class FileUtil {
    private Logger logger = LoggerFactory.getLogger(FileUtil.class);

    // 파일이 업로드 될 경로를 지정한다.
    @Value("${uploadSet.filePath}")
    private String filePath;

    @Autowired
    FileMapper fileMapper;

    public boolean MultiFileUpload(HttpSession session, Integer boardId, MultipartHttpServletRequest request){
        AdminVO adminVO = (AdminVO) session.getAttribute("adminLogin");
        UserVO loginVO = (UserVO) session.getAttribute("userLogin");
        if(ObjectUtils.isEmpty(adminVO) && ObjectUtils.isEmpty(loginVO)){
            return false;
        }
        // DB에 저장될 정보
        FileVO fileVO = new FileVO();
        try{
            // 파라미터 이름을 키로 파라미터에 해당하는 파일 정보를 값으로 하는 Map을 가져온다.
            Map<String, MultipartFile> files = request.getFileMap();

            // files.entrySet()의 요소를 읽어온다.
            Iterator<Entry <String,MultipartFile>> itr = files.entrySet().iterator();
            
            MultipartFile mFile;

            // 읽어 올 요소가 있으면 true, 없으면 false를 반환한다.
            while(itr.hasNext()){
                Entry<String, MultipartFile> entry = itr.next();

                mFile = entry.getValue();

                // 원본 파일명(확장자 포함)
                String fileName = mFile.getOriginalFilename();
                
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
                    mFile.transferTo(saveFile);
                } else{
                    mFile.transferTo(saveFile);
                }

                
                fileVO.setBoardId(boardId);
                fileVO.setFileName(fileName);
                fileVO.setFileCutName(fileCutName);
                fileVO.setSaveFileName(saveFileName);
                fileVO.setFileExt(fileExt);
                if(ObjectUtils.isEmpty(loginVO)){
                    fileVO.setCretUser(adminVO.getEno());
                }else{
                    fileVO.setCretUser(loginVO.getUserId());
                }

                
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
}
