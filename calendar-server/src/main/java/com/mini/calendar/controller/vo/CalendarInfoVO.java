package com.mini.calendar.controller.vo;

import com.mini.calendar.dao.model.CalendarInfo;

/**
 * @author songjiuhua
 * Created by 2020/12/25 16:35
 */
public class CalendarInfoVO extends CalendarInfo {
    
    private boolean currentDay;

    public boolean isCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(boolean currentDay) {
        this.currentDay = currentDay;
    }
}
