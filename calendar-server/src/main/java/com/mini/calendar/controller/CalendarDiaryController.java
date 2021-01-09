package com.mini.calendar.controller;

import com.alibaba.fastjson.JSON;
import com.mini.calendar.controller.request.CalendarDiaryListRequest;
import com.mini.calendar.controller.request.CalendarDiaryQueryRequest;
import com.mini.calendar.controller.request.CalendarDiaryRequest;
import com.mini.calendar.controller.response.BaseResponse;
import com.mini.calendar.controller.vo.CalendarDiaryVO;
import com.mini.calendar.service.CalendarDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songjiuhua
 * Created by 2021/1/6 16:37
 */
@RestController
@RequestMapping(value = "/diary")
public class CalendarDiaryController {

    @Autowired
    private CalendarDiaryService calendarDiaryService;

    @PostMapping(value = "/save")
    public BaseResponse<CalendarDiaryVO> saveDiary(@RequestBody CalendarDiaryRequest request){
        if (StringUtils.isEmpty(request.getOpenId())){
            return BaseResponse.fail("01", "参数异常");
        }
        CalendarDiaryVO diaryVO = calendarDiaryService.saveDiary(request);
        return BaseResponse.success(diaryVO);
    }

    @PostMapping(value = "/detail")
    public BaseResponse<CalendarDiaryVO> getDiary(@RequestBody CalendarDiaryQueryRequest request){
        System.out.println(request.getOpenId());
        System.out.println(request.getSolarDate());
        CalendarDiaryVO diaryVO = calendarDiaryService.queryDiary(request);
        System.out.println(JSON.toJSONString(diaryVO));
        return BaseResponse.success(diaryVO);
    }

    @PostMapping(value = "/list")
    public BaseResponse<List<CalendarDiaryVO>> list(@RequestBody CalendarDiaryListRequest request){
        if (StringUtils.isEmpty(request.getOpenId())){
            return BaseResponse.fail("01", "参数异常");
        }
        if (request.getPageNo() == null || request.getPageNo() == 0){
            request.setPageNo(0);
        }else {
            request.setPageNo(request.getPageNo() - 1);
        }
        List<CalendarDiaryVO> voList = calendarDiaryService.queryDiaryList(request);
        return BaseResponse.success(voList);
    }

}
