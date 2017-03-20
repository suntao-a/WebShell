package com.sulan.webshell.js.interfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.jsecode.library.utils.GsonUtils;
import com.sulan.webshell.Data;

/**
 * Created by huangsx on 2016/11/8.
 */

public class SystemInfo extends BaseInterface {

    public SystemInfo(Context context) {
        super(context);
    }

    @JavascriptInterface
    public String getSystemInfo() {
        return GsonUtils.toJson(Data.getCurSystem());
    }
}
