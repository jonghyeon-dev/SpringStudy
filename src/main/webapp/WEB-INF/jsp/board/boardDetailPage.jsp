<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>게시판상세</title>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light">
    <div class="container">
        <%@include file="../layouts/top.jsp"%> 
        <div>
            <div>
                <div class="insertArea">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">제목:&nbsp;</span>
                        </div>
                        <p class="form-control"><c:out value='${boardInfo.boardTitle}'/></p>
                    </div>
                    <c:if test="${not empty boardFileList}">
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">첨부파일</span>
                            </div>
                            <div id="fileList" class="ms-2">
                                <c:forEach items="${boardFileList}" var="items">
                                    <c:choose>
                                        <c:when test="${userLogin ne null or adminLogin ne null}">
                                            <a class="link-dark" href="<c:url value='/file/download?fileId=${items.fileId}'/>"><c:out value="${items.fileName}"/></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="link-dark"><c:out value="${items.fileName}"/></a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-control ck-content">
                        <c:out value='${boardInfo.boardCntnt}' escapeXml="false"/>
                    </div>
                    <c:choose>
                        <c:when test="${userLogin ne null}">
                            <div class="d-flex justify-content-center mt-2 mb-2">
                                <strong>추천&nbsp;</strong>
                                <c:choose>
                                    <c:when test="${retRecomInfo ne null && retRecomInfo.likeChu eq '1'}">
                                        <span class="bg-white border-2 shadow rounded-1 bi bi-heart" style="color:red; cursor:pointer" id="recom">
                                            <input type="hidden" name="like" value="${retRecomInfo.likeChu}">
                                            <strong style="color:black;">${recomCnt}</strong>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="bg-white border-2 shadow rounded-1 bi bi-heart" style="color:blue; cursor:pointer" id="recom">
                                            <input type="hidden" name="like" value="0">
                                            <strong style="color:black;">${recomCnt}</strong>
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <script>
                                $("#recom").click(()=>{
                                    let likeChu = 0;
                                    const like = $("#recom").find("input[name='like']").val();
                                    const boardId = '${boardId}';
                                    if(like === "0"){
                                        $("#recom").find("input[name='like']").val("1");
                                        $("#recom").css("color","red");
                                        likeChu = 1;
                                    }else{
                                        $("#recom").find("input[name='like']").val("0");
                                        $("#recom").css("color","blue");
                                    }
                                    $.ajax({
                                        url: '<c:url value="/board/${category}/boardRecom"/>',
                                        type: "POST",
                                        dataType: "json",
                                        traditional:true,
                                        data: {
                                                "boardId":boardId,
                                                "likeChu":likeChu
                                            },
                                        success: response=>{
                                            if(response.isSucceed){
                                                let data = response.data;
                                                $("#recom").find("span").text(data.recomCnt);
                                            }else{
                                                console.log(response.message);
                                            }
                                        },error: e=>{
                                            console.log("Ajax Get Data Error :: ", e);
                                        }
                                    });
                                    
                                })
                            </script>
                        </c:when>
                        <c:otherwise>
                            <div class="d-flex justify-content-center mt-2 mb-2">
                                <strong>추천&nbsp;</strong>
                                <span class="bg-white border-2 shadow rounded-1 bi bi-heart" style="color:blue;">
                                    <strong style="color:black;">${recomCnt}</strong>
                                </span>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="list-body white-bg" id="answerInsertArea">
                    <div class="list-box">
                        <span>사용자1</span>님의 댓글:&nbsp;
                        <div class="row">
                            <p class="col answerText">안녕하세요.</p>
                            <c:if test="">
                                <form class="form-box answerModifyArea" style="display:none" method="POST" action="/board/modifyAnswer?_method=PUT">
                                    <input type="hidden" name="boardId" value="">
                                    <input type="hidden" name="answerId" value="">
                                    <input class="form-control" type="text" name="content" value="">
                                    <button type="button" class="btn btn-warning ms-2 float-end answerModifyCancle">취소</button>
                                    <button type="submit" class="btn float-end">등록</button>
                                </form>
                                <div class="col answerBtnArea">
                                    <a class="btn btn-sm btn-danger float-end ansDelete" data-id="">삭제</a>
                                    <a class="btn btn-sm btn-success float-end ansModify">수정</a>
                                </div>
                                <!-- <script>
                                    document.querySelectorAll('.ansModify').forEach((col) =>{
                                        col.addEventListener('click', (event) =>{
                                        event.target.parentNode.style.display="none";
                                        event.target.parentNode.parentNode.querySelectorAll(".answerModifyArea")[0].style.display="inline";
                                        event.target.parentNode.parentNode.querySelectorAll(".answerText")[0].style.display="none";
                                        });
                                    });
                                
                                    document.querySelectorAll('.answerModifyCancle').forEach((col) =>{
                                        col.addEventListener('click', (event) =>{
                                        event.target.parentNode.style.display="none";
                                        event.target.parentNode.parentNode.querySelectorAll(".answerText")[0].style.display="inline";
                                        event.target.parentNode.parentNode.querySelectorAll(".answerBtnArea")[0].style.display="inline";
                                        });
                                    });
                                
                                    document.querySelectorAll('.recDelete').forEach((col) =>{
                                        col.addEventListener('click', (event) =>{
                                        let id = event.target.dataset.id;
                                        console.log("삭제ID: "+id);
                                        fetch('/board/deleteAnswer',{
                                            method:"DELETE",
                                            body:JSON.stringify({id:id}),
                                            headers:{"Content-Type":"application/json"}
                                            }).then((response)=>response.json())
                                            .then((data)=>{
                                                if(data.isSucceed){
                                                    location.reload();
                                                }else{
                                                    console.log(data.msg);
                                                }
                                        });
                                        });
                                    });
                                </script> -->
                            </c:if>
                        </div>
                    </div>
                </div>
                <c:if test="${userLogin ne null}">
                <div id="recomWrite" class="mb-2">
                    <form id="recomForm" class="form-box" method="POST" action="/board/addRecom">
                        <div>
                            <h4 class=""><strong>댓글쓰기</strong></h4>
                            <input type="text" class="form-control" name="content">
                        </div>
                        <input type="hidden" name="postId" value="">
                        <div class="recomBtnArea text-end">
                            <button class="btn btn-lg btn-primary">작성</button>
                        </div>
                    </form>
                </div>
                </c:if>
            </div>
            <div class="buttonArea text-start">
                <c:choose>
                    <c:when test="${page ne null && page ne ''}">
                        <a href='<c:url value="/board/${category}/${page}?searchOption=${searchOption}&searchWord=${searchWord}"/>' class="btn btn-outline-primary text-center">목록</a>
                    </c:when>
                    <c:otherwise>
                        <a href='<c:url value="/board/${category}/1?searchOption=${searchOption}&searchWord=${searchWord}"/>' class="btn btn-outline-primary text-center">목록</a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${category eq 'community'}">
                        <c:if test="${userLogin.userId eq boardInfo.cretUser}">
                            <a href='<c:url value="/board/${category}/boardModify/${boardId}"/>' class="btn btn-outline-warning text-center">수정</a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${userLogin.userId eq boardInfo.cretUser && userLogin.userGrant eq '0'}">
                            <a href='<c:url value="/board/${category}/boardModify/${boardId}"/>' class="btn btn-outline-warning text-center">수정</a>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
            
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>
 var boardDetailPage = (function(){
        var init = function(){

        };

        var registerEvent = function(){
           
        };

        return {
            init: function(){
                init();
                registerEvent();
            }
        }
    }());

    $(document).ready(e=>{
        boardDetailPage.init();
    });
</script>
</html>