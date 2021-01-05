package com.mini.calendar.service;

import com.mini.calendar.controller.vo.CalendarCountDayVO;
import com.mini.calendar.controller.vo.CalendarInfoVO;
import com.mini.calendar.dao.mapper.CalendarInfoMapper;
import com.mini.calendar.dao.model.CalendarCountDayDTO;
import com.mini.calendar.dao.model.CalendarInfo;
import com.mini.calendar.dao.model.CalendarInfoQueryDTO;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
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
    protected CalendarInfoMapper calendarInfoMapper;

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

    public CalendarCountDayVO countMonthDay(Integer year){
        CalendarCountDayVO countDayVO = new CalendarCountDayVO();
        countDayVO.setYear(year);
        List<CalendarCountDayVO.MonthCountDay> countDayList = new ArrayList<>();
        List<CalendarCountDayDTO> countDayDTOList = calendarInfoMapper.countDayNum(year);
        for (CalendarCountDayDTO dayDTO : countDayDTOList) {
            CalendarCountDayVO.MonthCountDay countDay = new CalendarCountDayVO.MonthCountDay();
            countDay.setMonth(dayDTO.getMonth());
            countDay.setDayNum(dayDTO.getDayNum());
            countDayList.add(countDay);
        }
        countDayVO.setMonthDayNumList(countDayList);
        return countDayVO;
    }

    public CalendarCountDayDTO countMonthDayNum(Integer year, Integer month){
        return calendarInfoMapper.countMonthDayNum(year, month);
    }

    public CalendarInfoVO queryDayDetail(Integer solarYear, Integer solarMonth, Integer solarDay){
        CalendarInfoVO vo = new CalendarInfoVO();
        CalendarInfo calendarInfo = calendarInfoMapper.queryDayDetail(solarYear, solarMonth, solarDay);
        BeanUtils.copyProperties(calendarInfo, vo);
        return vo;
    }

}
