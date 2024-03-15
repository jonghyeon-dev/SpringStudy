<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>게시판</title>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light">
    <main class="container">
        <header>
            <%@include file="../layouts/top.jsp"%> 
        </header>
        <section class="grid bg-white dark:bg-gray-800 rounded-md p-4 gap-14">
            <table class="table table-sm table-hover text_center">
                <colgroup>
                    <col width="10%">
                    <col width="50%">
                    <col width="15%">
                    <col width="15%">
                    <col width="5%">
                    <col width="5%">
                </colgroup>
                <thead class="text-center">
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일시</th>
                    <th>조회수</th>
                    <th>추천수</th>
                </thead>
                <tbody id="tbodyBoardList">
                    <c:forEach items="${totalContents.boardList}" var="items">
                        <tr>
                            <td class="text-center"><c:out value="${items.boardId}"/></td>
                            <td>
                                <a class="link-dark" href="<c:url value='/board/${category}/detail/${items.boardId}?page=${page}&searchOption=${searchOption}&searchWord=${searchWord}'/>">
                                    <c:out value="${items.boardTitle}"/>
                                    <c:if test="${items.asNew eq 1}">
                                        <div class="badge bg-danger bg-gradient rounded-pill ms-2 mb-2">News</div>
                                    </c:if> 
                                </a>
                            </td>
                            <td><c:out value="${items.cretUserNm}"/></td>
                            <fmt:parseDate value="${items.cretDate}" var="dateFmt" pattern="yyyyMMdd"/>
                            <td class="text-center"><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/></td>
                            <td class="text-center">${items.viewCnt}</td>
                            <td class="text-center">${items.likeChuCnt}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${fn:length(totalContents.boardList) < 10}">
                        <c:forEach var="i" begin="${fn:length(totalContents.boardList)}" end="9" step="1">
                            <tr>
                                <td class="text-center">&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td class="text-center">&nbsp;</td>
                                <td class="text-center">&nbsp;</td>
                                <td class="text-center">&nbsp;</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <form method="GET" action="<c:url value='/board/${category}'/>">
                <div class="row g-3 align-items-center d-flex justify-content-center">
                    <div class="col-auto">
                        <select class="form-select mb-2" name="searchOption">
                            <option value="title" selected>제목</option>
                            <option value="content">내용</option>
                            <option value="titCont">제목+내용</option>
                            <option value="user">글쓴이</option>
                        </select>
                    </div>
                    <div class="col-auto">
                        <input class="form-control mb-2" name="searchWord">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary mb-2">검색</button>
                    </div>
                </div>
            </form>
            <c:choose>
                <c:when test="${category eq 'community'}">
                    <c:if test="${userLogin ne null}">
                        <a class="btn btn-outline-info flot-right" href="<c:url value='/board/${category}/boardWrite'/>">글쓰기</a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${userLogin ne null && userLogin.userGrant eq '0'}">
                        <a class="btn btn-outline-info flot-right" href="<c:url value='/board/${category}/boardWrite'/>">글쓰기</a>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </seciton>
            <nav aria-label="Page navigation example">
                <ul class="pagination" style="justify-content: center;" id="boardInfoPagination">
                    <c:choose>
                        <c:when test="${totalContents.pageInfo.firstPage} > 0">
                            <li class="page-item">
                                <a class="page-link" href="<c:url value='/board/${category}/1?searchOption=${param.searchOption}&searchWord=${param.searchWord}'/>">&laquo;</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link">&laquo;</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${totalContents.pageInfo.prevPage > 0}">
                            <li class="page-item">
                                <a class="page-link" href="<c:url value='/board/${category}/${totalContents.pageInfo.prevPage}?searchOption=${param.searchOption}&searchWord=${param.searchWord}'/>">&lt;</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link">&lt;</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="idx" begin="${totalContents.pageInfo.startPage}" end="${totalContents.pageInfo.endPage}" step="1">
                        <c:choose>
                            <c:when test="${totalContents.pageInfo.currentPage eq idx}">
                                <li class="page-item active"><a class="page-link" href='<c:url value="/board/${category}/${idx}?searchOption=${param.searchOption}&searchWord=${param.searchWord}"/>'>${idx}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link" href='<c:url value="/board/${category}/${idx}?searchOption=${param.searchOption}&searchWord=${param.searchWord}"/>'>${idx}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${totalContents.pageInfo.nextPage > 0}">
                            <li class="page-item">
                                <a class="page-link" href="<c:url value='/board/${category}/${totalContents.pageInfo.nextPage}?searchOption=${param.searchOption}&searchWord=${param.searchWord}'/>">&gt;</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link">&gt;</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${totalContents.pageInfo.lastPage > 0}">
                            <li class="page-item">
                                <a class="page-link" href="<c:url value='/board/${category}/${totalContents.pageInfo.lastPage}?searchOption=${param.searchOption}&searchWord=${param.searchWord}'/>">&raquo;</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled">
                                <a class="page-link">&raquo;</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        <footer>
            <%@include file="../layouts/bottom.jsp"%>
        </footer>
    </main>
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
