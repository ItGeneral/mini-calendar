package com.mini.calendar.controller.request;

/**
 * @author songjiuhua
 * Created by 2021/1/9 17:10
 */
public class BaseRequest {

    private Integer pageNo;

    private Integer pageSize = 10;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
