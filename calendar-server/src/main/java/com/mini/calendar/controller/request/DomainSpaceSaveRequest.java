package com.mini.calendar.controller.request;

/**
 * @author songjiuhua
 * Created by 2021/1/16 13:43
 */
public class DomainSpaceSaveRequest {

    private Integer userId;

    private String title;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
