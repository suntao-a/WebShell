package com.sulan.webshell.entities;

/**
 * Created by huangsx on 2016/11/7.
 */

public class Data_Figure_RT_GetByAlarm extends BaseRequestParam{
    String token;

    public String getToken() {
        return token;
    }

    public Data_Figure_RT_GetByAlarm setToken(String token) {
        this.token = token;
        return this;
    }
}
