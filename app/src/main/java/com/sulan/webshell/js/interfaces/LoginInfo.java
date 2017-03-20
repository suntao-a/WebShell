package com.sulan.webshell.js.interfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.sulan.webshell.Data;

/**
 * Created by huangsx on 2016/11/8.
 */

public class LoginInfo extends BaseInterface {
    public LoginInfo(Context context) {
        super(context);
    }

    @JavascriptInterface
    public String getLoginInfo() {
        return Data.login.toJson();
    }
}
