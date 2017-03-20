package com.jsecode.library.utils;

import android.graphics.Color;

/**
 * Created by huangsx on 15/6/16.
 */
public class ColorUtils {

    /**
     * 生成Pressed状态的颜色
     *
     * @param color 原色
     * @return pressedColor
     */
    public static int getPressedColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = hsv[2] * 0.7f;
        return Color.HSVToColor(hsv);
    }
}
