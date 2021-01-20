package com.mini.calendar.controller.request;

/**
 * @author songjiuhua
 * Created by 2021/1/20 11:40
 */
public class SubjectPraiseRequest {

    private Integer spaceId;

    private Integer subjectId;

    private Integer userId;

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
