package com.mini.calendar.dao.model;

/**
 * @author songjiuhua
 * Created by 2021/1/16 14:08
 */
public class DomainSpaceUserDTO {

    /**
     * 空间id
     */
    private Integer spaceId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 空间名称
     */
    private String spaceName;
    /**
     * 空间主图地址
     */
    private String avatarUrl;
    /**
     * 用户昵称
     */
    private String nickName;

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

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
