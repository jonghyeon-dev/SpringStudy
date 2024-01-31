<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container px-5">
        <a class="navbar-brand" href="<c:url value='/main.do'/>">타이틀</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="<c:url value='/main.do'/>">홈</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/about.do'/>">소개</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/thymeleafTest'/>">타임리프테스트</a></li>
                <!-- <li class="nav-item"><a class="nav-link" href="<c:url value='/board/board.do?category=notice'/>">공지사항</a></li> -->
                <!-- <li class="nav-item"><a class="nav-link" href="<c:url value='/board/board.do?category=news'/>">소식</a></li> -->
                <!-- <li class="nav-item"><a class="nav-link" href="<c:url value='/board/board.do?cetegory=comunnity'/>">게시판테스트</a></li> -->
                <c:if test="${adminLogin ne null}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/adminAccountMain.do'/>">관리자목록</a></li>
                    <!-- <li class-"nav-item"><a class="nav-link" href="<c:url value='/user/userAccountMain.do'/>">사용자목록</a></li> --> 
                </c:if>
                <c:choose>
                    <c:when test="${adminLogin ne null || userLogin ne null}">
                        <li class="nav-item text-light">
                            <c:if test="${adminLogin ne null}">
                                ${adminLogin.name}님 환영합니다.
                            </c:if>
                            <c:if test="${userLogin ne null}">
                                ${userLogin.userNm}님 환영합니다.
                            </c:if>
                            <a class="btn btn-success" href='<c:url value="/logout.do"/>'>로그아웃</a>
                        </li>
                    </c:when>
                    <c:otherwise><li><a class="btn btn-success" href='<c:url value="/login.do"/>'>로그인</a></li></c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<div id="modalInfo" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="documemt">
        <div class="modal-content">
            <div class="modal-header">
                <h2 class="modal-title" id="modalTitle"></h2>
                <button type="button" class="btn-close" onclick="javascript:closeModal()">
                    <span aria-hidden="true"></span>
                </button>
            </div>
            <div class="modal-body">
                <p class="text-center" id="modalContent"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="javascript:closeModal()">닫기</button>
            </div>
        </div>
    </div>
</div>

<script>
    function closeModal(){
        $("#modalTitle").empty();
        $("#modalContent").empty();
        $("#modalInfo").hide();
    }
</script>