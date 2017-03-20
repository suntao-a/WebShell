package com.jsecode.library.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class Hud {

    private static ProgressDialog hud;

    public static synchronized ProgressDialog showHud(Context ctx, String title, String msg) {

        if (hud == null || hud.getContext() != ctx) {
            hud = new ProgressDialog(ctx, ProgressDialog.THEME_HOLO_DARK);
        }
        hud.setTitle(title);
        hud.setMessage(msg);

        assert ctx != null;
        if (ctx instanceof Activity && ((Activity) ctx).getWindow().isActive()) {
            hud.show();
        }
        return hud;
    }

    public static synchronized void hideHud() {
        if (hud != null) {
            try {
                hud.dismiss();
            } catch (Exception e) {
                hud = null;
                e.printStackTrace();
            }
        }
    }

    public static synchronized void showLoadingHud(Context context) {
        showHud(context, "", "正在加载...");
    }

}
