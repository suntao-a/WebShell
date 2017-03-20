package com.sulan.webshell;

import android.content.Context;
import android.os.Process;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by suntao-a on 2017/3/20.
 */

public class AppCrashHandler implements Thread.UncaughtExceptionHandler {
    private static AppCrashHandler instance = new AppCrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    private AppCrashHandler() {}
    public static AppCrashHandler getInstance() {
        return instance;
    }
    public void setCustomCrashHanler(Context context) {
        mContext = context;
        //崩溃时将catch住异常
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //友盟上报日志
        MobclickAgent.reportError(mContext, e);
        if(null != defaultUncaughtExceptionHandler)
            defaultUncaughtExceptionHandler.uncaughtException(t, e);

        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
