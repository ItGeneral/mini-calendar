package com.mini.calendar.service;

import com.mini.calendar.dao.mapper.CalendarUserMapper;
import com.mini.calendar.dao.model.CalendarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author songjiuhua
 * Created by 2021/1/6 15:51
 */
@Service
public class CalendarUserService {

    @Autowired
    private CalendarUserMapper calendarUserMapper;

    public void saveUser(CalendarUser user){
        calendarUserMapper.saveUser(user);
    }

    public CalendarUser queryByOpenIdOrId(Integer id, String openId){
        return calendarUserMapper.queryByOpenIdOrId(id, openId);
    }

}
