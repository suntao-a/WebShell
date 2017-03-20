package com.sulan.webshell;

import com.jsecode.library.utils.GsonUtils;
import com.jsecode.library.utils.PreferencesUtils;
import com.sulan.webshell.entities.Sys_System_GetByPlatform_Ext_Resp;
import com.sulan.webshell.entities.Sys_User_LoginSystem_Ext_Resp;

/**
 * Created by huangsx on 2016/11/7.
 */

public class Data {
    private static Sys_System_GetByPlatform_Ext_Resp systems;
    private static Sys_System_GetByPlatform_Ext_Resp.ListEntity curSystem;
    public static Sys_User_LoginSystem_Ext_Resp login;

    public static boolean setSystems(Sys_System_GetByPlatform_Ext_Resp resp) {
        if (systems != null && (systems == resp || GsonUtils.toJson(systems).equals(GsonUtils.toJson(resp)))) {
            return false;
        }
        systems = resp;
        PreferencesUtils.getDefault(MyApplication.getApplication()).edit().putString("systems", GsonUtils.toJson(systems)).apply();
        return true;
    }

    public static Sys_System_GetByPlatform_Ext_Resp getSystems() {
        if (systems == null) {
            String systems = PreferencesUtils.getDefault(MyApplication.getApplication()).getString("systems", null);
            if (systems != null) {
                Data.systems = GsonUtils.fromJson(systems, Sys_System_GetByPlatform_Ext_Resp.class);
            }
        }
        return systems;
    }

    public static boolean setCurSystem(Sys_System_GetByPlatform_Ext_Resp.ListEntity entity) {
        if (curSystem != null && (curSystem == entity || GsonUtils.toJson(curSystem).equals(GsonUtils.toJson(entity)))) {
            return false;
        }
        curSystem = entity;
        PreferencesUtils.getDefault(MyApplication.getApplication()).edit().putString("curSystem", GsonUtils.toJson(curSystem)).apply();
        return true;
    }

    public static Sys_System_GetByPlatform_Ext_Resp.ListEntity getCurSystem() {
        if (curSystem == null) {
            String curSystem = PreferencesUtils.getDefault(MyApplication.getApplication()).getString("curSystem", null);
            if (curSystem != null) {
                Data.curSystem = GsonUtils.fromJson(curSystem, Sys_System_GetByPlatform_Ext_Resp.ListEntity.class);
            }
        }
        return curSystem;
    }
}
