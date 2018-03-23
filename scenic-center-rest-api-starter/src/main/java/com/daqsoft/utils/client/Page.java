package com.daqsoft.utils.client;

import java.util.List;

public final class Page<T> {

    private int currentPage;
    private int pageSize;
    private int totalPage;
    private int totalRecord;
    private List<T> dataList;

    private Page() {
    }

    public Page(final int pageSize, final String page, final int totalRecord) {
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        setTotalPage();
        setCurrentPage(page);
    }
    public static Page newPage(int pageSize, final String page,
                               final int totalRecord) {
        return new Page(pageSize, page, totalRecord);
    }
    

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    private void setTotalPage() {
        if (totalRecord == 0) {
            totalPage = 1;
        } else if (totalRecord % pageSize == 0) {
            totalPage = totalRecord / pageSize;
        } else {
            totalPage = totalRecord / pageSize + 1;
        }
    }
    public int getCurrentPage() {
        return currentPage;
    }

    private void setCurrentPage(String page) {
        try {
            currentPage = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        /*
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }*/
    }
    public int getTotalPage() {
        return totalPage;

    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getStartRow() {
        return (currentPage - 1) * pageSize;
    }
    public int getEndRow() {
        return pageSize;
    }
    public List<T> getDataList() {
        return dataList;
    }
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
    public int getFirst() {
        return 1;
    }
    public int getPrevious() {
        return currentPage - 1;
    }
    public int getNext() {
        return currentPage + 1;
    }
    public int getLast() {
        return totalPage;
    }

    public int getpageSize() {
        return this.pageSize;
    }
}

