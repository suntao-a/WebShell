package com.sulan.webshell.entities;

/**
 * Created by huangsx on 2016/11/7.
 */

public class RequestEntity {
    private String Method;
    private BaseRequestParam Params;

    public RequestEntity(BaseRequestParam params) {
        this.Params = params;
        this.Method = params.getClass().getSimpleName();
    }
}
