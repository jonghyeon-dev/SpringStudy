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
                <c:set var="actionUrl" value="insertHeadContent.do"/>
            </c:if>
            <c:if test="${status eq 'update'}">
                <c:set var="actionUrl" value="updateHeadContent/${headContentInfo.contentSeq}"/>
            </c:if>
            <div class="card border-0 shadow rounded-3 overflow-hidden">
                <div class="card-body p-0">
                    <div class="row gx-0">
                        <div class="col-lg-6 col-xl-5 py-lg-5">
                            <div class="p-4 p-md-5 slider s1">
                                <div class="h2 fw-bolder prevHead">
                                    <c:if test="${status eq 'insert'}">헤드 컨텐츠1</c:if>
                                    <c:if test="${status eq 'update'}">${headContentInfo.title}</c:if>
                                </div>
                                <p class="prevText">
                                    <c:if test="${status eq 'insert'}">내용 작성1</c:if>
                                    <c:if test="${status eq 'update'}">${headContentInfo.cntnt}</c:if>
                                </p>
                            </div>
                        </div>
                        <div class="col-lg-6 col-xl-7">
                            <c:if test="${status eq 'insert'}"><div class="bg-featured-blog prevImg" style="background-image: url('https://dummyimage.com/700x350/343a40/6c757d')"></div></c:if>
                            <c:if test="${status eq 'update'}"><div class="bg-featured-blog prevImg" style="background-image: url('/image/display/${headContentInfo.imgFileId}')"></div></c:if>
                        </div>
                    </div>
                </div>
            </div>
            <form id="headContentInsertForm" method="post" action="<c:url value='/admin/${actionUrl}'/>" enctype="multipart/form-data" onSubmit="return validationForm()">
                <div class="insertArea">
                    <div class="input-group-prepend">
                        <span class="input-group-text">
                            <label for="uploadFiles">
                                <div class="btn btn-primary">파일첨부</div>
                            </label>
                        </span>
                    </div>
                    <input class="d-none form-control" type="file" id="uploadImage" name="uploadImage">
                    <div id="uploadImageView" class="ms-2">
                        <c:if test="${status eq 'update'}">
                            <!-- 이미지 정보 -->
                            <p>
                                <input type="hidden" name="imgFileId" value="${headContentInfo.imgFileId}">
                                ${headContentFile.fileName}
                                <button type="button" class="btn-close" onclick="javascript:deleteUploadFile(this);"></button>
                            </p>
                        </c:if>
                    </div>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">제목:&nbsp;</span>
                        </div>
                        <input type="text" class="form-control" name="title">
                    </div>
                    <div class="form-control">
                        <textarea class="form-control" name="cntnt"></textarea>
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
                        <a href='<c:url value="/admin/headContent.do"/>' class="btn btn-danger text-center">취소</a>
                    </c:if>
                    <c:if test="${status eq 'update'}">
                        <a href='<c:url value="/admin/headContentDetail/${headContentInfo.contentSeq}"/>' class="btn btn-danger text-center">취소</a>
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
            // ckeditor = CKEDITOR.replace('ckeditor',{
            //     filebrowserUploadUrl:'<c:url value="/file/imageUpload?type=Images"/>'
            // });

            const pageStatus = '<c:out value="${status}"/>';

            if(pageStatus === "update"){
                const headContentTitle = `<c:out value='${headCOntentInfo.title}'/>`;
                const headContentdCntnt = `<c:out value='${headCOntentInfo.cntnt}'/>`;
                $("#headContentInsertForm input[name='title']").val(headContentTitle);
                $("#headContentInsertForm textarea[name='cntnt']").text(headContentdCntnt);
                // ckeditor.setData(unescapeHtml(headContentdCntnt));
            }
        };

        var registerEvent = function(){
            $("#uploadFiles").change((e) => {
                const files = $("#uploadFiles")[0].files;
                var filesText = "";
                for(var i=0;i<files.length;i++){
                    filesText = filesText + "<span>" + files[i].name + "</span><br>";
                }
                $("#uploadResult").empty();
                $("#uploadResult").append(filesText);
            })

            $("#headContentInsertForm input[name='title']").keyup((e)=>{
                $(".prevHead").html(e.target.value);
            })

            $("#headContentInsertForm textarea[name='cntnt']").keyup((e)=>{
                $(".prevText").html(e.target.value.replaceAll("\n","<br>"));
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

    function deleteUploadFile(e){
        e.parentNode.remove();
    }
</script>
</html>
