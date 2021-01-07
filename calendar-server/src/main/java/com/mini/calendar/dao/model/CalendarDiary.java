package com.mini.calendar.dao.model;

import java.util.Date;

/**
 * @author songjiuhua
 * Created by 2021/1/6 16:09
 */
public class CalendarDiary {

    private Integer id;

    private Integer calendarInfoId;

    private String openId;

    private Integer userId;

    private String solarDate;

    private String title;

    private String content;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCalendarInfoId() {
        return calendarInfoId;
    }

    public void setCalendarInfoId(Integer calendarInfoId) {
        this.calendarInfoId = calendarInfoId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSolarDate() {
        return solarDate;
    }

    public void setSolarDate(String solarDate) {
        this.solarDate = solarDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
