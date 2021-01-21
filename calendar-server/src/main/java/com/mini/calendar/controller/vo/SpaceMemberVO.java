package com.mini.calendar.controller.vo;


/**
 * @author songjiuhua
 * Created by 2021/1/21 19:56
 */
public class SpaceMemberVO {

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
    private String addSpaceTime;

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

    public String getAddSpaceTime() {
        return addSpaceTime;
    }

    public void setAddSpaceTime(String addSpaceTime) {
        this.addSpaceTime = addSpaceTime;
    }
}
