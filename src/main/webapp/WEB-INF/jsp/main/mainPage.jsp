<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="../layouts/header.jsp"%>
    </head>
    <body class="bg-light">
        <div class="container">
            <%@include file="../layouts/top.jsp"%> 
            <!-- Page Content-->
            <section>
                <div class="container p-0">
                    <!-- <h1 class="fw-bolder fs-5 mb-4">Company Blog</h1> -->
                    <div class="card border-0 shadow rounded-3 overflow-hidden">
                        <div class="card-body p-0">
                            <div class="row gx-0">
                                <div class="col-lg-6 col-xl-5 py-lg-5">
                                    <div class="p-4 p-md-5">
                                        <!-- <div class="badge bg-primary bg-gradient rounded-pill mb-2">News</div> -->
                                        <div class="h2 fw-bolder">헤드 컨텐츠</div>
                                        <p>내용 작성</p>
                                        <a class="stretched-link text-decoration-none" href="#!">
                                            More
                                            <i class="bi bi-arrow-right"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-xl-7"><div class="bg-featured-blog" style="background-image: url('https://dummyimage.com/700x350/343a40/6c757d')"></div></div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-xl-8 card border">
                            <div class="card-body">
                                <h2 class="fw-bolder fs-5 mb-4 card-title">공지사항</h2>
                                <div id="newsItems">
                                    <c:forEach items="${noticeBoardList}" var="items">
                                        <div class="border-bottom border-top d-flex flex-row justify-content-between" style="height:2.5rem;">
                                            <a class="link-dark col" href="<c:url value='/boardDetail/notice/${items.boardId}'/>"><h5><c:out value="${items.boardTitle}"/></h5></a>
                                            <fmt:parseDate value="${items.cretDate}" var="dateFmt" pattern="yyyyMMdd"/>
                                            <div class="small text-muted col-sm-auto"><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/></div>
                                        </div>
                                    </c:forEach>
                                    <c:if test="${fn:length(noticeBoardList) < 5}">
                                        <c:forEach var="i" begin="${fn:length(noticeBoardList)}" end="4" step="1">
                                            <div class="border-bottom border-top" style="height:2.5rem;">
                                                <a class="link-dark col-xl-auto"><h5></h5></a>
                                                <div class="small text-muted col-sm-auto"></div>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                </div>
                                <div class="text-end mb-5 mb-xl-0">
                                    <a class="text-decoration-none" href="<c:url value='/board/notice'/>">
                                        More
                                        <i class="bi bi-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-4 p-0">
                            <div class="card border h-100">
                                <div class="card-body p-4">
                                    <div class="d-flex h-100 align-items-center justify-content-center">
                                        <div class="text-center">
                                            <div class="h6 fw-bolder">연락처</div>
                                            <p class="text-muted mb-4">
                                                하단의 연락처를 통해 연락해 주세요.
                                                <br />
                                                Email&nbsp;:&nbsp;<a>email@domain.com</a><br />
                                                HP&nbsp;:&nbsp;<a>02-123-1234</a>
                                            </p>
                                            <div class="h6 fw-bolder">Follow us</div>
                                            <a class="fs-5 px-2 link-dark" href="#!"><i class="bi-twitter-x"></i></a>
                                            <a class="fs-5 px-2 link-dark" href="#!"><i class="bi-facebook"></i></a>
                                            <a class="fs-5 px-2 link-dark" href="#!"><i class="bi-linkedin"></i></a>
                                            <a class="fs-5 px-2 link-dark" href="#!"><i class="bi-youtube"></i></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Blog preview section-->
            <section class="py-5">
                <div class="container px-5">
                    <h2 class="fw-bolder fs-5 mb-4">최근 소식</h2>
                    <div class="row gx-5">
                        <div class="col-lg-4 mb-5">
                            <div class="card h-100 shadow border-0">
                                <img class="card-img-top" src="https://dummyimage.com/600x350/ced4da/6c757d" alt="..." />
                                <div class="card-body p-4">
                                    <!-- <div class="badge bg-primary bg-gradient rounded-pill mb-2">News</div> -->
                                    <a class="text-decoration-none link-dark stretched-link" href="#!"><div class="h5 card-title mb-3">제목1</div></a>
                                    <p class="card-text mb-0">내용 간략(50 텍스트)...</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 mb-5">
                            <div class="card h-100 shadow border-0">
                                <img class="card-img-top" src="https://dummyimage.com/600x350/adb5bd/495057" alt="..." />
                                <div class="card-body p-4">
                                    <!-- <div class="badge bg-primary bg-gradient rounded-pill mb-2">Media</div> -->
                                    <a class="text-decoration-none link-dark stretched-link" href="#!"><div class="h5 card-title mb-3">제목2</div></a>
                                    <p class="card-text mb-0">내용 간략(50개 텍스트)...</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 mb-5">
                            <div class="card h-100 shadow border-0">
                                <img class="card-img-top" src="https://dummyimage.com/600x350/6c757d/343a40" alt="..." />
                                <div class="card-body p-4">
                                    <!-- <div class="badge bg-primary bg-gradient rounded-pill mb-2">News</div> -->
                                    <a class="text-decoration-none link-dark stretched-link" href="#!"><div class="h5 card-title mb-3">제목3</div></a>
                                    <p class="card-text mb-0">내용 간략(50개 텍스트)...</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="text-end mb-5 mb-xl-0">
                        <a class="text-decoration-none" href="#!">
                            More
                            <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </section> 
            <%@include file="../layouts/bottom.jsp"%> 
        </div>
    </body>
</html>
