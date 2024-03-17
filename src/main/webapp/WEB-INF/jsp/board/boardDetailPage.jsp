<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>게시판상세</title>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light" oncontextmenu="return false" onselectstart="return false" ondragstart="return false">
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
                                            <c:choose>
                                                <c:when test="${recomCnt ne null}">
                                                    <strong style="color:black;">${recomCnt}</strong>
                                                </c:when>
                                                <c:otherwise>
                                                    <strong style="color:black;">0</strong>
                                                </c:otherwise>
                                            </c:choose>
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
                                        url: '<c:url value="/board/${category}/recom"/>',
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
                                                console.log(data.recomCnt);
                                                $("#recom").find("strong").text(data.recomCnt);
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
                                    <c:choose>
                                        <c:when test="${recomCnt ne null}">
                                            <strong style="color:black;">${recomCnt}</strong>
                                        </c:when>
                                        <c:otherwise>
                                            <strong style="color:black;">0</strong>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            <c:if test="${category ne 'notice' && category ne 'news'}">
                <div class="list-body white-bg" id="comntInsertArea">
                    <c:set var="modifyComntCount" value="0"/>
                    <c:if test="${comntList ne null}">
                        <c:forEach var="items" items="${comntList}">
                            <div class="list-box" style='<c:if test="${items.groupLayer > 0}">padding-left:${((items.groupLayer-1)*1.2)+1.5}rem;</c:if>'>
                                <c:choose>
                                    <c:when test="${items.delYn eq 'N'}">
                                    <span>
                                        ${items.cretUserNm}님의 <c:if test="${items.groupLayer > 0}">대</c:if>댓글:&nbsp;
                                    </span>
                                    <div class="row">
                                        <p class="col comntText">
                                            ${items.boardComnt}
                                        </p>
                                        <c:if test="${userLogin ne null}">
                                            <c:if test="${items.cretUser eq userLogin.seq}">
                                                <c:set var="modifyComntCount" value="${modifyComntCount+1}"/> 
                                                <form class="form-box comntModifyArea" style="display:none" method="POST" action="/board/${category}/boardModifyComnt">
                                                    <input type="hidden" name="boardId" value="${boardInfo.boardId}">
                                                    <input type="hidden" name="comntId" value="${items.comntId}">
                                                    <input class="form-control" type="text" name="boardComnt" value="${items.boardComnt}">
                                                    <button type="submit" class="btn btn-outline-success float-end">수정</button>
                                                    <button type="button" class="btn btn-outline-warning ms-2 float-end comntModifyCancle">취소</button>
                                                </form>
                                                <div class="col comntBtnArea">
                                                    <a class="btn btn-sm btn-danger float-end comntDelete" data-id="${items.comntId}">삭제</a>
                                                    <a class="btn btn-sm btn-success float-end comntModify">수정</a>
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </div>
                                    <div class="mb-4">
                                        <fmt:parseDate value="${items.cretDate}" var="dateFmt" pattern="yyyyMMdd"/>
                                        <span><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/></span>
                                        <c:if test="${userLogin ne null}">
                                            <a class="link-dark reComnt" style="cursor:pointer;">답글쓰기</a>
                                            <form class="form-box reComntForm" style="display:none" method="POST" action="/board/${category}/boardComnt">
                                                <input type="hidden" name="boardId" value="${boardInfo.boardId}">
                                                <input type="hidden" name="parntId" value="${items.comntId}">
                                                <input type="hidden" name="originId" value="${items.originId}">
                                                <input type="hidden" name="groupStep" value="${items.groupStep}">
                                                <input type="hidden" name="groupLayer" value="${items.groupLayer}">
                                                <input class="form-control" placeholder="댓글을 남겨보세요." type="text" name="boardComnt" value="">
                                                <button type="submit" class="btn float-end">등록</button>
                                                <button type="button" class="btn ms-2 float-end reComntCancle">취소</button>
                                            </form>
                                        </c:if>
                                    </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="row">
                                            <p class="col comntText">삭제된 댓글 입니다.</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${userLogin ne null}">
                        <script>
                            document.querySelectorAll('.reComnt').forEach((col) =>{
                                col.addEventListener('click', (event) =>{
                                    document.querySelectorAll(".reComntForm").forEach((col)=>{
                                        col.style.display="none";
                                    })
                                event.target.parentNode.querySelectorAll(".reComntForm")[0].style.display="inline";
                                });
                            });
                            document.querySelectorAll('.reComntCancle').forEach((col) =>{
                                col.addEventListener('click', (event) =>{
                                event.target.parentNode.style.display="none";
                                });
                            });
                        </script>
                    </c:if>
                    <c:if test="${modifyComntCount > 0}"> 
                        <script>
                            document.querySelectorAll('.comntModify').forEach((col) =>{
                                col.addEventListener('click', (event) =>{
                                event.target.parentNode.style.display="none";
                                event.target.parentNode.parentNode.querySelectorAll(".comntModifyArea")[0].style.display="inline";
                                event.target.parentNode.parentNode.querySelectorAll(".comntText")[0].style.display="none";
                                });
                            });

                            document.querySelectorAll('.comntModifyCancle').forEach((col) =>{
                                col.addEventListener('click', (event) =>{
                                event.target.parentNode.style.display="none";
                                event.target.parentNode.parentNode.querySelectorAll(".comntText")[0].style.display="inline";
                                event.target.parentNode.parentNode.querySelectorAll(".comntBtnArea")[0].style.display="inline";
                                });
                            });
                        
                            document.querySelectorAll('.comntDelete').forEach((col) =>{
                                col.addEventListener('click', (event) =>{
                                    const boardId = "${boardInfo.boardId}";
                                    let comntId = event.target.dataset.id;
                                    console.log(comntId);
                                    $.ajax({
                                        url: '<c:url value="/board/${category}/deleteComnt"/>',
                                        type: "DELETE",
                                        dataType: "json",
                                        traditional:true,
                                        data: { 
                                                "boardId":boardId,
                                                "comntId":comntId
                                            },
                                        success: response=>{
                                            if(response.isSucceed){
                                                event.target.parentNode.parentNode.parentNode.innerHTML= "<div class='row'><p class='col comntText'>삭제된 댓글 입니다.</p></div>"
                                            }else{
                                                console.log(response.message);
                                            }
                                        },error: e=>{
                                            console.log("Ajax Get Data Error :: ", e);
                                        }
                                    })
                                });
                            });
                        </script> 
                    </c:if>
                </div>
                <c:if test="${userLogin ne null}">
                    <div id="recomWrite" class="mb-2">
                        <form id="recomForm" class="form-box" method="POST" action="/board/${category}/boardComnt">
                            <div>
                                <h4 class=""><strong>댓글쓰기</strong></h4>
                                <input type="text" class="form-control" name="boardComnt">
                            </div>
                            <input type="hidden" name="boardId" value="${boardId}">
                            <div class="recomBtnArea text-end">
                                <button class="btn btn-lg btn-primary">작성</button>
                            </div>
                        </form>
                    </div>
                </c:if>
            </c:if>
            </div>
            <div class="buttonArea text-start">
                <c:choose>
                    <c:when test="${param.page ne null && param.page ne ''}">
                        <a href='<c:url value="/board/${category}/${param.page}?searchOption=${param.searchOption}&searchWord=${param.searchWord}"/>' class="btn btn-outline-primary text-center">목록</a>
                    </c:when>
                    <c:otherwise>
                        <a href='<c:url value="/board/${category}?searchOption=${param.searchOption}&searchWord=${param.searchWord}"/>' class="btn btn-outline-primary text-center">목록</a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${category eq 'community'}">
                        <c:if test="${userLogin.seq eq boardInfo.cretUser}">
                            <a href='<c:url value="/board/${category}/boardModify/${boardId}"/>' class="btn btn-outline-warning text-center">수정</a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${userLogin.seq eq boardInfo.cretUser && userLogin.userGrant eq '0'}">
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