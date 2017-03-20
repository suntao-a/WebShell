package com.sulan.webshell.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jsecode.library.utils.StringUtils;
import com.sulan.webshell.Const;
import com.sulan.webshell.Data;
import com.sulan.webshell.MyApplication;
import com.sulan.webshell.R;
import com.sulan.webshell.base.BaseActivity;
import com.sulan.webshell.entities.Sys_System_GetByPlatform_Ext_Resp;
import com.sulan.webshell.network.ObjectResponseListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.service_url)
    EditText mServiceUrl;
    @Bind(R.id.default_system)
    TextView mDefaultSystem;
    @Bind(R.id.systems)
    Spinner mSystems;
    @Bind(R.id.btnRefreshSystems)
    Button mBtnRefreshSystems;
    @Bind(R.id.btnSaveSettings)
    Button mBtnSaveSettings;
    @Bind(R.id.activity_setting)
    RelativeLayout mActivitySetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        mServiceUrl.setText(Const.getServiceUrl());
        if(Data.getSystems()!=null){
            ArrayAdapter<Sys_System_GetByPlatform_Ext_Resp.ListEntity> adapter =
                    new ArrayAdapter<>(this,
                            android.R.layout.simple_dropdown_item_1line,Data.getSystems().getList());

            mSystems.setAdapter(adapter);

            mSystems.setSelection(Data.getSystems().findIndex(Data.getCurSystem()));
        }

    }

    public void initSystems() {
        showProgress();

        MyApplication.getApplication().refreshSystems(new ObjectResponseListener<Sys_System_GetByPlatform_Ext_Resp>() {
            @Override
            public void onSuccess(int tag, Sys_System_GetByPlatform_Ext_Resp response) {
                ArrayAdapter<Sys_System_GetByPlatform_Ext_Resp.ListEntity> adapter =
                        new ArrayAdapter<>(SettingActivity.this,
                                android.R.layout.simple_dropdown_item_1line, Data.getSystems().getList());

                mSystems.setAdapter(adapter);
            }

            @Override
            public void onFailed(int tag, Sys_System_GetByPlatform_Ext_Resp error) {
                alertRetry(error.getNote(), new Runnable() {
                    @Override
                    public void run() {
                        initSystems();
                    }
                });
            }

            @Override
            public void onFinished(int tag) {
                super.onFinished(tag);
                dismissProgress();
            }
        });
    }

    @OnClick(R.id.btnSaveSettings)
    public void onButtonSave() {
        String serviceUrl = mServiceUrl.getText().toString();
        if (StringUtils.isEmptyOrBlank(serviceUrl)) {
            toast("请输入服务器地址");
            return;
        }
        if (!serviceUrl.startsWith("http://") && !serviceUrl.startsWith("https://")) {
            serviceUrl = "http://" + serviceUrl;
        }
        boolean changed = Const.setServiceUrl(serviceUrl);

        Sys_System_GetByPlatform_Ext_Resp.ListEntity entity = (Sys_System_GetByPlatform_Ext_Resp.ListEntity) mSystems.getSelectedItem();
        changed |= Data.setCurSystem(entity);

        if (changed) {
            toast("设置已保存");
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            toast("设置未变更");
        }
    }

    @OnClick(R.id.btnRefreshSystems)
    public void onBtnRefresh() {
        initSystems();
    }

}
