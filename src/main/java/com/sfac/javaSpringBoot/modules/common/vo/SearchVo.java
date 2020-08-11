package com.sfac.javaSpringBoot.modules.common.vo;
/**
 * @Description SearchVo
 * @Author HymanHu
 * @Date 2020/8/11 15:01
 */
public class SearchVo {

    public final static int DEFAULT_CURRENT_PAGE = 1;
    public final static int DEFAULT_PAGE_SIZE = 5;

    private int currentPage;
    private int pageSize;
    private String keyWord;
    private String orderBy;
    private String sort;

    public void initSearchVo() {
        if (this != null) {
            this.setCurrentPage(this.getCurrentPage() == 0 ? DEFAULT_CURRENT_PAGE : this.getCurrentPage());
            this.setPageSize(this.getPageSize() == 0 ? DEFAULT_PAGE_SIZE : this.getPageSize());
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}