package com.mini.calendar.controller;

import com.mini.calendar.controller.request.SessionRequest;
import com.mini.calendar.controller.response.BaseResponse;
import com.mini.calendar.service.SessionService;
import com.mini.calendar.wx.model.AuthSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songjiuhua
 * Created by 2020/12/26 15:55
 */
@RestController
@RequestMapping(value = "/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping(value = "/key")
    public BaseResponse loadSession(@RequestBody SessionRequest sessionRequest){
        AuthSession session = sessionService.getSession(sessionRequest.getCode());
        return BaseResponse.success(session);
    }


}
