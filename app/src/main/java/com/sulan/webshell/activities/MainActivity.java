package com.sulan.webshell.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.jsecode.library.utils.Logger;
import com.jsecode.library.utils.StringUtils;
import com.sulan.webshell.Data;
import com.sulan.webshell.R;
import com.sulan.webshell.base.BaseActivity;
import com.sulan.webshell.db.FavoritePage;
import com.sulan.webshell.js.interfaces.JavaScriptInterface;
import com.sulan.webshell.js.interfaces.LoginInfo;
import com.sulan.webshell.js.interfaces.SystemInfo;
import com.sulan.webshell.ui.DummyTabContent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.activeandroid.Cache.getContext;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    public static final int REQUEST_FOR_LOGIN = 0;
    public static final int REQUEST_FOR_SETTING = 1;

    public static final String TAB_HOME = "首页";
    public static final String TAB_ALARM = "报警";
    public static final String TAB_SETTING = "设置";
    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    private MenuItem mAddFavorite;
    private MenuItem mRemoveFavorite;

    @Bind(android.R.id.tabs)
    TabWidget mTabs;
    @Bind(android.R.id.tabcontent)
    FrameLayout mTabContent;
    @Bind(R.id.realtabcontent)
    FrameLayout mRealTabContent;
    @Bind(R.id.tabHost)
    TabHost mTabHost;

    TabHost.TabSpec homeTabSpec;

    private boolean loading;

    private long lastBackPressStamp;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //webFrameFragment = new WebFrameFragment();
        //alarmFragment = new AlarmFragment();
        //settingFragment = new SettingFragment();

        mTabHost.setup();
        homeTabSpec = mTabHost.newTabSpec(TAB_HOME).setIndicator(getIndicatorView(TAB_HOME, R.drawable.home))
                .setContent(new DummyTabContent(this));
        mTabHost.addTab(homeTabSpec);
        //mTabHost.addTab(mTabHost.newTabSpec(TAB_ALARM).setIndicator(getIndicatorView(TAB_ALARM))
        //        .setContent(new DummyTabContent(this)));
        //        mTabHost.addTab(mTabHost.newTabSpec(TAB_SETTING).setIndicator(getIndicatorView(TAB_SETTING))
        //        .setContent(new DummyTabContent(this)));

        mTabs.setVisibility(View.GONE);
        mTabHost.setOnTabChangedListener(this);
        //onTabChanged(TAB_HOME);

        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptInterface(getContext()), "JavaScriptInterface");
        mWebView.addJavascriptInterface(new SystemInfo(getContext()), "SystemInfo");
        mWebView.addJavascriptInterface(new LoginInfo(getContext()), "LoginInfo");

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);

            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                Logger.e(this, "width:" + icon.getWidth() + " height:" + icon.getHeight());
                icon = Bitmap.createScaledBitmap(icon, 48, 48, true);
                Logger.e(this, "===width:" + icon.getWidth() + " height:" + icon.getHeight());
                Drawable drawable = new BitmapDrawable(getResources(), icon);
                getToolbar().setLogo(drawable);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loading = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loading = false;

                if (mWebView.canGoBack()) {
                    getToolbar().setNavigationIcon(R.drawable.nav_back);
                } else {
                    getToolbar().setNavigationIcon(null);
                }
            }
        });

        if (Data.login == null) {
            showActivityForResult(LoginActivity.class, REQUEST_FOR_LOGIN);
        } else {
            onActivityResult(REQUEST_FOR_LOGIN, RESULT_OK, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOR_LOGIN) {
            if (resultCode != RESULT_OK) {
                finish();
                System.exit(0);
            } else {
                if (Data.getCurSystem() != null) {
                    loadUrl(Data.getCurSystem().getSystemFirstPage());
                    //webFrameFragment.loadUrl("http://192.168.0.33/test");
                    reloadTabs();
                }
            }
        } else if (requestCode == REQUEST_FOR_SETTING) {
            if (resultCode == RESULT_OK) {
                Data.login = null;
                showActivityForResult(LoginActivity.class, REQUEST_FOR_LOGIN);
            }
        }
    }

    private View getIndicatorView(String title, @DrawableRes int resId) {
        ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.tab_indicator, null);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        titleView.setText(title);
        icon.setImageResource(resId);
        return view;
    }

    private View getIndicatorView(String title, Bitmap bmp) {
        ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.tab_indicator, null);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        titleView.setText(title);
        icon.setImageBitmap(bmp);
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
//        Fragment fragment = null;
//        if (TAB_HOME.equals(tabId)) {
//            fragment = webFrameFragment;
//        } else if (TAB_ALARM.equals(tabId)) {
//            fragment = alarmFragment;
//        } else if (TAB_SETTING.equals(tabId)) {
//            fragment = settingFragment;
//        }
//        if (fragment != null) {
//            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
//            t.replace(R.id.realtabcontent, fragment);
//            t.commit();
//        }

        if (TAB_HOME.equals(tabId)) {
            loadUrl(Data.getCurSystem().getSystemFirstPage());
        } else {
            try {
                long id = Long.parseLong(tabId);
                FavoritePage page = FavoritePage.load(FavoritePage.class, id);
                loadUrl(page.getUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (canGoBack()) {
            goBack();
        } else {
            if (System.currentTimeMillis() - lastBackPressStamp < 2000) {
                finish();
                System.exit(0);
            } else {
                toast("再按一次退出");
            }
            lastBackPressStamp = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_frame, menu);
        mAddFavorite = menu.findItem(R.id.action_add_favorite);
        mRemoveFavorite = menu.findItem(R.id.action_remove_favorite);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                reload();
                break;
            case R.id.action_setting:
                showActivityForResult(SettingActivity.class, REQUEST_FOR_SETTING);
                break;
            case R.id.action_add_favorite:
                addFavorite();
                break;

            case R.id.action_remove_favorite:
                removeFavorite();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addFavorite() {
        if (isLoading()) {
            toast("页面加载中...");
            return;
        }
        if (mTabHost.getTabWidget().getChildCount() >= 5) {
            toast("收藏夹已满！");
            return;
        }

        LayoutInflater factory = LayoutInflater.from(this);//提示框
        final View view = factory.inflate(R.layout.alert_add_favorite, null);//这里必须是final的
        final EditText edit = (EditText) view.findViewById(R.id.editText);//获得输入框对象

        edit.setText(getCurTitle());

        new AlertDialog.Builder(this)
                .setTitle("请输入收藏标题")
                .setView(view)
                .setPositiveButton("保存",
                        new android.content.DialogInterface.OnClickListener() {

                            private void keepDialogOpen(DialogInterface dialog) {
                                try {
                                    java.lang.reflect.Field field = dialog.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            private void closeDialog(DialogInterface dialog) {
                                try {
                                    java.lang.reflect.Field field = dialog.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialog, true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                keepDialogOpen(dialog);

                                String title = edit.getText().toString();
                                if (StringUtils.isEmptyOrBlank(title)) {
                                    toast("请输入标题");
                                    return;
                                }
                                long id = FavoritePage.add(getCurUrl(), title, getCurIcon());
                                if (id == -1) {
                                    toast("已收藏");
                                } else {
                                    toast("收藏成功");
                                }
                                reloadTabs();
                                mAddFavorite.setVisible(false);
                                mRemoveFavorite.setVisible(true);
                                closeDialog(dialog);

                            }
                        }).setNegativeButton("取消", null).create().show();

    }

    public void removeFavorite() {
        if (isLoading()) {
            toast("页面加载中...");
            return;
        }
        mAddFavorite.setVisible(true);
        mRemoveFavorite.setVisible(false);

        FavoritePage.delete(getCurUrl());

        toast("移除成功");
        reloadTabs();
    }


    public void reloadTabs() {
        mTabHost.setOnTabChangedListener(null);
        mTabHost.setCurrentTab(0);
        mTabHost.clearAllTabs();
        mTabHost.addTab(homeTabSpec);
        List<FavoritePage> list = FavoritePage.findAll();

        for (FavoritePage page : list) {
            mTabHost.addTab(mTabHost.newTabSpec("" + page.getId()).setIndicator(getIndicatorView(page.getTitle(), page.getIcon()))
                    .setContent(new DummyTabContent(this)));
        }
        mTabs.setVisibility(list.size() <= 0 ? View.GONE : View.VISIBLE);
        mTabHost.setOnTabChangedListener(this);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
        FavoritePage page = FavoritePage.find(url);
        mAddFavorite.setVisible(page == null);
        mRemoveFavorite.setVisible(page != null);
    }

    public String getCurTitle() {
        return mWebView.getTitle();
    }

    public Bitmap getCurIcon() {
        return mWebView.getFavicon();
    }

    public String getCurUrl() {
        return mWebView.getUrl();
    }

    public boolean isLoading() {
        return loading;
    }

    public void reload() {
        mWebView.stopLoading();
        mWebView.reload();
    }

    public void goBack() {
        mWebView.stopLoading();
        mWebView.goBack();
        if (!mWebView.canGoBack()) {
            getToolbar().setNavigationIcon(null);
        }
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }
}
