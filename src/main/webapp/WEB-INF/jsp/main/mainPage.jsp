<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>홈</title>
        <%@include file="../layouts/header.jsp"%>
    </head>
    <body class="bg-light" oncontextmenu="return false" onselectstart="return false" ondragstart="return false">
        <div class="container">
            <%@include file="../layouts/top.jsp"%> 
            <!-- Page Content-->
            <section>
                <div class="container p-0">
                    <!-- <h1 class="fw-bolder fs-5 mb-4">Company Blog</h1> -->
                    <div class="card border-0 shadow rounded-3 overflow-hidden">
                        <div class="card-body p-0">
                            <div class="swiper mySwiper">
                                <div class="swiper-wrapper">
                                    <div class="swiper-slide">
                                        <div class="row gx-0">
                                            <div class="col-lg-6 col-xl-5 py-lg-5">
                                                <div class="p-4 p-md-5 slider s1">
                                                    <div class="h2 fw-bolder">헤드 컨텐츠1</div>
                                                    <p>내용 작성1</p>
                                                    <a class="stretched-link text-decoration-none" href="#!">
                                                        More
                                                        <i class="bi bi-arrow-right"></i>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="col-lg-6 col-xl-7">
                                                <div class="bg-featured-blog" style="background-image: url('https://dummyimage.com/700x350/343a40/6c757d')"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="swiper-slide">
                                        <div class="row gx-0">
                                            <div class="col-lg-6 col-xl-5 py-lg-5">
                                                <div class="p-4 p-md-5 slider s1">
                                                    <div class="h2 fw-bolder">헤드 컨텐츠2</div>
                                                    <p>내용 작성2</p>
                                                    <a class="stretched-link text-decoration-none" href="#!">
                                                        More
                                                        <i class="bi bi-arrow-right"></i>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="col-lg-6 col-xl-7">
                                                <div class="bg-featured-blog" style="background-image: url('https://dummyimage.com/700x350/343a40/6c757d')"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="swiper-slide">
                                        <div class="row gx-0">
                                            <div class="col-lg-6 col-xl-5 py-lg-5">
                                                <div class="p-4 p-md-5 slider s1">
                                                    <div class="h2 fw-bolder">헤드 컨텐츠3</div>
                                                    <p>내용 작성3</p>
                                                    <a class="stretched-link text-decoration-none" href="#!">
                                                        More
                                                        <i class="bi bi-arrow-right"></i>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="col-lg-6 col-xl-7">
                                                <div class="bg-featured-blog" style="background-image: url('https://dummyimage.com/700x350/343a40/6c757d')"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- If we need pagination -->
                                <div class="swiper-pagination"></div>
                                <!-- If we need navigation buttons -->
                                <div class="swiper-button-prev"></div>
                                <div class="swiper-button-next"></div>
                                <!-- If we need scrollbar -->
                                <!-- <div class="swiper-scrollbar"></div> -->
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
                                            <a class="link-dark col" href="<c:url value='/board/notice/detail/${items.boardId}'/>">
                                                <h5>
                                                    <c:out value="${items.boardTitle}"/>
                                                    <c:if test="${items.asNew eq 1}">
                                                        <div class="badge bg-danger bg-gradient rounded-pill ms-2 mb-2">New</div>
                                                    </c:if>
                                                </h5>
                                            </a>
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
                                            <a class="fs-5 px-2 link-dark" href="https://twitter.com/?lang=ko" target="_blank"><i class="bi-twitter-x"></i></a>
                                            <a class="fs-5 px-2 link-dark" href="https://www.facebook.com/?locale=ko_KR" target="_blank"><i class="bi-facebook"></i></a>
                                            <a class="fs-5 px-2 link-dark" href="https://www.instagram.com/" target="_blank"><i class="bi-linkedin"></i></a>
                                            <a class="fs-5 px-2 link-dark" href="https://www.youtube.com" target="_blank"><i class="bi-youtube"></i></a>
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
                        <c:forEach items="${newsList}" var="items">
                            <div class="col-lg-4 mb-5">
                                <div class="card h-100 shadow border-0">
                                    <c:if test="${items.thumbPath ne ''}">
                                        <img class="card-img-top" src="${items.thumbPath}" alt="..." />
                                    </c:if>
                                    <div class="card-body p-4">
                                        <c:if test="${items.asNew eq 1}">
                                            <div class="badge bg-danger bg-gradient rounded-pill mb-2">New</div>
                                        </c:if>
                                        <a class="text-decoration-none link-dark stretched-link" href="/board/news/detail/${items.boardId}"><div class="h5 card-title mb-3">${items.title}</div></a>
                                        <fmt:parseDate value="${items.cretDate}" var="dateFmt" pattern="yyyyMMdd"/>
                                        <p class="card-text mb-0"><fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd"/></p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <c:if test="${fn:length(newsList) < 3}">
                            <c:forEach var="i" begin="${fn:length(newsList)}" end="2" step="1">
                                <div class="col-lg-4 mb-5">
                                    <div class="card h-100 shadow border-0">
                                        <img class="card-img-top" src="https://dummyimage.com/600x350/ced4da/6c757d" alt="..." />
                                        <div class="card-body p-4">
                                                <!-- <div class="badge bg-primary bg-gradient rounded-pill mb-2">New</div> -->
                                            <a class="text-decoration-none link-dark stretched-link"><div class="h5 card-title mb-3">제목</div></a>
                                            <p class="card-text mb-0">날짜</p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div class="text-end mb-5 mb-xl-0">
                        <a class="text-decoration-none" href="<c:url value='/board/news'/>">
                            More
                            <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </section> 
            <%@include file="../layouts/bottom.jsp"%> 
        </div>
        <script>
            const swiper = new Swiper('.mySwiper', {
                // Optional parameters
                loop: true, // 슬라이드 반복여부
                slidesPerView: 'auto',// 한 슬라이드에 보여줄 개수
                autoplay:{
                    delay:3000, //3초
                    disableOnInteraction: false //false 로 설정하면 스와이프 후 자동재생이 비활성화 되지 않음
                },
                // If we need pagination
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                },
                // Navigation arrows
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },
                // And if we need scrollbar
                //   scrollbar: {
                //     el: '.swiper-scrollbar',
                //   },
            });
        </script>
    </body>
</html>
