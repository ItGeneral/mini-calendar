package com.mini.calendar.dao.mapper;

import com.mini.calendar.dao.model.CalendarDiary;
import com.mini.calendar.dao.model.CalendarUser;
import com.mini.db.annotation.RecDB;
import com.mini.db.annotation.RecDBWritable;
import org.apache.ibatis.annotations.Param;

/**
 * @author songjiuhua
 * Created by 2021/1/6 15:49
 */
@RecDBWritable
@RecDB(name = "calendar")
public interface CalendarDairyMapper {

    void saveDairy(@Param("dairy") CalendarDiary calendarDiary);

    void updateDairy(@Param("dairy") CalendarDiary calendarDiary);

    CalendarDiary queryBySolarDate(@Param("solarDate") String solarDate, @Param("openId") String openId);

    CalendarDiary queryByOpenIdAndCalendarId(@Param("openId") String openId, @Param("calendarInfoId") Integer calendarInfoId);

}
