package com.mini.calendar.dao.model;

/**
 * @author songjiuhua
 * Created by 2021/1/16 13:54
 */
public class SpaceUserRelate {

    private Integer id;

    private Integer userId;

    private Integer domainSpaceId;
    /**
     * 0：表示自己创建的，1:表示邀请加入的
     */
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDomainSpaceId() {
        return domainSpaceId;
    }

    public void setDomainSpaceId(Integer domainSpaceId) {
        this.domainSpaceId = domainSpaceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
