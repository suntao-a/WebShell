package com.sulan.webshell.js.interfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by huangsx on 2016/11/8.
 */

public class JavaScriptInterface extends BaseInterface {
    public JavaScriptInterface(Context context) {
        super(context);
    }

    @JavascriptInterface
    public void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

}
