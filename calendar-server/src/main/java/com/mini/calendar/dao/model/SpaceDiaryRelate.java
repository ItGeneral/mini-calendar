package com.mini.calendar.dao.model;

/**
 * @author songjiuhua
 * Created by 2021/1/18 19:20
 */
public class SpaceDiaryRelate {

    private Integer userId;

    private Integer domainSpaceId;

    private Integer diaryId;

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

    public Integer getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(Integer diaryId) {
        this.diaryId = diaryId;
    }
}
