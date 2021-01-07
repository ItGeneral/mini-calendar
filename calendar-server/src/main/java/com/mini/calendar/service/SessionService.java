package com.mini.calendar.service;

import com.mini.calendar.dao.mapper.CalendarUserMapper;
import com.mini.calendar.dao.model.CalendarUser;
import com.mini.calendar.wx.CodeToSessionHandler;
import com.mini.calendar.wx.model.AuthSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author songjiuhua
 * Created by 2020/12/26 16:06
 */
@Service
public class SessionService {

    @Value("${wx.appid}")
    private String appId;
    @Value("${wx.app-secret}")
    private String appSecret;

    @Autowired
    private CalendarUserMapper userMapper;


    public AuthSession getSession(String code){
        AuthSession session = CodeToSessionHandler.getSession(appId, appSecret, code);
        CalendarUser checkUser = userMapper.queryByOpenIdOrId(0, session.getOpenId());
        if (checkUser == null){
            CalendarUser calendarUser = new CalendarUser();
            calendarUser.setOpenId(session.getOpenId());
            userMapper.saveUser(calendarUser);
        }
        return session;
    }

}
