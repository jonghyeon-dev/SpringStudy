package com.sarang.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sarang.config.FileUtils;
import com.sarang.mapper.FileMapper;
import com.sarang.model.UserVO;
import com.sarang.model.common.FileVO;

@Controller
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	// 파일의 경로를 지정한다.
    @Value("${spring.servlet.multipart.location}")
    private String filePath;

	@Autowired
	FileUtils fileUtils;

    @Autowired
    FileMapper fileMapper;

	@ResponseBody
	@PostMapping("/file/imageUpload")
	public Map<String,Object> imageUpload(HttpSession session, MultipartHttpServletRequest request
	, HttpServletResponse response, Model model) throws Exception{
		Map<String,Object> result = new HashMap<>();
		UserVO loginVO = (UserVO) session.getAttribute("userLogin");
		if(ObjectUtils.isEmpty(loginVO)){
			result.put("uploaded",false);
			HashMap<String,Object> message = new HashMap<>();
			message.put("message","file upload Fail");
			result.put("error",message);
            return result;
        }
		MultipartFile uploadFile = request.getFile("upload");
		FileVO fileInfo = new FileVO();
		try{
			fileInfo = fileUtils.FileUpload(session, uploadFile);
		}catch(Exception e){
			logger.error("File Upload Error : {}",e.getMessage());
		}
		if(fileInfo == null){
			result.put("uploaded",false);
			HashMap<String,Object> message = new HashMap<>();
			message.put("message","file upload Fail");
			result.put("error",message);
		}else{
			result.put("uploaded",true);
			result.put("filename",fileInfo.getFileName());
			result.put("url","/image/display/"+fileInfo.getFileId());
		}
		return result;
	}

	@GetMapping("/image/display/{fileId}")
	public ResponseEntity<byte[]> getImage(HttpSession session, HttpServletRequest request, HttpServletResponse response,
	@PathVariable("fileId") Integer fileId) throws Exception{
		
		ResponseEntity<byte[]> result = null;
		try{
			FileVO fileVO = fileMapper.getDownloadFileInfo(fileId);

			// 경로와 파일명으로 파일 객체를 생성한다.
			File dFile  = new File(fileVO.getFilePath(), fileVO.getSaveFileName() + "." + fileVO.getFileExt());
				
			// 파일 길이를 가져온다.
			int fSize = (int) dFile.length();
			
			// 파일이 존재
			if (fSize > 0) {
				HttpHeaders header = new HttpHeaders();
				header.add("Content-type", Files.probeContentType(dFile.toPath()));

				result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(dFile), header, HttpStatus.OK);
			}
		}catch(Exception e){
			logger.error("Error is : {}",e.getMessage());
		}
		return result;
	}

    @GetMapping("/file/download")
    public void fileDownload(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		UserVO loginVO = (UserVO)session.getAttribute("userLogin");
		if(ObjectUtils.isEmpty(loginVO)){
			response.sendRedirect("/error");
		}
        try{
            // 다운로드 받을 파일의 ID를 가져온다.
            Integer fileId = Integer.parseInt(request.getParameter("fileId"));

            /* 이 밑으로 전부 수정 필요  */
            // DB에서 다운로드 받을 파일의 정보를 가져온다.
            FileVO fileVO = fileMapper.getDownloadFileInfo(fileId);
			
			// 경로와 파일명으로 파일 객체를 생성한다.
			File dFile  = new File(fileVO.getFilePath(), fileVO.getSaveFileName() + "." + fileVO.getFileExt());
			
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