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
            <form class="searchForm" id="searchUserForm">
                <table class="table table-bordered searchTable text-center">
                    <colgroup>
                        <col width="15%">
                        <col width="25%">
                        <col width="15%">
                        <col width="25%">
                        <col width="20%">
                    </colgroup>
                    <tr>
                        <th class="table-success pt-3"><span class="form-label">번호 :</span></th>
                        <td><input class="form-control" onKeypress="return enterBtnClick(event,'getUserInfo')"  type="number" name="seq" value=""></td>
                        <th class="table-success pt-3"><span class="form-label">ID :</span></th>
                        <td><input class="form-control" onKeypress="return enterBtnClick(event,'getUserInfo')" type="text" name="eno" value=""></td>
                        <td>
                            <button class="btn btn-primary searchBtn" type="button" id="getUserInfo">검색</button>
                            <button class="btn btn-primary searchReset" type="button" id="searchReset">초기화</button>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="paging" value="0">
                <div style="float:right;">
                    <a class="btn btn-success" href='<c:url value="/addUser.do"/>'>등록하기</a>
                    <button class="btn btn-danger" type="button" id="deleteUser">삭제하기</button>
                </div>
            </form>
        <div>
            <table class="table table-bordered table-hover">
                <thead>
                    <tr class="text-center">
                        <th class="table-info"><input type="checkBox" id="checkAll"></th>
                        <th class="table-info">번호</th>
                        <th class="table-info">ID</th>
                        <th class="table-info">이름</th>
                        <th class="table-info">휴대폰번호</th>
                        <th class="table-info">E-MAIL</th>
                        <th class="table-info">생성일자</th>
                        <th class="table-info">생성시간</th>
                        <th class="table-info">변경일자</th>
                        <th class="table-info">변경시간</th>
                    </tr>
                </thead>
                <tbody id="tbodyUserInfoList">
                    <c:forEach items="${totalContents.userInfoList}" var="items">
                    <tr>
                        <td class='delCheck'><input type="checkbox" name="delCheck" value="${items.seq}"></td>
                        <td><c:out value="${items.seq}"/></td>
                        <td><c:out value="${items.eno}"/></td>
                        <td><c:out value="${items.name}"/></td>
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
        <div>
            <nav aria-label="Page navigation example">
                <ul class="pagination" style="justify-content: center;" id="userInfoPagination">
                    <c:set var="totalPage" value="${totalContents.totalPages}"/>
                    <c:choose>
                        <c:when test="${totalPage > 10}">
                            <li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>
                            <li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="0">1</a></li>
                            <c:forEach var="cnt" begin="2" end="10" step="1">
                                <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="${cnt-1}">${cnt}</a></li>
                            </c:forEach>
                            <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>
                            <c:forEach var="cnt" begin="1" end="${totalPage}" step="1">
                                <c:choose>
                                    <c:when test="${cnt eq 1}">
                                        <li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="${cnt-1}">${cnt}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="${cnt-1}">${cnt}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${totalPage eq 1}">
                                    <li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </div> 
        <%@include file="../layouts/bottom.jsp"%>
    </div>
