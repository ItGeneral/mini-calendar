package com.mini.calendar.controller.request;

/**
 * @author songjiuhua
 * Created by 2021/1/6 16:41
 */
public class CalendarDiaryQueryRequest {

    private String openId;

    private String solarDate;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSolarDate() {
        return solarDate;
    }

    public void setSolarDate(String solarDate) {
        this.solarDate = solarDate;
    }
}
