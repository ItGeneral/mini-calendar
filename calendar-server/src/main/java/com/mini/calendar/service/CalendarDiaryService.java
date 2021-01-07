package com.mini.calendar.service;

import com.mini.calendar.controller.request.CalendarDiaryQueryRequest;
import com.mini.calendar.controller.request.CalendarDiaryRequest;
import com.mini.calendar.controller.vo.CalendarDiaryVO;
import com.mini.calendar.dao.mapper.CalendarDairyMapper;
import com.mini.calendar.dao.mapper.CalendarInfoMapper;
import com.mini.calendar.dao.mapper.CalendarUserMapper;
import com.mini.calendar.dao.model.CalendarDiary;
import com.mini.calendar.dao.model.CalendarInfo;
import com.mini.calendar.dao.model.CalendarUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author songjiuhua
 * Created by 2021/1/6 16:43
 */
@Service
public class CalendarDiaryService {

    @Autowired
    private CalendarDairyMapper calendarDairyMapper;
    @Autowired
    private CalendarUserMapper calendarUserMapper;
    @Autowired
    private CalendarInfoMapper calendarInfoMapper;

    public CalendarDiaryVO saveDiary(CalendarDiaryRequest request){
        String openId = request.getOpenId();
        CalendarUser calendarUser = calendarUserMapper.queryByOpenIdOrId(0, openId);
        String[] solarDateArr = request.getSolarDate().split("-");
        CalendarInfo calendarInfo = calendarInfoMapper.queryDayDetail(Integer.valueOf(solarDateArr[0]),
                Integer.valueOf(solarDateArr[1]), Integer.valueOf(solarDateArr[2]));
        CalendarDiary existDiary = calendarDairyMapper.queryByOpenIdAndCalendarId(openId, calendarInfo.getId());
        CalendarDiary calendarDiary = new CalendarDiary();
        calendarDiary.setTitle(request.getTitle());
        calendarDiary.setContent(request.getContent());
        calendarDiary.setCalendarInfoId(calendarInfo.getId());
        calendarDiary.setUserId(calendarUser.getId());
        calendarDiary.setOpenId(calendarUser.getOpenId());
        calendarDiary.setSolarDate(request.getSolarDate());
        calendarDiary.setUpdateTime(new Date());
        if (existDiary != null && existDiary.getId() != null){
            calendarDiary.setId(existDiary.getId());
            calendarDairyMapper.updateDairy(calendarDiary);
        }else {
            calendarDairyMapper.saveDairy(calendarDiary);
        }
        CalendarDiaryVO diaryVO = new CalendarDiaryVO();
        BeanUtils.copyProperties(calendarDiary, diaryVO);
        return diaryVO;
    }

    public CalendarDiaryVO queryDiary(CalendarDiaryQueryRequest request){
        CalendarDiary calendarDiary = calendarDairyMapper.queryBySolarDate(request.getSolarDate(), request.getOpenId());
        CalendarDiaryVO diaryVO = new CalendarDiaryVO();
        BeanUtils.copyProperties(calendarDiary, diaryVO);
        return diaryVO;
    }

}
