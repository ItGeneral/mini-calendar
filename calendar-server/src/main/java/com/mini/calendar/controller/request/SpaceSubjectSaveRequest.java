package com.mini.calendar.controller.request;

/**
 * @author songjiuhua
 * Created by 2021/1/19 17:15
 */
public class SpaceSubjectSaveRequest {

    private Integer spaceId;

    private Integer userId;

    private String content;

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
