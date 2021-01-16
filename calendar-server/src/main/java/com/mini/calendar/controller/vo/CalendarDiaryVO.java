package com.mini.calendar.controller.vo;

import com.mini.calendar.dao.model.CalendarDiary;

/**
 * @author songjiuhua
 * Created by 2021/1/6 17:45
 */
public class CalendarDiaryVO extends CalendarDiary {

    private String updateTimeStr;

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