</body>
<script>
    var mainPage = (function(){
        var init = function(){

        };
        //사용자 데이터 출력
        var searchUserInfo = function(seq,eno,paging){
            $.ajax({
                    url: '<c:url value="getUserInfo.do"/>',
                    type: "GET",
                    dataType: "json",
                    traditional:true,
                    data: {"seq":seq,
                            "eno":eno,
                            "page":paging},
                    success: response=>{
                        if(response.succeed){
                            $("#checkAll").prop('checked',false);
                            let data = response.data.userInfoList;
                            let dataText = "";
                            for(i=0;i<data.length;i++){
                                dataText = dataText + "<tr>";
                                dataText = dataText + "<td class='delCheck'><input type='checkbox' name='delCheck' value="+data[i].seq+"></td>";
                                dataText = dataText + "<td>"+data[i].seq+"</td>";
                                dataText = dataText + "<td>"+data[i].eno+"</td>";
                                dataText = dataText + "<td>"+data[i].name+"</td>";
                                dataText = dataText + "<td>"+data[i].celph+"</td>";
                                dataText = dataText + "<td>"+data[i].email+"</td>";
                                dataText = dataText + "<td>"+data[i].cretDt+"</td>";
                                dataText = dataText + "<td>"+data[i].cretTm+"</td>";
                                dataText = dataText + "<td>"+data[i].chgDt+"</td>";
                                dataText = dataText + "<td>"+data[i].chgTm+"</td>";
                                dataText = dataText + "<tr>";
                            }
                            if(data.length<10){
                                for(i=0;i<(10 - data.length);i++){
                                    dataText = dataText + "<tr>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<td>&nbsp;</td>";
                                    dataText = dataText + "<tr>";
                                }
                            }
                            $("#tbodyUserInfoList").empty();
                            if(dataText != ""){
                                $("#tbodyUserInfoList").append(dataText);
                            }

                            // paging 처리
                            let totalPages = response.data.totalPages;
                            let pageText = "";
                            if(Number(paging)>0){
                                pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>'
                            }else{
                                pageText = pageText + '<li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>'
                            }
                            if(totalPages > 10){
                                if(Number(paging)<5){
                                    for(page=0;page<10;page++){
                                        if(Number(paging)===page){
                                            pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                        }else{
                                            pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                        }
                                    }
                                }else if(Number(paging) >= (Number(totalPages) - 5)){
                                    for(page=(Number(totalPages) - 10);page<Number(totalPages);page++){
                                        if(Number(paging)===page){
                                            pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                        }else{
                                            pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                        }
                                    }
                                }else{
                                    for(page=Number(paging)-5;page<(5+Number(paging));page++){
                                        if(Number(paging)===page){
                                            pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                        }else{
                                            pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                        }
                                    }
                                }
                            }else{
                                for(page=0;page<Number(totalPages);page++){
                                        if(Number(paging)===page){
                                            pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                        }else{
                                            pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                        }
                                    }
                            }
                            if(Number(paging) < (Number(totalPages)-1)){
                                pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>'
                            }else{
                                pageText = pageText + '<li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>'
                            }
                            $("#userInfoPagination").empty();
                            if(pageText != null){
                                $("#userInfoPagination").append(pageText);
                                $("#userInfoPagination li:not(.disabled.active)").on("click",function(){
                                    let selectedPage=$(this).find("input[name='page']").val();
                                    let oldData;
                                    if(selectedPage === "prev"){
                                        $("#userInfoPagination li").each(function(){
                                            let pageData = $(this).find("input[name='page']").val();
                                            if(oldData === "prev"){
                                                if(Number(pageData) === 0){
                                                    selectedPage = pageData;
                                                }else{
                                                    selectedPage = Number(pageData)-1;
                                                }
                                                return false;
                                            }else{
                                                oldData = pageData;
                                            }
                                        });
                                    }else if(selectedPage === "next"){
                                        $("#userInfoPagination li").each(function(){
                                            let pageData = $(this).find("input[name='page']").val();
                                            if(pageData === "next"){
                                                if(Number(oldData) === (Number(totalPages)-1)){
                                                    selectedPage = Number(oldData);
                                                }else{
                                                    selectedPage = Number(oldData)+1;
                                                }
                                                return false;
                                            }else{
                                                oldData = pageData;
                                            }
                                        });
                                    }
                                    searchUserInfo(seq,eno,selectedPage);
                                })
                            }
                        }
                    },
                    error: e=>{
                        console.log("Get UserInfoList Ajax Get Data Error :: ", e);
                    }
                })
        };

        //ENO 데이터 삭제
        var deleteUser = function(delList){
            console.log("deleteUser Start :: " + delList);
            $.ajax({
                url:"<c:url value='deleteUserInfo.do'/>",
                type:"POST",
                dataType:"json",
                traditional:true,
                data:{"delList": delList},
                // data:{"delListData": JSON.stringify(delList)},
                success: response=>{
                    let seq=$("#searchUserForm input[name='seq']").val();
                    let eno=$("#searchUserForm input[name='eno']").val();
                    searchUserInfo(seq,eno,0);
                    $("#checkAll").prop('checked',false);
                },
                error: e=>{
                    console.log("Delete User Ajax Get Data Error :: ", e);
                }
            })
        }

        var registerEvent = function(){
            //초기화 버튼 클릭 시
            $("#searchReset").click(e=>{
                $("#searchUserForm")[0].reset();
                $("#getUserInfo").trigger('click');
                $
            });

            //검색 버튼 클릭 시
            $("#getUserInfo").click(e=>{
                let seq=$("#searchUserForm input[name='seq']").val();
                let eno=$("#searchUserForm input[name='eno']").val();
                searchUserInfo(seq,eno,0);
            });

            //하단 페이지 숫자 클릭 시
            $("#userInfoPagination li:not(.disabled.active)").on("click",function(){
                let seq=$("#searchUserForm input[name='seq']").val();
                let eno=$("#searchUserForm input[name='eno']").val();
                let selectedPage=$(this).find("input[name='page']").val();
                let totalPages = '<c:out value="${userInfoList.totalPages}"/>';
                if(selectedPage === "next"){
                    if(Number(totalPages) > 10){
                        selectedPage = 6;
                    }else{
                        selectedPage = Number(totalPages)-1;
                    }
                }
                searchUserInfo(seq,eno,selectedPage);
            })

            //삭제하기 버튼 클릭 시
            $("#deleteUser").click(function(){
                if($("#tbodyUserInfoList input:checkbox[name='delCheck']:checked").length <= 0){
                    alert("삭제할 데이터가 선택되지 않았습니다.");
                };
                var delList = new Array();
                $("#tbodyUserInfoList input:checkbox[name='delCheck']:checked").each(
                    function(e){
                        delList.push($(this).val());
                    }
                )
                deleteUser(delList);
            });
            //전체 선택 체크박스 클릭 시
            $("#checkAll").click(e=>{
                var checked = $("#checkAll").is(":checked");
                if(checked){
                    $("#tbodyUserInfoList input:checkbox[name='delCheck']").each(
                        function(e){
                            $(this).prop('checked',true);
                        }
                    );
                }else{
                    $("#tbodyUserInfoList input:checkbox[name='delCheck']").each(
                        function(e){
                            $(this).prop('checked',false);
                        }
                    );
                }
            })
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

    function enterBtnClick(event, id){
        if (event.which === 13) {
            $('#' + id).trigger('click');
            return false;
        }
    }
</script>
</html>
