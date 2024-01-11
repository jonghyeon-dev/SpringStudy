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
                            <input class="form-control" type="text" name="eno" value="" required="true">
                        </td>
                        <td>
                            <button class="btn btn-primary" type="button" name="checkDuplication" data-toggle="modal" data-target="#modalInfo">중복확인</button>
                            <input type="hidden" name="checkDup" value="" required="true">
                            <span></span>
                        </td>
                    </tr>
                    <tr>
                        <th><span>PW :</span></th>
                        <td><input class="form-control" type="password" name="enoPw" value="" required="true"></td>
                        <th><span>이름 :</span></th>
                        <td><input class="form-control" type="text" name="name" value="" required="true"></td>
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
                    <a href='<c:url value="/userMain.do"/>' class="btn btn-warning">돌아가기</button></a>
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

        };

        var registerEvent = function(){
            $("#reset").click(function(){
                $("#adduserForm")[0].reset();
            });

            //모달창 오픈
            $("#addUserForm button[name='checkDuplication']").click(function(){
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
        const eno = $('#addUserForm input[name="eno"]').val(); // 아이디
        const enoPw = $('#addUserForm input[name="enoPw"]').val(); // 패스워드
        const name = $('#addUserForm input[name="name"]').val(); // 이름
        const cehckDup = $('#addUserForm input[name="checkDup"]').val(); // 중복체크 여부
        console.log("실행?");
        if(eno.trim() == "" || eno == null){
            $("#modalTitle").append("ID값이 없습니다.");
            $("#modalContent").append("ID값은 필수 값 입니다.");
            $('#addUserForm input[name="eno"]').focus();
            return false;
        }else if(enoPw.trim() == "" || enoPw == null){
            $("#modalTitle").append("패스워드 값이 없습니다.");
            $("#modalContent").append("패스워드 값은 필수 값 입니다.");
            $('#addUserForm input[name="enoPw"]').focus();
            return false;
        }else if(name.trim() == "" || name == null){
            $("#modalTitle").append("이름 값이 없습니다.");
            $("#modalContent").append("이름 값은 필수 값 입니다.");
            $('#addUserForm input[name="name"]').focus();
            return false;
        }else if(cehckDup != "true"){
            $("#modalTitle").append("중복체크가 되지 않았습니다.");
            $("#modalContent").append("중복체크 버튼을 클릭하여 아이디가 중복되었는지 확인 부탁드립니다.");
            $('#addUserForm input[name="eno"]').focus();
            return false;
        }

        return true;
    }
</script>
</html>
