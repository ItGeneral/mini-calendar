package com.mini.calendar.controller;

import com.mini.calendar.controller.request.CalendarDiaryQueryRequest;
import com.mini.calendar.controller.request.CalendarDiaryRequest;
import com.mini.calendar.controller.response.BaseResponse;
import com.mini.calendar.controller.vo.CalendarDiaryVO;
import com.mini.calendar.service.CalendarDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "/detail/{id}")
    public BaseResponse getDiary(@PathVariable("id") Integer id, @RequestBody CalendarDiaryQueryRequest request){
        CalendarDiaryVO diaryVO = calendarDiaryService.queryDiary(request);
        return BaseResponse.success(diaryVO);
    }

}
