<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>내 정보</title>
    <%@include file="../layouts/header.jsp"%>
</head>
<body>
    <div class="container">
        <%@include file="../layouts/top.jsp"%>
        <header>
            <table class="table table-sm table-bordered table-hover text_center my-3">
                <colgroup>
                    <col width="25%">
                    <col width="25%">
                    <col width="25%">
                    <col width="25%">
                </colgroup>
                <thead class="table-info">
                    <tr class="text-center">
                        <th scope="col">ID</th>
                        <th scope="col">이름</th>
                        <th scope="col">전화번호</th>
                        <th scope="col">메일주소</th>
                    </tr>
                </thead>
                <tbody class="text-center">
                    <tr>
                        <td><c:out value="${userLogin.userId}"/></td>
                        <td><c:out value="${userLogin.userNm}"/></td>
                        <c:choose>
                            <c:when test="${fn:length(userLogin.celph) eq 10}">
                                <fmt:formatNumber var="celPhNo" value="${userLogin.celph}" pattern="###,###,####"/>
                                <td>0<c:out value='${fn:replace(celPhNo,",","-")}'/></td>
                            </c:when>
                            <c:otherwise>
                                <fmt:formatNumber var="celPhNo" value="${userLogin.celph}" pattern="###,####,####"/>
                                <td>0<c:out value='${fn:replace(celPhNo,",","-")}'/></td>
                            </c:otherwise>
                        </c:choose>
                        <td><c:out value="${userLogin.email}"/></td>
                    </tr>
                </tbody>
            </table>
        </header>
        <div class="buttonArea d-flex justify-content-center mb-2">
            <a href='<c:out value="/changeMyData.do"/>' class="btn btn-primary text-center">내정보수정</a>
            <a href='<c:out value="/changeMyPass.do"/>' class="btn btn-primary text-center ms-2">비밀번호변경</a>
        </div>
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
</html>
