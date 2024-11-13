<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>글작성</title>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light">
    <div class="container">
        <%@include file="../layouts/top.jsp"%> 
        <div class="text-center">
            <c:if test="${status eq 'insert'}">
                <c:set var="actionUrl" value="boardInsert"/>
            </c:if>
            <c:if test="${status eq 'update'}">
                <c:set var="actionUrl" value="boardUpdate/${boardInfo.boardId}"/>
            </c:if>
            <form id="boardInsertForm" method="post" action="<c:url value='/board/${category}/${actionUrl}'/>" enctype="multipart/form-data" onSubmit="return validationForm()">
                <div class="insertArea">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">제목:&nbsp;</span>
                        </div>
                        <input type="text" class="form-control" name="boardTitle">
                    </div>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                <label for="uploadFiles">
                                    <div class="btn btn-primary">파일첨부</div>
                                </label>
                            </span>
                        </div>
                        <input class="d-none form-control" type="file" id="uploadFiles" name="uploadFiles" multiple>
                        <div id="uploadResult" class="ms-2">
                            <div id="uploadInsert"></div>
                            <c:if test="${status eq 'update'}">
                                <div id="uploadUpdate">
                                    <c:forEach items="${boardFileList}" var="items">
                                        <p>
                                            <input type="hidden" name="fileIds" value="${items.fileId}">
                                            ${items.fileName}
                                            <button type="button" class="btn-close" onclick="javascript:deleteUploadFile(this);"></button>
                                        </p>
                                    </c:forEach>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="form-control">
                        <textarea name="boardCntnt" id="ckeditor" name="ckeditor"></textarea>
                    </div>
                </div>
                <div class="buttonArea">
                    <button type="submit" id="btnSubmit" class="btn btn-success text-center">
                        <c:if test="${status eq 'insert'}">
                            등록
                        </c:if>
                        <c:if test="${status eq 'update'}">
                            수정
                        </c:if>
                    </button>
                    <c:if test="${status eq 'insert'}">
                        <a href='<c:url value="/board/${category}"/>' class="btn btn-danger text-center">취소</a>
                    </c:if>
                    <c:if test="${status eq 'update'}">
                        <a href='<c:url value="/board/${category}/detail/${boardInfo.boardId}"/>' class="btn btn-danger text-center">취소</a>
                    </c:if>
                </div>
            </form>
        </div>
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>

 var boardInsertPage = (function(){
        let ckeditor;
        var init = function(){
            ckeditor = CKEDITOR.replace('ckeditor',{
                filebrowserUploadUrl:'<c:url value="/file/imageUpload?type=Images"/>'
            });

            const pageStatus = '<c:out value="${status}"/>';

            if(pageStatus === "update"){
                const boardTitle = `<c:out value='${boardInfo.boardTitle}'/>`;
                const boardCntnt = `<c:out value='${boardInfo.boardCntnt}'/>`;
                $("#boardInsertForm input[name='boardTitle']").val(boardTitle);
                ckeditor.setData(unescapeHtml(boardCntnt));
            }
        };

        var registerEvent = function(){
            $("#uploadFiles").change((e) => {
                const files = $("#uploadFiles")[0].files;
                var filesText = "";
                for(var i=0;i<files.length;i++){
                    filesText = filesText + "<p>" + files[i].name;
                    filesText = filesText + `<button type="button" data-index="\${files[i].lastModified}" class="btn-close" onclick="javascript:deleteInsertFile(this);"></button>`;
                    filesText = filesText + "</p>";
                }
                $("#uploadInsert").empty();
                $("#uploadInsert").append(filesText);
            })
        };

        return {
            init: function(){
                init();
                registerEvent();
            }
        }
    }());

    $(document).ready(e=>{
        boardInsertPage.init();
    });

    function validate(){
        return false;
    }

    function validationForm(){
        const boardTitle = $('#boardInsertForm input[name="boardTitle"]').val(); // 제목
        if(boardTitle.trim() == "" || boardTitle == null){
            $("#modalTitle").append("제목이 없습니다.");
            $("#modalContent").append("제목은 필수 값 입니다.");
            $("#modalInfo").show();
            $('#boardInsertForm input[name="boardTitle"]').focus();
            return false;
        }
        return true;
    }

    function unescapeHtml(str){
        if(str == null){
            return "";
        }
        return str.replace(/&amp;lt;script&amp;gt;/g,'')
                .replace(/&amp;lt;\/script&amp;gt;/g,'')
                .replace(/javascript:/g,'')
                .replace(/&amp;/g,'&')
                .replace(/&lt;/g,'<')
                .replace(/&gt;/g,'>')
                .replace(/&quot;/g,'"')
                .replace(/&#034;/g,'"')
                .replace(/&#039;/g,"'")
                .replace(/&#39/g,"'")
                
    }

    function deleteInsertFile(e){
        const dataTransfer = new DataTransfer();
        const removeTargetId = e.dataset.index;
        const files = $("#uploadFiles")[0].files;

        Array.from(files)
            .filter(file => file.lastModified != removeTargetId)
            .forEach(file => {
            dataTransfer.items.add(file);
            });

        document.querySelector('#uploadFiles').files = dataTransfer.files;
        e.parentNode.remove();
    }

    function deleteUploadFile(e){
        e.parentNode.remove();
    }
</script>
</html>
