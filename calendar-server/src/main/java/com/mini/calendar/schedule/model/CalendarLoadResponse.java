package com.mini.calendar.schedule.model;

import java.util.List;

/**
 * @author songjiuhua
 * Created by 2020/12/25 16:06
 */
public class CalendarLoadResponse {

    private String status;

    private long t;

    private List<CalendarData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public List<CalendarData> getData() {
        return data;
    }

    public void setData(List<CalendarData> data) {
        this.data = data;
    }
}
