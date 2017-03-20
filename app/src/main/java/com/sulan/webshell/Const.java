package com.sulan.webshell;

import com.jsecode.library.utils.PreferencesUtils;

/**
 * Created by huangsx on 2016/11/7.
 */

public class Const {

    // 网络
    public final static String NAME_SPACE = "http://tempuri.org/";
    public final static String ARRAYS_NAME_SPACE = "http://schemas.microsoft.com/2003/10/Serialization/Arrays";
    private static String SERVICE_URL = null;
    //    public static String SERVICE_URL = "http://61.155.6.72:3508/BaseService";
    public static final String SOAP_ACTION = "http://tempuri.org/IOperate_Invoke/Operate_Invoke_Json";
    public static final String METHOD_NAME = "Operate_Invoke_Json";
    public static final String PARAM_NAME = "JsonString";
    public static final String RESPONSE_NAME = "Operate_Invoke_JsonResponse";
    public static final String RESULT_NAME = "Operate_Invoke_JsonResult";
    public static final int REQUEST_TIMEOUT = 30000;

    public static String getServiceUrl() {
        if (SERVICE_URL == null) {
            SERVICE_URL = PreferencesUtils.getDefault(MyApplication.getApplication()).getString("serviceUrl", "http://61.155.238.92:3510/BaseService");
        }
        return SERVICE_URL;
    }

    public static boolean setServiceUrl(String url) {
        if (SERVICE_URL != null && SERVICE_URL.equals(url)) {
            return false;
        }

        SERVICE_URL = url;
        PreferencesUtils.getDefault(MyApplication.getApplication()).edit().putString("serviceUrl", url).apply();
        return true;
    }
}
