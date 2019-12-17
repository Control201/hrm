package com.hrm.bean;

import java.io.Serializable;

/**
 * @ClassName MyPage
 * @Version 1.0
 **/
public class MyPage implements Serializable {
    //当前页号
    private Integer pageNum;

    //当前页记录数量
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public MyPage(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public MyPage(){

    }
}
