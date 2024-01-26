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
        <div class="grid bg-white dark:bg-gray-800 rounded-md p-4 gap-14">
            <table class="table table-hover table-striped text_center" style="border: 1px solid">
                <thead>
                    <th class="table-info">번호</th>
                    <th class="table-info">제목</th>
                    <th class="table-info">작성자</th>
                    <th class="table-info">작성일시</th>
                </thead>
                <tbody id="tbodyBoardList">
                    <tr>
                        <td>1</td>
                        <td><a class="link-dark" href="<c:url value='/boardDetail/community/1'/>">제목1</a></td>
                        <td>김원효</td>
                        <td>2024.01.01</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td><a class="link-dark" href="<c:url value='/boardDetail/community/2'/>">제목2</a></td>
                        <td>제정권</td>
                        <td>2024.01.07</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td><a class="link-dark" href="<c:url value='/boardDetail/community/3'/>">제목3</a></td>
                        <td>친첨정</td>
                        <td>2024.01.12</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>
                            <a class="link-dark" href="<c:url value='/boardDetail/community/4'/>">제목4</a>
                            <div class="badge bg-danger bg-gradient rounded-pill mb-2">New</div>
                        </td>
                        <td>관공효</td>
                        <td>11:13</td>
                    </tr>
                </tbody>
            </table>
            <a class="btn btn-outline-info float-right" href="#" id="boardWrite">글쓰기</a>
        </div>
        <div>
            <nav aria-label="Page navigation example">
                <ul class="pagination" style="justify-content: center;" id="boardInfoPagination">
                    <c:set var="totalPage" value="${totalContents.totalPages}"/>
                    <c:choose>
                        <c:when test="${totalPage > 10}">
                            <li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>
                            <li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="0">1</a></li>
                            <c:forEach var="cnt" begin="2" end="10" step="1">
                                <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="${cnt-1}">${cnt}</a></li>
                            </c:forEach>
                            <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>
                            <c:forEach var="cnt" begin="1" end="${totalPage}" step="1">
                                <c:choose>
                                    <c:when test="${cnt eq 1}">
                                        <li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="${cnt-1}">${cnt}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="${cnt-1}">${cnt}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${totalPage <= 1}">
                                    <li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>
 var boardPage = (function(){
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
        boardPage.init();
    });
</script>
</html>