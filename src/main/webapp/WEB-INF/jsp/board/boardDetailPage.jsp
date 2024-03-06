<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
                </div>
            </div>
            <div class="buttonArea text-right">
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
                        <c:if test="${userLogin.userId eq boardInfo.cretUser || adminLogin.eno eq boardInfo.cretUser}">
                            <a href='<c:url value="/board/${category}/boardModify/${boardId}"/>' class="btn btn-outline-warning text-center">수정</a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${adminLogin.eno eq boardInfo.cretUser}">
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