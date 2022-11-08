package com.mysite.sbb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    private int currentPage;            // 현재 페이지 번호
    private int totalPageList;      // 총 페이지 수
    private int totalPageCount;     // 총 게시물 수
    private int pageItem = 10;      // 한 페이지 범위의 개수
    private int pageListItem = 10;  // 보여질 페이지 범위의 개수
    private int maxId;              // 게시글 마지막 PK
    private int pageId;             // 검색 시작할 PK
    private boolean prev;
    private boolean next;

    public void pageSet(int page, int maxId) {
        // 현재 페이지
        this.currentPage = page;
        this.maxId = maxId;

        // 몇 번째 게시글 부터 시작하는지, 게시글 PK 번호
        this.pageId = this.maxId - (currentPage * pageItem);
        this.totalPageList = Math.floorDiv(this.maxId, this.getPageItem());

        // 이전 버튼 상태
        this.prev = this.currentPage > 0;
        // 다음 버튼 상태
        this.next = this.currentPage < this.totalPageList;
    }

}
