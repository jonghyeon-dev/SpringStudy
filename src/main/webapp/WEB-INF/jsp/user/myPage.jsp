<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            <table class="table table-sm text_center">
                <colgroup>
                    <col width="15%">
                    <col width="35%">
                    <col width="15%">
                    <col width="35%">
                </colgroup>
                <tbody>
                    <tr>
                        <td>ID : </td>
                        <td><c:out value="${userLogin.userId}"/></td>
                        <td>Tel : </td>
                        <td><c:out value="${userLogin.celph}"/></td>
                    </tr>
                    <tr>
                        <td>name : </td>
                        <td><c:out value="${userLogin.userNm}"/></td>
                        <td>Email : </td>
                        <td><c:out value="${userLogin.email}"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <button class="btn btn-sm btn-primary" type="button" onClick="changePassword()">Change Passward</button>
                            <script>
                                function changePassword(){
                                    console.log("Button Change Password is Clicked");
                                }
                            </script>
                        </td>
                        <td></td>
                        <td>
                            <button class="btn btn-sm btn-primary" type="button" onClick="changeMyData()">Change MyData</button>
                            <script>
                                function changeMyData(){
                                    console.log("Button Change MyData is Clicked");
                                }
                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </header>
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
</html>
