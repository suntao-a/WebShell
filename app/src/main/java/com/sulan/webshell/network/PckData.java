package com.sulan.webshell.network;

import com.jsecode.library.utils.DateUtils;
import com.jsecode.library.utils.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


public final class PckData {

    private PckData() {
    }

    private static String pack(String cmd, JSONObject params) {
        JSONObject json = new JSONObject();
        try {
            json.put("Method", cmd);
            json.put("Params", params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    private static String pack2(String cmd, String[] keys, Object[] values) {
        JSONObject json = params(keys, values);
        try {
            json.put("cmd", cmd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static JSONObject params(String[] keys, Object[] values) {
        JSONObject params = new JSONObject();
        if (keys == null || values == null) {
            return params;
        }
        if (keys.length != values.length) {
            Logger.e(PckData.class, "参数不正确");
            return params;
        }
        try {
            int length = keys.length;
            for (int i = 0; i < length; i++) {
                if (values[i] != null)
                    params.put(keys[i], values[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public static JSONArray array(List<?> objects) {
        JSONArray array = new JSONArray(objects);
        return array;
    }

    public static JSONArray array(Object... objects) {
        return array(Arrays.asList(objects));
    }

    public static String[] keys(String... keys) {
        return keys;
    }

    public static Object[] values(Object... objects) {
        return objects;
    }

    /**********************************************************************************************************/
    /*********************************************** 华丽的分割线 *********************************************/
    /**********************************************************************************************************/
    /********************************************** 以下为业务接口 ********************************************/
    /**********************************************************************************************************/

    /**
     * @return 获取系统信息接口
     */
    public static String Sys_System_GetByPlatform_Ext() {
        return pack("Sys_System_GetByPlatform_Ext", params(keys("platform"), values("Platform_Phone")));
    }

    public static String Sys_User_LoginSystem_Ext(String systemID,String userName,String password){
        return pack("Sys_User_LoginSystem_Ext",params(keys("systemID","userName","password"),values()));
    }


    public static final String GetStationInfos() {
        return pack("GetStationInfos", new JSONObject());
    }

    public static final String GetSensorInfos() {
        return pack("GetSensorInfos", new JSONObject());
    }

    public static final String GetSensorMetaDatas(List<String> sensorIds, Date date) {
        return pack(
                "GetSensorMetaDatas",
                params(keys("beginTime", "endTime", "dataIdList"),
                        values(DateUtils.getDate(date) + " 00:00:00", DateUtils.getDate(date) + " 23:59:59",
                                array(sensorIds))));
    }

}
