package com.sulan.webshell.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jsecode.library.utils.PreferencesUtils;
import com.jsecode.library.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sulan.webshell.Data;
import com.sulan.webshell.R;
import com.sulan.webshell.base.BaseActivity;
import com.sulan.webshell.entities.Sys_User_LoginSystem_Ext;
import com.sulan.webshell.entities.Sys_User_LoginSystem_Ext_Resp;
import com.sulan.webshell.network.NetWorkHandler;
import com.sulan.webshell.network.ObjectResponseListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_SAVE_PWD = "save_password";

    @Bind(R.id.login_progress)
    ProgressBar mLoginProgress;
    @Bind(R.id.username)
    EditText mUsername;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.sign_in_button)
    Button mSignInButton;
    @Bind(R.id.email_login_form)
    LinearLayout mEmailLoginForm;
    @Bind(R.id.login_form)
    ScrollView mLoginForm;
    @Bind(R.id.logo)
    ImageView mLogo;
    @Bind(R.id.company)
    TextView mCompany;
    @Bind(R.id.check_save_password)
    CheckBox mCheckSavePwd;
    @Bind(R.id.systemIcon)
    ImageView mSystemIcon;
    @Bind(R.id.systemName)
    TextView mSystemName;
    @Bind(R.id.btnSetting)
    ImageButton mBtnSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        toolbar.setNavigationIcon(null);
        toolbar.setVisibility(View.GONE);

        mPassword = (EditText) findViewById(R.id.password);
        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mUsername.setText(PreferencesUtils.getDefault(this).getString(KEY_USERNAME, ""));
        if (PreferencesUtils.getDefault(this).getBoolean(KEY_SAVE_PWD, false)) {
            mPassword.setText(PreferencesUtils.getDefault(this).getString(KEY_PASSWORD, ""));
            mCheckSavePwd.setChecked(true);
        }

        if (Data.getCurSystem() == null) {
            toast("请设置需要登录的系统");
            showActivity(SettingActivity.class);
        } else {
            setTitle(Data.getCurSystem().getSystemName());
            mSystemName.setText(Data.getCurSystem().getSystemName());
            ImageLoader.getInstance().displayImage(Data.getCurSystem().getSystemLogo(), mSystemIcon);
            mCompany.setText(Data.getCurSystem().getManufacturer());
            ImageLoader.getInstance().displayImage(
                    Data.getCurSystem().getManufacturerLogo()
                    , mLogo);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Data.getCurSystem() != null) {
            setTitle(Data.getCurSystem().getSystemName());
            mSystemName.setText(Data.getCurSystem().getSystemName());
            ImageLoader.getInstance().displayImage(Data.getCurSystem().getSystemLogo(), mSystemIcon);
            mCompany.setText(Data.getCurSystem().getManufacturer());
            ImageLoader.getInstance().displayImage(
                    Data.getCurSystem().getManufacturerLogo()
                    , mLogo);
        }
    }

    @OnCheckedChanged(R.id.check_save_password)
    void onCheckChanged(CompoundButton btn, boolean checked) {
        PreferencesUtils.getDefault(this).edit()
                .putBoolean(KEY_SAVE_PWD, checked)
                .apply();
    }

    @OnClick(R.id.btnSetting)
    void settingClick() {
        showActivity(SettingActivity.class);
    }

    @OnClick(R.id.sign_in_button)
    void attemptLogin() {

        if (Data.getCurSystem() == null) {
            toast("请设置需要登录的系统");
            showActivity(SettingActivity.class);
            return;
        }

        // Reset errors.
        mUsername.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            NetWorkHandler.soapPost(new Sys_User_LoginSystem_Ext()
                            .setSystemID(Data.getCurSystem().getSystemID())
                            .setUserName(username)
                            .setPassword(StringUtils.getMD5Str(password))
                            .setPassword(password)
                    , new ObjectResponseListener<Sys_User_LoginSystem_Ext_Resp>() {
                        @Override
                        public void onSuccess(int tag, Sys_User_LoginSystem_Ext_Resp response) {
                            if (response != null) {
                                Data.login = response;
                                setResult(RESULT_OK);
                                PreferencesUtils.getDefault(LoginActivity.this)
                                        .edit()
                                        .putString(KEY_USERNAME, username)
                                        .putString(KEY_PASSWORD, password)
                                        .apply();
                                finish();
                            } else {
                                toast("登录失败，请检查用户名密码是否正确");
                            }
                        }

                        @Override
                        public void onFailed(int tag, Sys_User_LoginSystem_Ext_Resp error) {
                            toast(error.getNote());
                        }

                        @Override
                        public void onFinished(int tag) {
                            super.onFinished(tag);
                            showProgress(false);
                        }
                    });
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 1;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_setting) {
            showActivity(SettingActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

