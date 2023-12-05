<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="nav nav-black"> 
    <div class="headTitle"><h2>테스트</h2></div>
    <div class="float-right">
        <ul class="navBtn">
            <li><a href="<c:url value='/main.do'/>">홈</a></li>
            <li><a href="#">테스트</a></li>
            <li><a class="btn btn-success" href='<c:url value="/userLogout.do"/>'>로그아웃</a></li>
        </ul>
    </div>
</div>