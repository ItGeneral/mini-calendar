package com.mini.calendar.controller.request;

/**
 * @author songjiuhua
 * Created by 2021/1/4 10:55
 */
public class CalendarDetailQueryRequest {

    private Integer solarYear;

    private Integer solarMonth;

    private Integer solarDay;

    public Integer getSolarYear() {
        return solarYear;
    }

    public void setSolarYear(Integer solarYear) {
        this.solarYear = solarYear;
    }

    public Integer getSolarMonth() {
        return solarMonth;
    }

    public void setSolarMonth(Integer solarMonth) {
        this.solarMonth = solarMonth;
    }

    public Integer getSolarDay() {
        return solarDay;
    }

    public void setSolarDay(Integer solarDay) {
        this.solarDay = solarDay;
    }
}
