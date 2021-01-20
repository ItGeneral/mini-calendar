package com.mini.calendar.service;

import com.mini.calendar.controller.request.CalendarDiaryListRequest;
import com.mini.calendar.controller.request.CalendarDiaryQueryRequest;
import com.mini.calendar.controller.request.CalendarDiaryRequest;
import com.mini.calendar.controller.vo.CalendarDiaryVO;
import com.mini.calendar.dao.mapper.CalendarDairyMapper;
import com.mini.calendar.dao.mapper.CalendarInfoMapper;
import com.mini.calendar.dao.mapper.CalendarUserMapper;
import com.mini.calendar.dao.mapper.SpaceDiaryRelateMapper;
import com.mini.calendar.dao.model.CalendarDiary;
import com.mini.calendar.dao.model.CalendarInfo;
import com.mini.calendar.dao.model.CalendarUser;
import com.mini.calendar.dao.model.SpaceDiaryRelate;
import com.mini.calendar.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private SpaceDiaryRelateMapper diaryRelateMapper;

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

        SpaceDiaryRelate diaryRelate = new SpaceDiaryRelate();
        diaryRelate.setUserId(request.getUserId());
        diaryRelate.setDomainSpaceId(request.getSpaceId());
        diaryRelate.setDiaryId(calendarDiary.getId());
        diaryRelateMapper.saveSpaceDiaryRelate(diaryRelate);
        return diaryVO;
    }

    public CalendarDiaryVO queryDiary(CalendarDiaryQueryRequest request){
        CalendarDiary calendarDiary = calendarDairyMapper.queryBySolarDate(request.getSolarDate(), request.getOpenId());
        if (calendarDiary == null){
            return new CalendarDiaryVO();
        }
        CalendarDiaryVO diaryVO = new CalendarDiaryVO();
        BeanUtils.copyProperties(calendarDiary, diaryVO);
        return diaryVO;
    }

    public List<CalendarDiaryVO> queryDiaryList(CalendarDiaryListRequest request){
        Integer offset = request.getPageNo() * request.getPageSize();
        Integer limit = request.getPageSize();
        List<CalendarDiary> calendarDiaryList = calendarDairyMapper.queryDiaryList(request.getOpenId(), offset, limit);
        List<CalendarDiaryVO> voList = new ArrayList<>();
        for (CalendarDiary calendarDiary : calendarDiaryList) {
            CalendarDiaryVO vo = new CalendarDiaryVO();
            Date updateTime = calendarDiary.getUpdateTime();
            vo.setUpdateTimeStr(DateUtil.formatDate(updateTime, DateUtil.TIMESTAMP_CHILD_PATTERN));
            BeanUtils.copyProperties(calendarDiary, vo);
            voList.add(vo);
        }
        return voList;
    }

}
