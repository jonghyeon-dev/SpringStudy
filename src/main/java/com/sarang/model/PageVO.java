package com.sarang.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PageVO {
    private static final float listSize = 10; // 한 화면에 보여줄 게시글 목록 수 (소수점 계산을 위해 float로 정의)
    private static final float pageSize = 10; // 한 화면에 보여줄 하단 페이지네이션 수 (소수점 계산을 위해 float로 정의)

    private Integer totalPage;      // 총 페이지 수
    private Integer pageGroup;      // 화면에 보여질 페이지 그룹

    private Integer currentPage;    // 현재 페이지
    private Integer startPage;      // 화면에 보여질 시작 페이지
    private Integer endPage;        // 화면에 보여질 마지막 페이지
    private Integer prevPage;       // 이전 페이지 그룹
    private Integer nextPage;       // 다음 페이지 그룹
    private Integer firstPage;      // 처음 페이지
    private Integer lastPage;       // 마지막 페이지

    public PageVO(int totalCount, int currentPage){
        // 첫 페이지
        setFirstPage(1);
        // 현재 페이지
        setCurrentPage(currentPage);
        // 총 페이지 = 반올림(총 데이터 개수 / 화면에 표시항 데이터 개수)
        setTotalPage((int)Math.ceil(totalCount/listSize));
        // 화면에 표시할 페이지 그룹 = 반올림(현재 페이지 / 화면에 표시할 페이지 개수)
        setPageGroup((int)Math.ceil(currentPage/pageSize));
        // 화면에 표시할 시작 페이지 = (화면에 표시할 페이지 그룹 * 화면에 표시할 페이지 개수) - (화면에 표시할 페이지 개수 -1)
        // startPage 데이터가 0과 같거나 작다면 startPage = 1
        setStartPage((int)((pageGroup * pageSize) - (pageSize -1)) > 0 ? (int)((pageGroup * pageSize) - (pageSize -1)) : 1);
        // 화면에 표시할 마지막 페이지 = 화면에 표시할 페이지 그룹 * 화면에 표시할 페이지 개수
        // endPage데이터가 총 페이지 보다 크다면 endPage = totalPage
        setEndPage((int)(pageGroup*pageSize) < totalPage ? (int)(pageGroup*pageSize) : totalPage);
        // 화면에 표시할 첫번쨰 페이지가 화면에 페이지 개수 보다 크다면 '이전' 생성
        setPrevPage(startPage > pageSize ? (startPage - 1) : 0);
        // 화면에 표시할 마지막 페이지가 총 페이지 보다 작다면 '다음' 생성
        setNextPage(endPage < totalPage ? (endPage + 1) : 0);
        // 화면에 표시할 페이지 그룹이 1보다 크다면 '처음' 생성
        setFirstPage(pageGroup > 1 ? 1 : 0);
        // 화면에 표시할 페이지 그룹이 반올림(총 페이지/ 화면에 표시할 페이지 개수) 보다 작다면 '마지막' 생성
        setLastPage(pageGroup < Math.ceil(totalPage/pageSize)? totalPage : 0);
    }
}
