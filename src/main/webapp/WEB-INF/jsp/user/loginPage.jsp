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
            <form id="CheckUserForm" method="post" action="<c:url value='/checkUser.do'/>">
                <table class="table table-bordered">
                    <colgroup>
                        <col width="20%">
                        <col width="30%">
                        <col width="20%">
                        <col width="30%">
                    </colgroup>
                    <tr>
                        <th><span>ID :</span></th>
                        <td><input type="text" name="userId" value="" required="true"></td>
                        <th><span>PW :</span></th>
                        <td><input type="password" name="userPw" value="" required="true"></td>
                    </tr>
                </table>
                <c:if test="${!empty errorMsg}">
                    <span style="color:red">${errorMsg}</span><br/>
                </c:if>
                <button class="btn btn-primary" type="submit">로그인</button>
                <a class="btn btn-primary" href="/createAccount.do">회원가입</a>
            </form>
        <div>
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>
    var loginPage = (function(){
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
        loginPage.init();
    });
</script>
</html>
