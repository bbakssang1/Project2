package com.rooke.paging;

import com.rooke.domain.SearchDto;
import lombok.Getter;

@Getter
public class Pagination {

  private int totalRecordCount; // 전체 데이터 수
  private int totalPageCount; // 전체 페이지 수
  private int startPage; // 첫 페이지 번호
  private int endPage; // 마지막 페이지 번호
  private int limitStart; // limit 시작 위치
  private boolean existPrevPage; // 이전 페이지 존재 여부
  private boolean existNextPage; // 다음 페이지 존재 여부

  public Pagination(int totalRecordCount, SearchDto search) {
    if (totalRecordCount > 0) {
      this.totalRecordCount = totalRecordCount;
      this.calculation(search);


    }
  }

  private void calculation(SearchDto search) {
    // 전체 페이지 수 계산
    totalPageCount = ((totalRecordCount - 1) / search.getRecordSize()) + 1;

    // 현재 페이지 번호가 전체 페이지 수보다 큰 경우에 현재 페이지 번호에 전체 페이지 수 저장
    if (search.getPage() > totalPageCount) {
      search.setPage(totalPageCount);
    }

    // 첫 페이지 번호 계산]
    startPage = ((search.getPage() - 1) / search.getPageSize()) * search.getPageSize() + 1;
    // 끝 페이지 번호 계산
    endPage = startPage + search.getPageSize() - 1;
    // 끝 페이지가 전체 페이지 수보다 큰 경우 끝 페이지를 전체 페이지 수에 저장
    if (endPage > totalPageCount) {
      endPage = totalPageCount;

    }
    // limit 시작 위치
    limitStart = (search.getPage() - 1) * search.getRecordSize();
    // 이전 페이지 존재 여부 확인
    existPrevPage = startPage != 1;
    // 다음 페이지 존재 여부 확인
    existNextPage = (endPage * search.getRecordSize()) < totalRecordCount;



  }
}
