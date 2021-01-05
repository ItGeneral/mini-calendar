package com.mini.calendar.controller;

import com.alibaba.fastjson.JSON;
import com.mini.calendar.controller.request.CalendarDetailQueryRequest;
import com.mini.calendar.controller.request.CalendarQueryRequest;
import com.mini.calendar.controller.response.BaseResponse;
import com.mini.calendar.controller.vo.CalendarCountDayVO;
import com.mini.calendar.controller.vo.CalendarInfoVO;
import com.mini.calendar.dao.model.CalendarCountDayDTO;
import com.mini.calendar.dao.model.CalendarInfo;
import com.mini.calendar.service.CalendarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author songjiuhua
 * Created by 2020/12/30 10:59
 */
@RestController
@RequestMapping(value = "/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    private static List<String> weekList = Arrays.asList("一",  "二",  "三",  "四",  "五",  "六",  "日");

    /**
     * 获取当月的数据，需要凑满35天
     * @param calendarQueryRequest
     * @return
     */
    @PostMapping(value = "/list")
    public BaseResponse<List<CalendarInfoVO>> getCalendarList(@RequestBody CalendarQueryRequest calendarQueryRequest){
        List<CalendarInfo> calendarInfoList = calendarService.queryCalendarListBySolarYearAndMonth(calendarQueryRequest.getYear(), calendarQueryRequest.getMonth());
        LocalDate localDate = LocalDate.now();
        int month = localDate.getMonth().getValue();
        int year = localDate.getYear();
        int currentDay = localDate.getDayOfMonth();
        List<CalendarInfoVO> result = new ArrayList<>();
        List<CalendarInfoVO> currentList = new ArrayList<>();
        for (CalendarInfo calendarInfo : calendarInfoList) {
            if (calendarInfo.getSolarMonth().equals(calendarQueryRequest.getMonth()) && calendarInfo.getSolarYear().equals(calendarQueryRequest.getYear())){
                CalendarInfoVO vo = new CalendarInfoVO();
                if (currentDay == calendarInfo.getSolarDay() &&
                        calendarInfo.getSolarMonth().equals(month) &&
                        calendarInfo.getSolarYear().equals(year)){
                    vo.setCurrentDay(true);
                }
                BeanUtils.copyProperties(calendarInfo, vo);
                currentList.add(vo);
            }
        }
        //获取前几天的数据
        CalendarInfoVO firstCalendar = currentList.get(0);
        String preWeekDay = firstCalendar.getWeekDay();
        int firstIndex = weekList.indexOf(preWeekDay);
        Integer firstId = firstCalendar.getId();
        List<Integer> preIdList = new ArrayList<>();
        for (int i = 1; i <= firstIndex; i++) {
            preIdList.add(firstId - i);
        }
        //获取后几天的数据
        CalendarInfoVO lastCalendar = currentList.get(currentList.size() - 1);
        String lastWeekDay = lastCalendar.getWeekDay();
        int lastIndex = weekList.indexOf(lastWeekDay);
        List<Integer> lastIdList = new ArrayList<>();
        for (int i = 1; i < 7 - lastIndex; i++) {
            lastIdList.add(lastCalendar.getId() + i);
        }
        if (CollectionUtils.isEmpty(lastIdList) && currentList.size() + preIdList.size() < 35 ){
            for (int i = 1; i <= 7; i++) {
                lastIdList.add(lastCalendar.getId() + i);
            }
        }

        List<CalendarInfoVO> preList = new ArrayList<>();
        List<CalendarInfoVO> lastList = new ArrayList<>();
        for (CalendarInfo calendarInfo : calendarInfoList) {
            if (preIdList.contains(calendarInfo.getId())){
                CalendarInfoVO vo = new CalendarInfoVO();
                BeanUtils.copyProperties(calendarInfo, vo);
                preList.add(vo);
            }else if (lastIdList.contains(calendarInfo.getId())){
                CalendarInfoVO vo = new CalendarInfoVO();
                BeanUtils.copyProperties(calendarInfo, vo);
                lastList.add(vo);
            }
        }
        result.addAll(preList);
        result.addAll(currentList);
        result.addAll(lastList);
        return BaseResponse.success(result);
    }

    /**
     * 获取年/月
     * @return
     */
    @PostMapping(value = "/year/list")
    public BaseResponse<List<List<Integer>>> getYearList(){
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> yearList = calendarService.queryYearList();
        result.add(yearList);
        result.add(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
        return BaseResponse.success(result);
    }

    /**
     * 统计每月有多少天
     * @param request
     * @return
     */
    @PostMapping(value = "/count/day")
    public BaseResponse<CalendarCountDayVO> countDays(@RequestBody CalendarQueryRequest request){
        return BaseResponse.success(calendarService.countMonthDay(request.getYear()));
    }

    /**
     * 查询某日的信息
     * @param request
     * @return
     */
    @PostMapping(value = "/day/detail")
    public BaseResponse<CalendarInfoVO> detail(@RequestBody CalendarDetailQueryRequest request){
        return BaseResponse.success(calendarService.queryDayDetail(request.getSolarYear(), request.getSolarMonth(), request.getSolarDay()));
    }

    @PostMapping(value = "/init/day-picker")
    public BaseResponse<List<List<Integer>>> initDay(@RequestBody CalendarDetailQueryRequest request){
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> yearList = calendarService.queryYearList();
        result.add(yearList);
        result.add(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12));
        //获取某月的天数
        CalendarCountDayDTO calendarCountDayDTO = calendarService.countMonthDayNum(request.getSolarYear(), request.getSolarMonth());
        Integer dayNum = calendarCountDayDTO.getDayNum();
        List<Integer> dayList = new ArrayList<>();
        for (int i = 1; i <= dayNum; i++) {
            dayList.add(i);
        }
        result.add(dayList);
        return BaseResponse.success(result);
    }

}
