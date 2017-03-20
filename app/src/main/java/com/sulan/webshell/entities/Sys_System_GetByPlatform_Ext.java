package com.sulan.webshell.entities;

/**
 * Created by huangsx on 2016/11/7.
 */

public class Sys_System_GetByPlatform_Ext extends BaseRequestParam {
    private String platform = "Platform_Phone";

    public String getPlatform() {
        return platform;
    }

    public Sys_System_GetByPlatform_Ext setPlatform(String platform) {
        this.platform = platform;
        return this;
    }
}
