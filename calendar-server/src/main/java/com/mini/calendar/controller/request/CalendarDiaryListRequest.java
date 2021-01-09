package com.mini.calendar.controller.request;

/**
 * @author songjiuhua
 * Created by 2021/1/9 17:11
 */
public class CalendarDiaryListRequest extends BaseRequest{

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
