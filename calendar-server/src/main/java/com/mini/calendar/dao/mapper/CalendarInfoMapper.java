package com.mini.calendar.dao.mapper;

import com.mini.calendar.controller.vo.CalendarInfoVO;
import com.mini.calendar.dao.model.CalendarCountDayDTO;
import com.mini.calendar.dao.model.CalendarInfo;
import com.mini.calendar.dao.model.CalendarInfoQueryDTO;
import com.mini.db.annotation.RecDB;
import com.mini.db.annotation.RecDBWritable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author songjiuhua
 * Created by 2020/12/25 16:35
 */
@RecDBWritable
@RecDB(name = "calendar")
public interface CalendarInfoMapper {

    void batchSave(@Param("calendarInfoList") List<CalendarInfo> calendarInfoList);

    /**
     * 查询最近3个月的数据，上月、当月、下月
     * @return
     */
    List<CalendarInfo> queryCalendarListBySolarYearAndMonth(@Param("queryDTOList")List<CalendarInfoQueryDTO> queryDTOList);

    List<Integer> queryYearList();

    List<CalendarCountDayDTO> countDayNum(@Param("solarYear") Integer solarYear);

    CalendarCountDayDTO countMonthDayNum(@Param("solarYear") Integer solarYear, @Param("solarMonth") Integer solarMonth);

    CalendarInfo queryDayDetail(@Param("solarYear") Integer solarYear, @Param("solarMonth") Integer solarMonth, @Param("solarDay") Integer solarDay);
}
