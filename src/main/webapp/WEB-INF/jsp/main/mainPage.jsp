<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light">
    <div class="container">
        <div>
            <form id="searchUserForm">
                <span>번호&nbsp;:&nbsp;&nbsp;</span><input type="text" name="seq" value=""><br>
                <span>ID&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;</span><input type="text" name="eno" value=""><br>
            </form>
            <button type="button" id="searchReset">초기화</button>
            <button type="button" id="getUserInfo">검색</button>
        <div>
        <table border='1'>
            <thead>
                <tr>
                    <th>번호</th>
                    <th>ID</th>
                    <th>휴대폰번호</th>
                    <th>E-MAIL</th>
                    <th>생성일자</th>
                    <th>생성시간</th>
                    <th>변경일자</th>
                    <th>변경시간</th>
                </tr>
            </thead>
            <tbody id="tbodyUserInfoList">
                <c:forEach items="${userInfoList}" var="items">
                <tr>
                    <td><c:out value="${items.seq}"/></td>
                    <td><c:out value="${items.eno}"/></td>
                    <td><c:out value="${items.celph}"/></td>
                    <td><c:out value="${items.email}"/></td>
                    <td><c:out value="${items.cretDt}"/></td>
                    <td><c:out value="${items.cretTm}"/></td>
                    <td><c:out value="${items.chgDt}"/></td>
                    <td><c:out value="${items.chgTm}"/></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
<script>
    var mainPage = (function(){
        var init = function(){
            console.log("Document Is Ready");
        };

        var registerEvent = function(){
            $("#searchReset").click(e=>{
                $("#searchUserForm")[0].reset();
            });

            $("#getUserInfo").click(e=>{
                $("#tbodyUserInfoList").empty();
                let seq=$("#searchUserForm input[name='seq']").val();
                let eno=$("#searchUserForm input[name='eno']").val(); 
                $.ajax({
                    url: '<c:url value="getUserInfo.do"/>',
                    type: "GET",
                    dataType: "json",
                    data: {"seq":seq,
                            "eno":eno},
                    success: response=>{
                        if(response.succeed){
                            let data = response.data;
                            let dataText = "";
                            for(i=0;i<data.length;i++){
                                dataText = dataText + "<tr>";
                                dataText = dataText + "<td>"+data[i].seq+"</td>";
                                dataText = dataText + "<td>"+data[i].eno+"</td>";
                                dataText = dataText + "<td>"+data[i].celph+"</td>";
                                dataText = dataText + "<td>"+data[i].email+"</td>";
                                dataText = dataText + "<td>"+data[i].cretDt+"</td>";
                                dataText = dataText + "<td>"+data[i].cretTm+"</td>";
                                dataText = dataText + "<td>"+data[i].chgDt+"</td>";
                                dataText = dataText + "<td>"+data[i].chgTm+"</td>";
                                dataText = dataText + "<tr>";
                            }
                            if(dataText != ""){
                                $("#tbodyUserInfoList").append(dataText);
                            }
                        }
                    },
                    error: e=>{
                        console.log("Ajax Get Data Error :: ", e);
                    }
                })
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
        mainPage.init();
    });
</script>
</html>
