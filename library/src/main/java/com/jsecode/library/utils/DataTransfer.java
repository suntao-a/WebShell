package com.jsecode.library.utils;

import android.content.Intent;
import android.os.Bundle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class DataTransfer {

    public static final String DATA_KEY = "data_transfer_data_key";

    // 单例模式实例
    private static DataTransfer instance = null;
    private final HashMap<String, Object> mMap;

    // synchronized 用于线程安全，防止多线程同时创建实例
    public synchronized static DataTransfer getInstance() {
        if (instance == null) {
            instance = new DataTransfer();
        }
        return instance;
    }

    private DataTransfer() {
        mMap = new HashMap<String, Object>();
    }

    public String putForKey(Object provider, Object... values) {
        String key = String.format("%s-%s", provider.getClass().getSimpleName(), UUID22.getUUID22());
        mMap.put(key, values);
        return key;
    }

    public Bundle putForBundle(Object provider, Object... values) {
        String key = putForKey(provider, values);
        Bundle bundle = new Bundle();
        bundle.putString(DATA_KEY, key);
        return bundle;
    }


    public <T> List<T> getAndRemoveByKey(String key) {
        if (key == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] data = (T[]) mMap.remove(key);
        if (data != null) {
            return Arrays.asList(data);
        }
        return null;
    }

    public <T> List<T> getAndRemoveByBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String key = bundle.getString(DATA_KEY);
        return getAndRemove(key);
    }

    public <T> List<T> getAndRemoveByIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        String key = intent.getStringExtra(DATA_KEY);
        return getAndRemove(key);
    }

    public static <T> List<T> getAndRemove(String key) {
        return getInstance().getAndRemoveByKey(key);
    }

    public static <T> List<T> getAndRemove(Bundle bundle) {
        return getInstance().getAndRemoveByBundle(bundle);
    }

    public static <T> List<T> getAndRemove(Intent intent) {
        return getInstance().getAndRemoveByIntent(intent);
    }

    public static <T> T getAndRemoveObject(Intent intent) {
        List<T> list = getAndRemove(intent);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
