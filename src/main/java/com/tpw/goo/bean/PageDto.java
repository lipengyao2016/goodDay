package com.tpw.goo.bean;

import java.util.List;

public class PageDto<T> {
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurPageNo() {
        return curPageNo;
    }

    public void setCurPageNo(int curPageNo) {
        this.curPageNo = curPageNo;
    }

    public int getTotalPageCnt() {
        return totalPageCnt;
    }

    public void setTotalPageCnt(int totalPageCnt) {
        this.totalPageCnt = totalPageCnt;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    int pageSize;
    int curPageNo;
    int totalPageCnt;

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    int totalCnt;
    List<T> data;
}
