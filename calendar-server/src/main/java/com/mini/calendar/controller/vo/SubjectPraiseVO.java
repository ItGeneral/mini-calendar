package com.mini.calendar.controller.vo;

/**
 * @author songjiuhua
 * Created by 2021/1/20 11:57
 */
public class SubjectPraiseVO {

    private Integer spaceId;

    private Integer subjectId;

    private Integer userId;

    private String userName;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
