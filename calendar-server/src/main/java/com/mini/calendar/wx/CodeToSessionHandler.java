package com.mini.calendar.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mini.calendar.util.HttpUtil;
import com.mini.calendar.wx.model.AuthSession;

/**
 * @author songjiuhua
 * Created by 2020/12/26 14:41
 */
public class CodeToSessionHandler {

    private static final String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    /**
     *
     * @param appId 小程序appId
     * @param appSecret 小程序appSecret
     * @param code 调用wx.login登录成功后，返回的code
     * @return
     */
    public static AuthSession getSession(String appId, String appSecret, String code){
        String url = code2SessionUrl + "&appid=" + appId + "&secret=" + appSecret + "&js_code=" + code;
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(result);
        AuthSession authSession = new AuthSession();
        authSession.setOpenid(jsonObject.getString("openid"));
        authSession.setSessionKey(jsonObject.getString("session_key"));
        authSession.setUnionId(jsonObject.getString("unionid"));
        return authSession;
    }


}
