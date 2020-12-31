package com.mini.calendar.schedule;

import com.alibaba.fastjson.JSON;
import com.mini.calendar.dao.mapper.CalendarInfoMapper;
import com.mini.calendar.dao.model.CalendarInfo;
import com.mini.calendar.schedule.model.CalendarDTO;
import com.mini.calendar.schedule.model.CalendarData;
import com.mini.calendar.schedule.model.CalendarLoadResponse;
import com.mini.calendar.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author songjiuhua
 * Created by 2020/12/25 15:22
 */
@Component
public class CalendarLoader {

    public static String calendarUrl = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php";

    @Autowired
    protected CalendarInfoMapper calendarInfoMapper;

    public void loadCalendar(){
        List<Integer> monthList = Arrays.asList(2, 5, 8, 11);
        for (int year = 2000; year < 2051; year++){
            for (Integer month : monthList) {
                String query = "?query=" + year + "年" + month + "月&resource_id=39043&t=" + System.currentTimeMillis() + "&ie=utf8&oe=gbk&format=json&tn=wisetpl";
                String invokeUrl = calendarUrl + query;
                String result = HttpUtil.get(invokeUrl);
                CalendarLoadResponse response = JSON.parseObject(result, CalendarLoadResponse.class);
                if (response != null && !CollectionUtils.isEmpty(response.getData())){
                    CalendarData calendarData = response.getData().get(0);
                    List<CalendarDTO> almanacList = calendarData.getAlmanac();
                    if (!CollectionUtils.isEmpty(almanacList)){
                        List<CalendarInfo> calendarInfoList = new ArrayList<>();
                        for (CalendarDTO calendarDTO : almanacList) {
                            CalendarInfo calendarInfo = new CalendarInfo();
                            calendarInfo.setSolarYear(calendarDTO.getYear());
                            calendarInfo.setSolarMonth(calendarDTO.getMonth());
                            calendarInfo.setSolarDay(calendarDTO.getDay());
                            calendarInfo.setLunarYear(calendarDTO.getLunarYear());
                            calendarInfo.setLunarMonth(calendarDTO.getLunarMonth());
                            calendarInfo.setLunarDay(calendarDTO.getLunarDate());
                            calendarInfo.setGzYear(calendarDTO.getGzYear());
                            calendarInfo.setGzMonth(calendarDTO.getGzMonth());
                            calendarInfo.setGzDay(calendarDTO.getGzDate());
                            calendarInfo.setAnimal(calendarDTO.getAnimal());
                            calendarInfo.setAvoid(calendarDTO.getAvoid());
                            calendarInfo.setSuit(calendarDTO.getSuit());
                            calendarInfo.setWeekDay(calendarDTO.getCnDay());
                            calendarInfo.setRemark(calendarDTO.getValue() == null ? "" : calendarDTO.getValue());
                            calendarInfo.setStatus(calendarDTO.getStatus() == null ? 0 : calendarDTO.getStatus());
                            calendarInfoList.add(calendarInfo);
                        }
                        calendarInfoMapper.batchSave(calendarInfoList);
                    }
                }
            }
        }
    }

}
