package com.mini.calendar.service;

import com.mini.calendar.wx.CodeToSessionHandler;
import com.mini.calendar.wx.model.AuthSession;
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


    public AuthSession getSession(String code){
        return CodeToSessionHandler.getSession(appId, appSecret, code);
    }

}
