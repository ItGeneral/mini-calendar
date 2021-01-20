package com.mini.calendar.dao.model;

import java.util.Date;

/**
 * @author songjiuhua
 * Created by 2021/1/18 19:03
 */
public class DomainSubjectDTO {

    private Integer userId;

    private String nickName;

    private String userAvatarUrl;

    private Date diaryCreateTime;

    private String content;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public Date getDiaryCreateTime() {
        return diaryCreateTime;
    }

    public void setDiaryCreateTime(Date diaryCreateTime) {
        this.diaryCreateTime = diaryCreateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
