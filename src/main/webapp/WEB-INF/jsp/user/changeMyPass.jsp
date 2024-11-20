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
        <div>
            <form id="updatePassForm" method="post" action="<c:url value='/updateUserPass.do'/>" onsubmit="return validationForm()">
                <table class="table table-bordered">
                    <colgroup>
                        <col width="20%">
                        <col width="80%">
                    </colgroup>
                    <tr>
                        <th><span>현재 패스워드<span style="color:red">*</span> :</span></th>
                        <td><input class="form-control" type="password" name="oldUserPwd" value="" required="true"></td>
                    </tr>
                    <tr>
                        <th><span>새 패스워드<span style="color:red">*</span> :</span></th>
                        <td><input class="form-control" type="password" name="userPwd" value="" required="true"></td>
                    </tr>
                    <tr>
                        <th><span>새 패스워드 확인<span style="color:red">*</span> :</span></th>
                        <td><input class="form-control" type="password" name="pwdCheck" value="" required="true"></td>
                    </tr>
                </table>
                <input type="hidden" name="paging" value="0">
                <div class="button-area text-center mb-2">
                    <button class="btn btn-success" type="submit">수정</button>
                    <a href='<c:url value="/myPage.do"/>' class="btn btn-warning">돌아가기</button></a>
                </div>
            </form>
        <div>
        <input type="hidden" id="errorMsg" value="${errorMsg}">
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>
    var chageMyPassPage = (function(){
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
        chageMyPassPage.init();
    });
    
    function validationForm(){
        $("#modalTitle").empty();
        $("#modalContent").empty();
        const oldUserPwd = $('#updatePassForm input[name="oldUserPwd"]').val(); // 패스워드
        const userPwd = $('#updatePassForm input[name="userPwd"]').val(); // 패스워드
        const pwdCheck = $('#updatePassForm input[name="pwdCheck"]').val(); // 패스워드확인

        let regPwd = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/ //영문 숫자 특수기호 조합 8자리 이상 25자리 이하

        if(oldUserPwd.trim() == "" || oldUserPwd == null){
            $("#modalTitle").append("현재 패스워드 값이 없습니다.");
            $("#modalContent").append("현재 패스워드 값은 필수 값 입니다.");
            $('#updatePassForm input[name="oldUserPwd"]').focus();
            $("#modalInfo").show();
            return false;
        }else if(userPwd.trim() == "" || userPwd == null){
            $("#modalTitle").append("새 패스워드 값이 없습니다.");
            $("#modalContent").append("새 패스워드 값은 필수 값 입니다.");
            $('#updatePassForm input[name="userPwd"]').focus();
            $("#modalInfo").show();
            return false;
        }else if(pwdCheck.trim() == "" || pwdCheck == null){
            $("#modalTitle").append("새 패스워드 확인 값이 없습니다.");
            $("#modalContent").append("새 패스워드 확인 값은 필수 값 입니다.");
            $('#updatePassForm input[name="pwdCheck"]').focus();
            $("#modalInfo").show();
            return false;
        }else if(oldUserPwd === userPwd){
            $("#modalTitle").append("새 패스워드가 현재 값과 같습니다.");
            $("#modalContent").append("새 패스워드와 현재 패스워드의 값이 같습니다.");
            $('#updatePassForm input[name="userPwd"]').focus();
            $("#modalInfo").show();
            return false;
        }

        if(regPwd.test(userPwd) == false){
            $("#modalTitle").append("패스워드 입력값 오류");
            $("#modalContent").append("패스워드는 영문 숫자 특수문자 조합으로 8자리 이상 25자리 이하입니다.<br/>사용 가능한 특수문자 !@#$%^*+=-");
            $('#updatePassForm input[name="userPwd"]').focus();
            $("#modalInfo").show();
            return false;
        }

        if(userPwd != pwdCheck){
            $("#modalTitle").append("패스워드 값 확인");
            $("#modalContent").append("패스워드 값과 확인 값이 같지 않습니다.");
            $('#updatePassForm input[name="userPwd"]').focus();
            $("#modalInfo").show();
            return false;
        }
        return true;
    }
</script>
</html>
