package com.zoey.reps;

import java.util.List;

public class PageResp<T> {

    private long totalNum;

    private List<T> list;

    public PageResp() {
    }

    public PageResp(long totalNum, List<T> list) {
        this.totalNum = totalNum;
        this.list = list;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageResp{" +
                "totalNum=" + totalNum +
                ", list=" + list +
                '}';
    }
}
