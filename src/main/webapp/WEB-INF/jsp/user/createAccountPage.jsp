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
            <form id="addUserForm" method="post" action="<c:url value='/insertUser.do'/>" onsubmit="return validationForm()">
                <table class="table table-bordered">
                    <colgroup>
                        <col width="20%">
                        <col width="30%">
                        <col width="20%">
                        <col width="30%">
                    </colgroup>
                    <tr>
                        <th><span>ID :</span></th>
                        <td colspan="2">
                            <input class="form-control" type="text" name="userId" value="" required="true">
                        </td>
                        <td>
                            <button class="btn btn-primary" type="button" name="checkDuplication" data-toggle="modal" data-target="#modalInfo">중복확인</button>
                            <input type="hidden" name="checkDup" value="" required="true">
                        </td>
                    </tr>
                    <tr>
                        <th><span>PW :</span></th>
                        <td><input class="form-control" type="password" name="userPw" value="" required="true"></td>
                        <th><span>닉네임 :</span></th>
                        <td><input class="form-control" type="text" name="userNm" value="" required="true"></td>
                    </tr>
                    <tr>
                        <th><span>휴대폰번호 :</span></th>
                        <td><input class="form-control" type="tel" name="celph" pattern="^01([0|1|6|7|8|9])-?[0-9]{3,4}-?[0-9]{4}" value=""></td>
                        <th><span>이메일 :</span></th>
                        <td><input class="form-control" type="email" name="email" value=""></td>
                    </tr>
                </table>
                <input type="hidden" name="paging" value="0">
                <div class="button-area text-center mb-2">
                    <button class="btn btn-primary" type="button" id="reset">초기화</button>
                    <a href='<c:url value="/login.do"/>' class="btn btn-warning">돌아가기</button></a>
                    <button class="btn btn-success" type="submit">등록</button>
                </div>
            </form>
        <div>
        <input type="hidden" id="errorMsg" value="${errorMsg}">
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>
    var joinPage = (function(){
        var init = function(){
            const errorMsg = $("#errorMsg").val()
            if(errorMsg != null && errorMsg != ""){
                $("#modalTitle").append("Error");
                $("#modalContent").append(errorMsg);
                $("#modalInfo").show();
            }
        };

        checkDuplication = function(){
            const userId = $("#addUserForm input[name='userId']").val();
            $.ajax({url:"/checkUserDuplication",
                    type:"GET",
                    async:true,
                    headers :{
                        "Content-Type" : "application/json",
                        "X-HTTP-Method-Override" : "GET"
                    },
                    dataType:"json",
                    data: JSON.Stringify({
                        "userId":userId
                    }),
                    success : function(result){
                        console.log(result)
                    },
                    error : function(request, status, error){
                        console.log(status)
                        console.log(error)
                    }
        
            })

        }

        var registerEvent = function(){
            $("#reset").click(function(){
                $("#addUserForm")[0].reset();
            });

            //모달창 오픈
            $("#addUserForm button[name='checkDuplication']").click(function(){
                checkDuplication()
                $("#modalTitle").append("중복체크");
                $("#modalContent").append("중복체크 완료");
                $("#modalInfo").show();
            })

            $("#addUserForm input[name='checkDup']").keypress(function(e){
                this.empty();
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
        const userId = $('#addUserForm input[name="userId"]').val(); // 아이디
        const userPw = $('#addUserForm input[name="userPw"]').val(); // 패스워드
        const userNm = $('#addUserForm input[name="userNm"]').val(); // 이름
        const checkDup = $('#addAdminForm input[name="checkDup"]').val(); // 중복체크 여부
        // const phone = '01012345678';
        // phone
        // .replace(/[^0-9]/g, '') // 숫자를 제외한 모든 문자 제거 g: 전역검색, i: 대소문자 구분없이, m: 여러줄 검색
        // .replace(/^(\d{3})(\d{3,4})(\d{4})$/, `$1-$2-$3`); // 하이픈 추가

        console.log("실행?");
        if(userId.trim() == "" || userId == null){
            $("#modalTitle").append("ID값이 없습니다.");
            $("#modalContent").append("ID값은 필수 값 입니다.");
            $('#addUserForm input[name="userId"]').focus();
            return false;
        }else if(userPw.trim() == "" || userPw == null){
            $("#modalTitle").append("패스워드 값이 없습니다.");
            $("#modalContent").append("패스워드 값은 필수 값 입니다.");
            $('#addUserForm input[name="userPw"]').focus();
            return false;
        }else if(userNm.trim() == "" || userNm == null){
            $("#modalTitle").append("닉네임 값이 없습니다.");
            $("#modalContent").append("닉네임 값은 필수 값 입니다.");
            $('#addUserForm input[name="userNm"]').focus();
            return false;
        }else if(checkDup != "true"){
            $("#modalTitle").append("중복체크가 되지 않았습니다.");
            $("#modalContent").append("중복체크 버튼을 클릭하여 아이디가 중복되었는지 확인 부탁드립니다.");
            $('#addUserForm input[name="userId"]').focus();
            return false;
        }

        const regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/ // 한국 휴대폰 번호 정규식
        let celph = $('#addUserForm input[name="celph"]').val();
        if(celph != null && celph.trim() != ""){
            if(regPhone.test(celph) == false){
                $("#modalTitle").append("후대폰번호 입력값 오류");
                $("#modalContent").append("휴대폰번호를 형식에 맞게 정확히 입력해 주세요.<br>예:010-123-1234 혹은 010-1234-1234");
                $('#addUserForm input[name="eno"]').focus();
                return false;
            }
            celph = celph.replace(/-/gi, "");// celph 안에 하이픈 전체 공백으로 치환
        }
        $('#addUserForm input[name="celph"]').val(celph.trim());

        return true;
    }
</script>
</html>
