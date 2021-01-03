package com.mini.calendar.controller.vo;

import java.util.List;

/**
 * @author jiuhua
 * @since 2021/1/2
 */
public class CalendarCountDayVO {

    private Integer year;

    private List<MonthCountDay> monthDayNumList;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<MonthCountDay> getMonthDayNumList() {
        return monthDayNumList;
    }

    public void setMonthDayNumList(List<MonthCountDay> monthDayNumList) {
        this.monthDayNumList = monthDayNumList;
    }

    public static class MonthCountDay {

        private Integer month;

        private Integer dayNum;

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
}
