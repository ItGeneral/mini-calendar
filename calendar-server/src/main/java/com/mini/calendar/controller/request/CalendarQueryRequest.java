package com.mini.calendar.controller.request;

/**
 * @author songjiuhua
 * Created by 2020/12/30 13:36
 */
public class CalendarQueryRequest {

    private Integer year;

    private Integer month;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
