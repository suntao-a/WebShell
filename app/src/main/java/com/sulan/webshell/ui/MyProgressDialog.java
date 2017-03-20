package com.sulan.webshell.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.sulan.webshell.R;

/**
 * Created by huangsx on 15/10/30.
 */
public class MyProgressDialog extends AlertDialog {

    private TextView textView;

    public MyProgressDialog(Context context) {
        super(context, R.style.Theme_AppCompat_Dialog_Alert_Translate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        textView = (TextView) findViewById(R.id.text);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setText(int resId) {
        textView.setText(resId);
    }
}
