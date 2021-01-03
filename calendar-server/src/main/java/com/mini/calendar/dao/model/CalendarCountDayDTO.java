package com.mini.calendar.dao.model;

/**
 * @author jiuhua
 * @since 2021/1/2
 */
public class CalendarCountDayDTO {

    private Integer year;

    private Integer month;

    private Integer dayNum;

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

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }
}
