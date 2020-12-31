package com.mini.calendar.dao.model;

/**
 * @author songjiuhua
 * Created by 2020/12/30 11:13
 */
public class CalendarInfoQueryDTO {

    private Integer solarYear;

    private Integer solarMonth;

    public CalendarInfoQueryDTO() {
    }

    public CalendarInfoQueryDTO(Integer solarYear, Integer solarMonth) {
        this.solarYear = solarYear;
        this.solarMonth = solarMonth;
    }

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
}
