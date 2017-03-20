package com.jsecode.library.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by huangsx on 15/11/18.
 */
public class UiUtils {
    /**
     * 显示软键盘
     *
     * @param edit
     */
    public static void showKeyBoard(View edit) {
        InputMethodManager m = (InputMethodManager) edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!m.isActive(edit)) {
            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param edit
     */
    public static void hideKeyBoard(View edit) {
        InputMethodManager m = (InputMethodManager) edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (m.isActive() && edit.getWindowToken() != null) {
            m.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
