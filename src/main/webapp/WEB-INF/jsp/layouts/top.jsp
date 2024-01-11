<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container px-5">
        <div class="navbar-brand"><h2>타이틀</h2></div>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="<c:url value='/main.do'/>">홈</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/about.do'/>">소개</a></li>
                <c:if test="${sessionScope.loginUser ne null}"></c:if>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/user/userMain.do'/>">등록삭제테스트</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/board/board.do'/>">게시판테스트</a></li>
                <c:if test="${sessionScope.loginUser ne null}">
                    <li><a class="btn btn-success" href='<c:url value="/user/userLogout.do"/>'>로그아웃</a></li>
                </c:if>
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