<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>관리자정보</title>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light" oncontextmenu="return false" onselectstart="return false" ondragstart="return false">
    <div class="container">
        <%@include file="../layouts/top.jsp"%> 
        <div>
            <form class="searchForm" id="searchHeadContnentsForm">
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
                        <td><input class="form-control" onKeypress="return enterBtnClick(event,'getHeadContents')"  type="number" name="seq" value=""></td>
                        <th class="table-success pt-3"><span class="form-label">제목 :</span></th>
                        <td><input class="form-control" onKeypress="return enterBtnClick(event,'getHeadContents')" type="text" name="title" value=""></td>
                        <td>
                            <button class="btn btn-primary searchBtn" type="button" id="getHeadContents">검색</button>
                            <button class="btn btn-primary searchReset" type="button" id="searchReset">초기화</button>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="paging" value="0">
                <div style="float:right;">
                    <a class="btn btn-success" href='<c:url value="/admin/addHeadContent.do"/>'>등록하기</a>
                    <!-- <button class="btn btn-danger" type="button" id="deleteAdmin">삭제하기</button> -->
                </div>
            </form>
        <div>
            <table class="table table-bordered table-hover">
                <thead>
                    <tr class="text-center">
                        <th class="table-info"><input type="checkBox" id="checkAll"></th>
                        <th class="table-info">번호</th>
                        <th class="table-info">제목</th>
                        <th class="table-info">작성자</th>
                        <th class="table-info">생성일자</th>
                        <th class="table-info">생성시간</th>
                        <th class="table-info">변경일자</th>
                        <th class="table-info">변경시간</th>
                        <th class="table-info">시작일자</th>
                        <th class="table-info">종료일자</th>
                    </tr>
                </thead>
                <tbody id="tbodyHeadContentsList">
                    <c:forEach items="${totalContents.headContentsList}" var="items">
                    <tr>
                        <td class='delCheck'><input type="checkbox" name="delCheck" value="${items.seq}"></td>
                        <td><c:out value="${items.contentSeq}"/></td>
                        <td><c:out value="${items.title}"/></td>
                        <td><c:out value="${items.userNm}"/></td>
                        <td><c:out value="${items.cretDate}"/></td>
                        <td><c:out value="${items.cretTime}"/></td>
                        <td><c:out value="${items.chgDate}"/></td>
                        <td><c:out value="${items.chgTime}"/></td>
                        <td><c:out value="${items.strDate}"/></td>
                        <td><c:out value="${items.endDate}"/></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <nav aria-label="Page navigation example">
                <ul class="pagination" style="justify-content: center;" id="headContentsPagination">
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
    var adminAccountPage = (function(){
        var init = function(){

        };
        //사용자 데이터 출력
        // var searchHeadContents = function(seq,userId,paging){
        //     $.ajax({
        //             url: '<c:url value="/admin/getHeadContents.do"/>',
        //             type: "GET",
        //             dataType: "json",
        //             traditional:true,
        //             data: {"seq":seq,
        //                     "userId":userId,
        //                     "page":paging},
        //             success: response=>{
        //                 if(response.isSucceed){
        //                     $("#checkAll").prop('checked',false);
        //                     let data = response.data.adminInfoList;
        //                     let dataText = "";
        //                     for(i=0;i<data.length;i++){
        //                         dataText = dataText + "<tr>";
        //                         dataText = dataText + "<td class='delCheck'><input type='checkbox' name='delCheck' value="+data[i].seq+"></td>";
        //                         dataText = dataText + "<td>"+data[i].seq+"</td>";
        //                         dataText = dataText + "<td>"+data[i].userId+"</td>";
        //                         dataText = dataText + "<td>"+data[i].userNm+"</td>";
        //                         dataText = dataText + "<td>"+data[i].userGrant+"</td>";
        //                         dataText = dataText + "<td>"+data[i].celph+"</td>";
        //                         dataText = dataText + "<td>"+data[i].email+"</td>";
        //                         dataText = dataText + "<td>"+data[i].cretDate+"</td>";
        //                         dataText = dataText + "<td>"+data[i].cretTime+"</td>";
        //                         dataText = dataText + "<td>"+data[i].chgDate+"</td>";
        //                         dataText = dataText + "<td>"+data[i].chgTime+"</td>";
        //                         dataText = dataText + "<tr>";
        //                     }
        //                     if(data.length<10){
        //                         for(i=0;i<(10 - data.length);i++){
        //                             dataText = dataText + "<tr>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<td>&nbsp;</td>";
        //                             dataText = dataText + "<tr>";
        //                         }
        //                     }
        //                     $("#tbodyAdminInfoList").empty();
        //                     if(dataText != ""){
        //                         $("#tbodyAdminInfoList").append(dataText);
        //                     }

        //                     // paging 처리
        //                     let totalPages = response.data.totalPages;
        //                     let pageText = "";
        //                     if(Number(paging)>0){
        //                         pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>'
        //                     }else{
        //                         pageText = pageText + '<li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>'
        //                     }
        //                     if(totalPages > 10){
        //                         if(Number(paging)<5){
        //                             for(page=0;page<10;page++){
        //                                 if(Number(paging)===page){
        //                                     pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
        //                                 }else{
        //                                     pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
        //                                 }
        //                             }
        //                         }else if(Number(paging) >= (Number(totalPages) - 5)){
        //                             for(page=(Number(totalPages) - 10);page<Number(totalPages);page++){
        //                                 if(Number(paging)===page){
        //                                     pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
        //                                 }else{
        //                                     pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
        //                                 }
        //                             }
        //                         }else{
        //                             for(page=Number(paging)-5;page<(5+Number(paging));page++){
        //                                 if(Number(paging)===page){
        //                                     pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
        //                                 }else{
        //                                     pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
        //                                 }
        //                             }
        //                         }
        //                     }else{
        //                         for(page=0;page<Number(totalPages);page++){
        //                                 if(Number(paging)===page){
        //                                     pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
        //                                 }else{
        //                                     pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
        //                                 }
        //                             }
        //                     }
        //                     if(Number(paging) < (Number(totalPages)-1)){
        //                         pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>'
        //                     }else{
        //                         pageText = pageText + '<li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>'
        //                     }
        //                     $("#adminInfoPagination").empty();
        //                     if(pageText != null){
        //                         $("#adminInfoPagination").append(pageText);
        //                         $("#adminInfoPagination li:not(.disabled.active)").on("click",function(){
        //                             let selectedPage=$(this).find("input[name='page']").val();
        //                             let oldData;
        //                             if(selectedPage === "prev"){
        //                                 $("#adminInfoPagination li").each(function(){
        //                                     let pageData = $(this).find("input[name='page']").val();
        //                                     if(oldData === "prev"){
        //                                         if(Number(pageData) === 0){
        //                                             selectedPage = pageData;
        //                                         }else{
        //                                             selectedPage = Number(pageData)-1;
        //                                         }
        //                                         return false;
        //                                     }else{
        //                                         oldData = pageData;
        //                                     }
        //                                 });
        //                             }else if(selectedPage === "next"){
        //                                 $("#adminInfoPagination li").each(function(){
        //                                     let pageData = $(this).find("input[name='page']").val();
        //                                     if(pageData === "next"){
        //                                         if(Number(oldData) === (Number(totalPages)-1)){
        //                                             selectedPage = Number(oldData);
        //                                         }else{
        //                                             selectedPage = Number(oldData)+1;
        //                                         }
        //                                         return false;
        //                                     }else{
        //                                         oldData = pageData;
        //                                     }
        //                                 });
        //                             }
        //                             searchAdminInfo(seq,userId,selectedPage);
        //                         })
        //                     }
        //                 }
        //             },
        //             error: e=>{
        //                 console.log("Get AdminInfoList Ajax Get Data Error :: ", e);
        //             }
        //         })
        // };

        //ENO 데이터 삭제
        var deleteHeadContents = function(delList){
            $.ajax({
                url:"<c:url value='/admin/deleteHeadContents.do'/>",
                type:"POST",
                dataType:"json",
                traditional:true,
                data:{"delList": delList},
                success: response=>{
                    let seq=$("#searchHeadContentsForm input[name='seq']").val();
                    let eno=$("#searchHeadContentsForm input[name='title']").val();
                    searchHeadContents(seq,eno,0);
                    $("#checkAll").prop('checked',false);
                },
                error: e=>{
                    console.log("Delete Admin Ajax Get Data Error :: ", e);
                }
            })
        }

        var registerEvent = function(){
            //초기화 버튼 클릭 시
            $("#searchReset").click(e=>{
                $("#searchHeadContentsForm")[0].reset();
                $("#getHeadContents").trigger('click');
                $
            });

            //검색 버튼 클릭 시
            $("#getHeadContents").click(e=>{
                let seq=$("#searchHeadContentsForm input[name='seq']").val();
                let eno=$("#searchHeadContentsForm input[name='title']").val();
                searchHeadContents(seq,userId,0);
            });

            //하단 페이지 숫자 클릭 시
            $("#headContentsPagination li:not(.disabled.active)").on("click",function(){
                let seq=$("#searchHeadContentsForm input[name='seq']").val();
                let userId=$("#searchHeadContentsForm input[name='title']").val();
                let selectedPage=$(this).find("input[name='page']").val();
                let totalPages = '<c:out value="${headContentsList.totalPages}"/>';
                if(selectedPage === "next"){
                    if(Number(totalPages) > 10){
                        selectedPage = 6;
                    }else{
                        selectedPage = Number(totalPages)-1;
                    }
                }
                searchHeadContents(seq,userId,selectedPage);
            })

            //삭제하기 버튼 클릭 시
            $("#deleteAdmin").click(function(){
                if($("#tbodyAdminInfoList input:checkbox[name='delCheck']:checked").length <= 0){
                    alert("삭제할 데이터가 선택되지 않았습니다.");
                };
                var delList = new Array();
                $("#tbodyAdminInfoList input:checkbox[name='delCheck']:checked").each(
                    function(e){
                        delList.push($(this).val());
                    }
                )
                deleteHeadContents(delList);
            });
            //전체 선택 체크박스 클릭 시
            $("#checkAll").click(e=>{
                var checked = $("#checkAll").is(":checked");
                if(checked){
                    $("#tbodyAdminInfoList input:checkbox[name='delCheck']").each(
                        function(e){
                            $(this).prop('checked',true);
                        }
                    );
                }else{
                    $("#tbodyAdminInfoList input:checkbox[name='delCheck']").each(
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
        adminAccountPage.init();
    });

    function enterBtnClick(event, id){
        if (event.which === 13) {
            $('#' + id).trigger('click');
            return false;
        }
    }
</script>
</html>
