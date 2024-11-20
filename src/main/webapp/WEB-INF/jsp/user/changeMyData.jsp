<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>내정보변경</title>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light">
    <div class="container">
        <%@include file="../layouts/top.jsp"%> 
        <form id="updateUserForm" method="post" action="<c:url value='/updateUser.do'/>" onsubmit="return validationForm()">
            <table class="table table-bordered">
                <colgroup>
                    <col width="20%">
                    <col width="30%">
                    <col width="20%">
                    <col width="30%">
                </colgroup>
                <tr>
                    <th><span>ID : </span></th>
                    <td>
                        <span id="userId">${userLogin.userId}</span>
                    </td>
                    <th><span>닉네임<span style="color:red">*</span> :</span></th>
                    <td><input class="form-control" type="text" name="userNm" value="${userLogin.userNm}" required="true"></td>
                </tr>
                <tr>
                    <th><span>휴대폰번호 :</span></th>
                    <!-- pattern="01([0|1|6|7|8|9])-?[0-9]{3,4}-?[0-9]{4}" -->
                    <td><input class="form-control" type="tel" name="celph" value="${userLogin.celph}"></td>
                    <th><span>이메일 :</span></th>
                    <td><input class="form-control" type="email" name="email" value="${userLogin.email}"></td>
                </tr>
            </table>
            <input type="hidden" name="paging" value="0">
            <div class="button-area text-center mb-2">
                <button class="btn btn-success" type="submit">수정</button>
                <a href='<c:url value="/myPage.do"/>' class="btn btn-warning">돌아가기</button></a>
            </div>
        </form>
        <input type="hidden" id="errorMsg" value="${errorMsg}">
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>
    var changeMyDataPage = (function(){
        var init = function(){
            const errorMsg = $("#errorMsg").val()
            $("#modalTitle").empty();
            $("#modalContent").empty();
            if(errorMsg != null && errorMsg != ""){
                $("#modalTitle").append("Error");
                $("#modalContent").append(errorMsg);
                $("#modalInfo").show();
            }
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
        changeMyDataPage.init();
    });
    
    function validationForm(){
        $("#modalTitle").empty();
        $("#modalContent").empty();
        
        const regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/ // 한국 휴대폰 번호 정규식
        let celph = $('#addUserForm input[name="celph"]').val();
        if(celph != null && celph.trim() != ""){
            if(regPhone.test(celph) == false){
                $("#modalTitle").append("후대폰번호 입력값 오류");
                $("#modalContent").append("휴대폰번호를 형식에 맞게 정확히 입력해 주세요.<br>예:010-123-1234 혹은 010-1234-1234");
                $('#addUserForm input[name="eno"]').focus();
                $("#modalInfo").show();
                return false;
            }
            celph = celph.replace(/-/gi, "");// celph 안에 하이픈 전체 공백으로 치환
        }
        $('#addUserForm input[name="celph"]').val(celph.trim());

        return true;
    }
</script>
</html>
