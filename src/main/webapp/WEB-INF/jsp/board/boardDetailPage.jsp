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
            <a class="btn btn-outline-info float-right" href="#" id="boardWrite">글쓰기</a>
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