package com.mini.calendar.controller.request;

/**
 * @author songjiuhua
 * Created by 2021/1/6 16:38
 */
public class CalendarDiaryRequest {

    private String solarDate;

    private String title;

    private String content;

    private String openId;

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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
