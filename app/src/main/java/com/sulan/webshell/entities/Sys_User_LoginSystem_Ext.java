package com.sulan.webshell.entities;

/**
 * Created by huangsx on 2016/11/7.
 */

public class Sys_User_LoginSystem_Ext extends BaseRequestParam {

    private String systemID;
    private String userName;
    private String password;

    public String getSystemID() {
        return systemID;
    }

    public Sys_User_LoginSystem_Ext setSystemID(String systemID) {
        this.systemID = systemID;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Sys_User_LoginSystem_Ext setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Sys_User_LoginSystem_Ext setPassword(String password) {
        this.password = password;
        return this;
    }
}
