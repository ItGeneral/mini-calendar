package com.mini.calendar.service;

import com.mini.calendar.dao.mapper.CalendarInfoMapper;
import com.mini.calendar.dao.model.CalendarInfo;
import com.mini.calendar.dao.model.CalendarInfoQueryDTO;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songjiuhua
 * Created by 2020/12/30 11:00
 */
@Service
public class CalendarService {

    @Autowired
    private CalendarInfoMapper calendarInfoMapper;

    public List<CalendarInfo> queryCalendarListBySolarYearAndMonth(Integer year, Integer month){
        List<CalendarInfoQueryDTO> queryDTOList = new ArrayList<>();
        queryDTOList.add(new CalendarInfoQueryDTO(year, month));
        if (month == 1){
            queryDTOList.add(new CalendarInfoQueryDTO(year - 1, 12));
            queryDTOList.add(new CalendarInfoQueryDTO(year, month + 1));
        }else if (month == 12){
            queryDTOList.add(new CalendarInfoQueryDTO(year + 1, 1));
            queryDTOList.add(new CalendarInfoQueryDTO(year, month - 1));
        }else {
            queryDTOList.add(new CalendarInfoQueryDTO(year, month - 1));
            queryDTOList.add(new CalendarInfoQueryDTO(year, month + 1));
        }
        List<CalendarInfo> calendarInfoList = calendarInfoMapper.queryCalendarListBySolarYearAndMonth(queryDTOList);
        return calendarInfoList;
    }

    public List<Integer> queryYearList(){
        return calendarInfoMapper.queryYearList();
    }

}
