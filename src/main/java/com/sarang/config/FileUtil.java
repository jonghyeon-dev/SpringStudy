package com.sarang.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import java.io.File;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sarang.mapper.FileMapper;
import com.sarang.model.AdminVO;
import com.sarang.model.FileVO;

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
                String fileExt = fileName.substring(fileName.lastIndexOf("."));

                // 실제 서버에 저장될 파일명
                String saveFileName = UUID.randomUUID().toString();
                
                // 저장될 경로와 파일명
                String saveFilePath = filePath + File.separator + saveFileName + fileExt;

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

                        String dictFile = filePath + File.separator + saveFileName + "("+ index+ ")" + fileExt;

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
                fileVO.setCretUser(adminVO.getEno());
                
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

    @GetMapping("/file/download")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try{
            // 다운로드 받을 파일의 ID를 가져온다.
            Integer fileId = Integer.parseInt(request.getParameter("fileId"));

            /* 이 밑으로 저눕 수정 필요  */
            // DB에서 다운로드 받을 파일의 정보를 가져온다.
            FileVO fileVO = fileMapper.getDownloadFileInfo(fileId);
			
			// 경로와 파일명으로 파일 객체를 생성한다.
			File dFile  = new File(fileVO.getFilePath(), fileVO.getSaveFileName() + fileVO.getFileExt());
			
			// 파일 길이를 가져온다.
			int fSize = (int) dFile.length();
			
			// 파일이 존재
			if (fSize > 0) {
				
				// 파일명을 URLEncoder 하여 attachment, Content-Disposition Header로 설정
				String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileVO.getFileName(), "UTF-8");
				
				// ContentType 설정
				response.setContentType("application/octet-stream; charset=utf-8");
				
				// Header 설정
				response.setHeader("Content-Disposition", encodedFilename);
				
				// ContentLength 설정
				response.setContentLengthLong(fSize);
	
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
				
				/* BufferedInputStream
				 * 
					java.io의 가장 기본 파일 입출력 클래스
					입력 스트림(통로)을 생성해줌
					사용법은 간단하지만, 버퍼를 사용하지 않기 때문에 느림
					속도 문제를 해결하기 위해 버퍼를 사용하는 다른 클래스와 같이 쓰는 경우가 많음
				*/
				in = new BufferedInputStream(new FileInputStream(dFile));
				
				/* BufferedOutputStream
				 * 
					java.io의 가장 기본이 되는 파일 입출력 클래스
					출력 스트림(통로)을 생성해줌
					사용법은 간단하지만, 버퍼를 사용하지 않기 때문에 느림
					속도 문제를 해결하기 위해 버퍼를 사용하는 다른 클래스와 같이 쓰는 경우가 많음
				*/
				out = new BufferedOutputStream(response.getOutputStream());
				
				try {
					byte[] buffer = new byte[4096];
				 	int bytesRead=0;
				 	
				 	/*
						모두 현재 파일 포인터 위치를 기준으로 함 (파일 포인터 앞의 내용은 없는 것처럼 작동)
						int read() : 1byte씩 내용을 읽어 정수로 반환
						int read(byte[] b) : 파일 내용을 한번에 모두 읽어서 배열에 저장
						int read(byte[] b. int off, int len) : 'len'길이만큼만 읽어서 배열의 'off'번째 위치부터 저장
				 	*/
				 	while ((bytesRead = in.read(buffer))!=-1) {
						out.write(buffer, 0, bytesRead);
					}
					
				 	// 버퍼에 남은 내용이 있다면, 모두 파일에 출력
					out.flush();
				}
				finally {
					/*
						현재 열려 in,out 스트림을 닫음
						메모리 누수를 방지하고 다른 곳에서 리소스 사용이 가능하게 만듬
					*/
					in.close();
					out.close();
				}
		    } else {
		    	throw new FileNotFoundException("파일이 없습니다.");
		    }
        }catch(Exception e){
            logger.info(e.getMessage());
        }
    }
    
}
