package com.jsecode.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jsecode.library.R;

/**
 * Created by hohaiuhsx on 15/3/6.
 */
public class PackageUtils {

    // Method
    private static PackageInfo packageInfo;

    /**
     * 获取 R.string.app_name
     *
     * @param context context
     * @return appName
     */
    public static String appName(Context context) {

        if (context != null) {
            return context.getString(R.string.app_name);
        }
        return "";
    }

    /**
     * 获取VersionName
     *
     * @param context context
     * @return versionName
     */
    public static String appVersion(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 获取VersionCode
     *
     * @param context context
     * @return versionCode
     */
    public static int appVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 获取PackageInfo
     *
     * @param context context
     * @return PackageInfo
     */
    public static PackageInfo getPackageInfo(Context context) {

        if (packageInfo == null) {

            try {
                PackageManager manager = context.getPackageManager();
                packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                Logger.e(PackageUtils.class, e);
            }
        }
        return packageInfo;
    }
}
