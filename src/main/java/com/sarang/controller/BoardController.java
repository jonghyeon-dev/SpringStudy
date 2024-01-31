package com.sarang.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sarang.config.FileUtil;
import com.sarang.mapper.FileMapper;
import com.sarang.model.BoardVO;
import com.sarang.model.FileVO;
import com.sarang.service.BoardService;



@Controller
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    private static final int pageSize = 10;

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileUtil fileUtil;

    @Autowired 
    private FileMapper fileMapper;
    
    @GetMapping(value="/board/{category}")
    public String adminLoginPage(HttpSession session, HttpServletRequest request
    , HttpServletResponse response , Model model, @PathVariable("category") String category) throws Exception{
        logger.info("boardPage View");

        if(category.isEmpty() || category == null){
            category = "community";
        }

        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("start",0);
        reqMap.put("size",pageSize);
        reqMap.put("boardCate",category);
        List<BoardVO> boardList = boardService.getBoardList(reqMap);
        HashMap<String,Object> pageInfo = boardService.getBoardPageInfo(reqMap);

        HashMap<String, Object> totalContents = new HashMap<>();
        totalContents.put("boardList", boardList);
        totalContents.put("totalPages", pageInfo.get("totalPage"));
        model.addAttribute("totalContents", totalContents);

        return "board/boardPage";
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
