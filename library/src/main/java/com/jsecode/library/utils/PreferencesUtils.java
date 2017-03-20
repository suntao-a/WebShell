package com.jsecode.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by huangsx on 2016/11/9.
 */

public class PreferencesUtils {

    public static final Map<String, SharedPreferences> PREFERENCES_MAP = new HashMap<>();

    public static SharedPreferences getSharedPreferences(Context context, String name) {
        SharedPreferences preferences = PREFERENCES_MAP.get(name);
        if (preferences == null) {
            preferences = context.getSharedPreferences(name, MODE_PRIVATE);
            PREFERENCES_MAP.put(name, preferences);
        }
        return preferences;
    }

    public static SharedPreferences getDefault(Context context) {
        return getSharedPreferences(context, "default_preferences.xml");
    }

}
