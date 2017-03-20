package com.sulan.webshell;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sulan.webshell.entities.Sys_System_GetByPlatform_Ext;
import com.sulan.webshell.entities.Sys_System_GetByPlatform_Ext_Resp;
import com.sulan.webshell.network.NetWorkHandler;
import com.sulan.webshell.network.ObjectResponseListener;

/**
 * Created by huangsx on 2016/11/4.
 */

public class MyApplication extends Application {

    private static MyApplication application;

    public static MyApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
//        if (!BuildConfig.DEBUG) {
            AppCrashHandler crashHandler = AppCrashHandler.getInstance();
            crashHandler.setCustomCrashHanler(getApplicationContext());
//        }


        ActiveAndroid.setLoggingEnabled(true);

        Const.getServiceUrl();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getBaseContext()));

        Data.getSystems();
        Data.getCurSystem();

        if (Data.getSystems() == null) {
            refreshSystems(null);
        }
    }

    public void refreshSystems(final ObjectResponseListener<Sys_System_GetByPlatform_Ext_Resp> listener) {
        NetWorkHandler.soapPost(new Sys_System_GetByPlatform_Ext(), new ObjectResponseListener<Sys_System_GetByPlatform_Ext_Resp>() {
            @Override
            public void onSuccess(int tag, Sys_System_GetByPlatform_Ext_Resp response) {
                Data.setSystems(response);
                if (Data.getCurSystem() == null && response.getList() != null && response.getList().size() > 0) {
                    Data.setCurSystem(response.getList().get(0));
                }
                if (listener != null)
                    listener.onSuccess(tag, response);
            }

            @Override
            public void onFailed(int tag, Sys_System_GetByPlatform_Ext_Resp error) {
                if (listener != null)
                    listener.onFailed(tag, error);
            }

            @Override
            public void onFinished(int tag) {
                super.onFinished(tag);
                if (listener != null) {
                    listener.onFinished(tag);
                }
            }
        });
    }
}
