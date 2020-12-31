package com.mini.calendar.schedule.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songjiuhua
 * Created by 2020/12/25 16:13
 */
public class CalendarData {

    List<CalendarDTO> almanac = new ArrayList<>();

    public List<CalendarDTO> getAlmanac() {
        return almanac;
    }

    public void setAlmanac(List<CalendarDTO> almanac) {
        this.almanac = almanac;
    }
}
