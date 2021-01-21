package com.mini.calendar.dao.model;

import java.util.Date;

/**
 * @author songjiuhua
 * Created by 2021/1/21 19:51
 */
public class SpaceUserRelateDTO {

    private Integer userId;

    private Integer spaceId;
    /**
     * 用户名
     */
    private String nickName;
    /**
     * 用户头像
     */
    private String avatarUrl;
    /**
     * 加入空间时间
     */
    private Date createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
