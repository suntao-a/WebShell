package com.sulan.webshell.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jsecode.library.ui.Alert;
import com.jsecode.library.utils.DataTransfer;
import com.jsecode.library.utils.Logger;
import com.loopj.android.http.RequestHandle;
import com.sulan.webshell.MyApplication;
import com.sulan.webshell.R;
import com.sulan.webshell.ui.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * 页面基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static Handler staticHandler = new Handler();

    protected Toolbar toolbar;
    protected FrameLayout contentContainer;

    private MyProgressDialog mProgressDialog;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        contentContainer = (FrameLayout) findViewById(R.id.contentContainer);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsRelative(0, 0);
        getLayoutInflater().inflate(layoutResID, contentContainer, true);

        if (isTaskRoot()) {
            toolbar.setNavigationIcon(null);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private Intent intent(Class<?> activity, Object... values) {
        Intent i = new Intent(this, activity);
        if (values != null && values.length > 0) {
            Bundle bundle = DataTransfer.getInstance().putForBundle(this, values);
            i.putExtras(bundle);
        }
        return i;
    }

    public void showActivity(Class<?> activity) {
        showActivity(activity, new Object[]{});
    }

    public void showActivity(Class<?> activity, Object... values) {
        startActivity(intent(activity, values));
    }

    public void showActivityForResult(Class<?> activity, int requestCode) {
        showActivityForResult(activity, requestCode, new Object[]{});
    }

    public void showActivityForResult(Class<?> activity, int requestCode, Object... values) {
        startActivityForResult(intent(activity, values), requestCode);
    }

    public void jump2Activity(Class<?> activity) {
        jump2Activity(activity, new Object[]{});
    }

    public void jump2Activity(Class<?> activity, Object... values) {
        finish();
        showActivity(activity, values);
    }

    public void toast(@StringRes int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    public void toast(CharSequence character) {
        Toast.makeText(this, character, Toast.LENGTH_SHORT).show();
    }

    public void toastLong(@StringRes int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    public void toastLong(CharSequence character) {
        Toast.makeText(this, character, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public MyProgressDialog showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new MyProgressDialog(this);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
        return mProgressDialog;
    }

    public MyProgressDialog showProgress(final RequestHandle handle) {
        MyProgressDialog dialog = showProgress();
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (handle != null && !handle.isCancelled() && !handle.isFinished())
                    handle.cancel(true);
            }
        });
        return dialog;
    }

    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public List<?> getTransferDataBundle(Intent intent) {
        return DataTransfer.getAndRemove(intent);
    }

    public MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    @SuppressWarnings("unchecked")
    public <T> T getTransferData(Intent intent) {
        List<?> list = getTransferDataBundle(intent);
        Logger.e(this, "list:" + list);
        if (list != null && list.size() > 0) {
            return (T) list.get(0);
        }
        return null;
    }

    public void alertRetry(String msg, final Runnable positive) {
        Alert.showAlert(BaseActivity.this,
                "提示", msg, "重试", "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            positive.run();
                        } else {
                            finish();
                        }
                    }
                }).setCancelable(false);
    }

    public void alertError(String msg) {
        Alert.showAlert(BaseActivity.this,
                "提示", msg, "确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false);
    }

}
