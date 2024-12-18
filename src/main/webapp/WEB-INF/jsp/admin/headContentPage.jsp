<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>메인헤드콘텐츠관리</title>
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
                        <col width="10%">
                        <col width="15%">
                        <col width="35%">
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
                    <a class="btn btn-success" href='<c:url value="/admin/headContentWrite.do"/>'>등록하기</a>
                </div>
            </form>
        <div>
            <table class="table table-bordered table-hover">
                <colgroup>
                    <col width="5%">
                    <col width="45%">
                    <col width="15%">
                    <col width="15%">
                    <col width="15%">
                </colgroup>
                <thead>
                    <tr class="text-center">
                        <th class="table-info">번호</th>
                        <th class="table-info">제목</th>
                        <th class="table-info">작성자</th>
                        <th class="table-info">시작일자</th>
                        <th class="table-info">종료일자</th>
                    </tr>
                </thead>
                <tbody id="tbodyHeadContentsList">
                    <c:forEach items="${totalContents.headContentsList}" var="items">
                    <tr>
                        <td class='delCheck text-center'><c:out value="${items.contentSeq}"/></td>
                        <td><a href="<c:url value='/admin/headContentModify/${items.contentSeq}'/>"><c:out value="${items.title}"/></a></td>
                        <td><c:out value="${items.cretUserNm}"/></td>
                        <td>
                            <fmt:parseDate value="${items.strDate}" var="startDate" pattern="yyyyMMdd"/>
                            <fmt:formatDate value="${startDate}" var="strDate" pattern="yyyy-MM-dd"/>
                            <c:out value="${strDate}"/>
                        </td>
                        <td>
                            <fmt:parseDate value="${items.endDate}" var="endedDate" pattern="yyyyMMdd"/>
                            <fmt:formatDate value="${endedDate}" var="endDate" pattern="yyyy-MM-dd"/>
                            <c:out value="${endDate}"/>
                        </td>
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
                                <c:when test="${totalPage eq 1 or totalPage eq 0}">
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
        var searchHeadContents = function(contentSeq,title,delYn,paging){
            $.ajax({
                    url: '<c:url value="/admin/getHeadContents.do"/>',
                    type: "GET",
                    dataType: "json",
                    traditional:true,
                    data: {"contentSeq":contentSeq,
                            "title":title,
                            "delYn":delYn,
                            "page":paging},
                    success: response=>{
                        if(response.isSucceed){
                            let data = response.data.headContentsList;
                            let dataText = "";
                            for(i=0;i<data.length;i++){
                                dataText = dataText + "<tr>";
                                dataText = dataText + "<td>"+data[i].contentSeq+"</td>";
                                dataText = dataText + "<td>"+data[i].title+"</td>";
                                dataText = dataText + "<td>"+data[i].certUserNm+"</td>";
                                dataText = dataText + "<td>"+data[i].strDate+"</td>";
                                dataText = dataText + "<td>"+data[i].endDate+"</td>";
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
                                    dataText = dataText + "<tr>";
                                }
                            }
                            $("#tbodyHeadContentsList").empty();
                            if(dataText != ""){
                                $("#tbodyHeadContentsList").append(dataText);
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
                            $("#headContentsPagination").empty();
                            if(pageText != null){
                                $("#headContentsPagination").append(pageText);
                                $("#headContentsPagination li:not(.disabled.active)").on("click",function(){
                                    let selectedPage=$(this).find("input[name='page']").val();
                                    let oldData;
                                    if(selectedPage === "prev"){
                                        $("#headContentsPagination li").each(function(){
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
                                        $("#headContentsPagination li").each(function(){
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
                                    searchAdminInfo(contentSeq,title,delYn,selectedPage);
                                })
                            }
                        }
                    },
                    error: e=>{
                        console.log("Get AdminInfoList Ajax Get Data Error :: ", e);
                    }
                })
        };

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

    function dateConvert(yyyymmdd){
        let yyyy = yyyymmdd.substring(0,4);
        let mm = yyyymmdd.substring(4,6);
        let dd = yyyymmdd.substring(6,8);

        return yyyy+"-"+mm+"-"+dd;
    }

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
