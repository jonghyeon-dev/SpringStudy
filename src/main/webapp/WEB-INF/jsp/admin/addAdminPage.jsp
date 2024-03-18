<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>관리자등록</title>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light">
    <div class="container">
        <%@include file="../layouts/top.jsp"%> 
        <div>
            <form id="addAdminForm" method="post" action="<c:url value='/admin/insertAdmin.do'/>" onsubmit="return validationForm()">
                <table class="table table-bordered">
                    <colgroup>
                        <col width="20%">
                        <col width="30%">
                        <col width="20%">
                        <col width="30%">
                    </colgroup>
                    <tr>
                        <th><span>ID<span style="color:red">*</span> :</span></th>
                        <td>
                            <input class="form-control" type="text" name="userId" value="" required="true">
                            <button class="btn btn-primary" type="button" name="checkDuplication" data-toggle="modal" data-target="#modalInfo">중복확인</button>
                            <input type="hidden" name="checkDup" value="" required="true">
                        </td>
                        <th><span>이름<span style="color:red">*</span> :</span></th>
                        <td><input class="form-control" type="text" name="userNm" value="" required="true"></td>
                    </tr>
                    <tr>
                        <th><span>패스워드<span style="color:red">*</span> :</span></th>
                        <td><input class="form-control" type="password" name="userPwd" value="" required="true"></td>
                        <th><span>패스워드확인<span style="color:red">*</span> :</span></th>
                        <td><input class="form-control" type="password" name="userPwdCheck" value="" required="true"></td>
                    </tr>
                    <tr>
                        <th><span>휴대폰번호 :</span></th>
                        <td><input class="form-control" type="number" name="celph" value=""></td>
                        <th><span>이메일 :</span></th>
                        <td><input class="form-control" type="email" name="email" value=""></td>
                    </tr>
                </table>
                <c:if test="${!empty errorMsg}">
                    <span style="color:red">${errorMsg}</span>
                </c:if>
                <input type="hidden" name="paging" value="0">
                <div class="button-area text-center mb-2">
                    <button class="btn btn-primary" type="button" id="reset">초기화</button>
                    <a href='<c:url value="/admin/adminAccountMain.do"/>' class="btn btn-warning">돌아가기</button></a>
                    <button class="btn btn-success" type="submit">등록</button>
                </div>
            </form>
        <div>
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>
    var joinPage = (function(){
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

        checkDuplication = function(){
                let userId = $("#addAdminForm input[name='userId']").val();
                $.ajax({url:"<c:url value='/checkUserDup.do'/>",
                        type:"POST",
                        dataType: "json",
                        traditional: true,
                        data: {"userId":userId},
                        success : function(result){
                            $("#modalTitle").empty();
                            $("#modalContent").empty();
                            if(result.isSucceed){
                                $("#modalTitle").append("중복체크");
                                $("#modalContent").append("사용할 수 있는 ID입니다.");
                                $("#modalInfo").show();
                                $("#addAdminForm input[name='checkDup']").val(true);
                            }else{
                                $("#modalTitle").append("중복체크");
                                $("#modalContent").append("중복되는 ID가 존재합니다.");
                                $("#modalInfo").show();
                                $("#addAdminForm input[name='checkDup']").val(false);
                            }
                        },
                        error : function(request, status, error){
                            console.log(status)
                            console.log(error)
                        }
            
                });
            }
            
        var registerEvent = function(){
            $("#reset").click(function(){
                $("#addAdminForm")[0].reset();
            });

            //모달창 오픈
            $("#addAdminForm button[name='checkDuplication']").click(function(){
                checkDuplication();
            })

            $("#addAdminForm input[name='userId']").keypress(function(e){
                $("#addUserForm input[name='checkDup']").val(false);
            });
        };

        return {
            init: function(){
                init();
                registerEvent();
            }
        }
    }());

    $(document).ready(e=>{
        joinPage.init();
    });
    
    function validationForm(){
        const userId = $('#addAdminForm input[name="userId"]').val(); // 아이디
        const userPwd = $('#addAdminForm input[name="userPwd"]').val(); // 패스워드
        const userPwdCheck = $('#addAdminForm input[name="userPwdCheck"]').val(); // 패스워드
        const userNm = $('#addAdminForm input[name="userNm"]').val(); // 이름
        const cehckDup = $('#addAdminForm input[name="checkDup"]').val(); // 중복체크 여부
        if(userId.trim() == "" || userId == null){
            $("#modalTitle").append("ID값이 없습니다.");
            $("#modalContent").append("ID값은 필수 값 입니다.");
            $('#addAdminForm input[name="userId"]').focus();
            return false;
        }else if(userPwd.trim() == "" || userPwd == null){
            $("#modalTitle").append("패스워드 값이 없습니다.");
            $("#modalContent").append("패스워드 값은 필수 값 입니다.");
            $('#addAdminForm input[name="userPwd"]').focus();
            return false;
        }else if(userPwdCheck.trim() == "" || userPwdCheck == null){
            $("#modalTitle").append("패스워드 확인 값이 없습니다.");
            $("#modalContent").append("패스워드 확인 값은 필수 값 입니다.");
            $('#addAdminForm input[name="userPwdCheck"]').focus();
            return false;
        }else if(userNm.trim() == "" || userNm == null){
            $("#modalTitle").append("이름 값이 없습니다.");
            $("#modalContent").append("이름 값은 필수 값 입니다.");
            $('#addAdminForm input[name="userNm"]').focus();
            return false;
        }else if(cehckDup != "true"){
            $("#modalTitle").append("중복체크가 완료 되지 않았습니다.");
            $("#modalContent").append("중복체크 버튼을 클릭하여 아이디가 중복되었는지 확인 부탁드립니다.");
            $('#addAdminForm input[name="userId"]').focus();
            return false;
        }

        if(userPwd != userPwdCheck){
            $("#modalTitle").append("패스워드 값 확인");
            $("#modalContent").append("패스워드 값과 확인 값이 같지 않습니다.");
            $('#addUserForm input[name="userPw"]').focus();
            $("#modalInfo").show();
            return false;
        }

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

