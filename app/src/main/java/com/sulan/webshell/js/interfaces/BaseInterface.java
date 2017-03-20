package com.sulan.webshell.js.interfaces;

import android.content.Context;

/**
 * Created by huangsx on 2016/11/8.
 */

public class BaseInterface {
    protected Context mContext;

    public BaseInterface(Context context) {
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    public BaseInterface setmContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }
}
