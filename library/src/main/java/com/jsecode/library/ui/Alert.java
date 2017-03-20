package com.jsecode.library.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.WindowManager;

import com.jsecode.library.utils.StringUtils;


public class Alert {

    public static AlertDialog showAlert(Context context, String title, String msg, DialogInterface.OnClickListener listener,
                                        boolean cancelable, String positive, String neutral, String negative, OnCancelListener cancelListener, boolean system) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title).setMessage(msg).setCancelable(cancelable);
        if (listener == null) {
            listener = new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        }

        builder.setOnCancelListener(cancelListener);

        if (!StringUtils.isEmpty(positive)) {
            builder = builder.setPositiveButton(positive, listener);
        }
        if (!StringUtils.isEmpty(neutral)) {
            builder = builder.setNeutralButton(neutral, listener);
        }
        if (!StringUtils.isEmpty(negative)) {
            builder = builder.setNegativeButton(negative, listener);
        }

        AlertDialog dialog = builder.create();
        if (system) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
        return dialog;
    }

    public static AlertDialog showAlert(Context context, String title, String msg, DialogInterface.OnClickListener listener,
                                        boolean cancelable, String positive, String neutral, String negative, OnCancelListener cancelListener) {
        return showAlert(context, title, msg, listener,
                cancelable, positive, neutral, negative, cancelListener, false);
    }

    public static AlertDialog showAlert(Context context, String title, String msg) {
        return showAlert(context, title, msg, null, true, "确定", null, null, null);
    }

    public static AlertDialog showAlert(Context context, String title, String msg, DialogInterface.OnClickListener listener) {
        return showAlert(context, title, msg, listener, true, "确定", null, null, null);
    }

    public static AlertDialog showAlert(Context context, String title, String msg, DialogInterface.OnClickListener listener, boolean cancelable) {
        return showAlert(context, title, msg, listener, cancelable, "确定", null, null, null);
    }

    public static AlertDialog showAlert(Context context, String title, String msg, String ok, DialogInterface.OnClickListener listener) {
        return showAlert(context, title, msg, listener, true, ok, null, null, null);
    }

    public static AlertDialog showAlert(Context context, String title, String msg, String ok, String cancel,
                                        DialogInterface.OnClickListener listener) {
        return showAlert(context, title, msg, listener, true, ok, null, cancel, null);
    }

    public static AlertDialog showAlert(Context context, String title, String msg, String ok, String cancel,
                                        DialogInterface.OnClickListener listener, OnCancelListener cancelListener) {
        return showAlert(context, title, msg, listener, true, ok, null, cancel, cancelListener);
    }

    public static AlertDialog showSystemAlert(Context context, String title, String msg) {
        return showAlert(context, title, msg, null, true, "确定", null, null, null, true);
    }

    public static AlertDialog showSystemAlert(Context context, String title, String msg, DialogInterface.OnClickListener listener) {
        return showAlert(context, title, msg, listener, true, "确定", null, null, null, true);
    }

    public static AlertDialog showSystemAlert(Context context, String title, String msg, DialogInterface.OnClickListener listener, boolean cancelable) {
        return showAlert(context, title, msg, listener, cancelable, "确定", null, null, null, true);
    }

    public static AlertDialog showSystemAlert(Context context, String title, String msg, String ok, DialogInterface.OnClickListener listener) {
        return showAlert(context, title, msg, listener, true, ok, null, null, null);
    }

    public static AlertDialog showSystemAlert(Context context, String title, String msg, String ok, String cancel,
                                              DialogInterface.OnClickListener listener) {
        return showAlert(context, title, msg, listener, true, ok, null, cancel, null, true);
    }

    public static AlertDialog showSystemAlert(Context context, String title, String msg, String ok, String cancel,
                                              DialogInterface.OnClickListener listener, OnCancelListener cancelListener) {
        return showAlert(context, title, msg, listener, true, ok, null, cancel, cancelListener, true);
    }

}
