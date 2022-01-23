package com.zoey.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class PageReq {
    @NotNull(message = "page can not be empty!")
    private int page;

    @NotNull(message = "size can not be empty!")
    @Max(value = 1000, message = "size can not be over 1000")
    private int size;

    public PageReq() {
    }

    public PageReq(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public String toString() {
        return "PageInfo{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
