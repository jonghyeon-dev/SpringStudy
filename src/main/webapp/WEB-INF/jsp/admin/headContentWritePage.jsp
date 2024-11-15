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
                            <div class="p-4 p-md-5 text-start">
                                <div class="h2 fw-bolder prevHead">
                                    헤드 컨텐츠1
                                </div>
                                <p class="prevText">
                                    내용 작성1
                                </p>
                                <div class="stretched-link text-decoration-none">
                                    More
                                    <i class="bi bi-arrow-right"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6 col-xl-7">
                            <div class="bg-featured-blog prevImg" style="background-image: url('https://dummyimage.com/700x350/343a40/6c757d')"></div>
                        </div>
                    </div>
                </div>
            </div>
            <form id="headContentInsertForm" method="post" action="<c:url value='/admin/${actionUrl}'/>" enctype="multipart/form-data" onSubmit="return validationForm()">
                <div class="insertArea">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                <label for="uploadImage">
                                    <div class="btn btn-primary">파일첨부</div>
                                </label>
                            </span>
                        </div>
                        <input class="d-none form-control" type="file" id="uploadImage" name="uploadImage" accept="image/*" onchange="imageUpload(this)">
                        <div id="uploadImageView" class="ms-2">
                            <c:if test="${status eq 'update'}">
                                <!-- 이미지 정보 -->
                                <p>
                                    <input type="hidden" name="imgFileId" value="${headContentInfo.imgFileId}">
                                    ${headContentInfo.fileName}
                                    <button type="button" class="btn-close" onclick="javascript:deleteUploadFile(this);"></button>
                                </p>
                            </c:if>
                        </div>
                    </div>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">제목:&nbsp;</span>
                        </div>
                        <input type="text" class="form-control" name="title">
                    </div>
                    <div class="input-group">
                        <c:if test="${status eq 'insert'}">
                            <div class="input-group-prepend">
                                <span class="input-group-text" style="height:100%;">
                                    공지사항 생성:&nbsp;
                                    <input type="checkbox" name="createNotice" id="createNotice" checked="true">
                                </span> 
                            </div>
                            <script>
                                $("#createNotice").change((e)=>{
                                    let checked = $("#createNotice").is(":checked");
                                    if(checked){
                                        $("#headContentInsertForm input[name='conectUrl']").attr("disabled",true);
                                    }else{
                                        $("#headContentInsertForm input[name='conectUrl']").removeAttr("disabled");
                                    }
                                })
                            </script>
                        </c:if>
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                연결URL&nbsp;:&nbsp;
                            </span>
                        </div>

                        <input type="text" class="form-control" name="connectUrl" <c:if test="${status eq 'insert'}"> disabled="true" </c:if>></input>
                    </div>
                    <div class="input-group">
                        <div class="input-group">
                            <span class="input-group-text">
                                시작일자&nbsp;:&nbsp;
                            </span>
                            <input type="date" class="form-control datepicker" id="strDate" name="strDate" placeholder="시작일자"/>
                            <span class="input-group-text">
                                종료일자&nbsp;:&nbsp;
                            </span>
                            <input type="date" class="form-control datepicker" id="endDate" name="endDate" placeholder="종료일자"/>
                        </div>
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
                    <c:if test="${status eq 'update'}">
                        <a href="<c:url value='/admin/deleteHeadContent/${headContentInfo.contentSeq}' />" class="btn btn-warning text-center">삭제</a>
                    </c:if>
                    <a href='<c:url value="/admin/headContent.do"/>' class="btn btn-danger text-center">취소</a>
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
            const pageStatus = '<c:out value="${status}"/>';

            if(pageStatus === "update"){
                const headContentTitle = `<c:out value='${headContentInfo.title}'/>`;
                const headContentCntnt = `<c:out value='${headContentInfo.cntnt}'/>`;
                const connectUrl = `<c:out value='${headContentInfo.connectUrl}'/>`;
                const imgFileId = `<c:out value='${headContentInfo.imgFileId}'/>`
                const strDate = `<c:out value='${headContentInfo.strDate}'/>`;
                const endDate = `<c:out value='${headContentInfo.endDate}'/>`;

                $("#headContentInsertForm input[name='title']").val(headContentTitle);
                $("#headContentInsertForm textarea[name='cntnt']").text(headContentCntnt);
                $("#headContentInsertForm input[name='connectUrl']").val(connectUrl);
                $(".prevHead").text(headContentTitle);
                $(".prevText").html(headContentCntnt.replaceAll("\n","<br/>"));
                $(".prevImg").css("background-image","url(/image/display/"+imgFileId+")");
                $("#strDate").val(dateConvert(strDate));
                $("#endDate").val(dateConvert(endDate));
            }
            function dateConvert(yyyyMMdd){
                let yyyy = yyyyMMdd.substring(0,4);
                let mm = yyyyMMdd.substring(4,6);
                let dd = yyyyMMdd.substring(6,8);
                return yyyy + "-" + mm  + "-" + dd;
            }
        };

        var registerEvent = function(){


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
        const title = $('#headContentInsertForm input[name="title"]').val(); // 제목
        if(title.trim() == "" || title == null){
            $("#modalTitle").append("제목이 없습니다.");
            $("#modalContent").append("제목은 필수 값 입니다.");
            $("#modalInfo").show();
            $('#headContentInsertForm input[name="title"]').focus();
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

    function imageUpload(e){
        const files = e.files;
        if(files && files[0]){
            let reader = new FileReader();
            reader.onload = function(event){
                document.querySelector("div.prevImg").style.backgroundImage="url("+event.target.result+")";
            };
            reader.readAsDataURL(files[0]);
        }
        let filesText = "";
        for(let i=0;i<files.length;i++){
            filesText = filesText + "<p>" + files[i].name;
            filesText = filesText + `<button type="button" class="btn-close" onclick="javascript:deleteUploadFile(this);"></button>`;
            filesText = filesText + "</p>";
        }
        $("#uploadImageView").empty();
        $("#uploadImageView").append(filesText);
    };

    function deleteUploadFile(e){
        $("#uploadImage").val("");
        document.querySelector(".prevImg").style.backgroundImage="url(https://dummyimage.com/700x350/343a40/6c757d)";
        e.parentNode.remove();
    }
</script>
</html>
