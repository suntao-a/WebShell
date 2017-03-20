package com.sulan.webshell.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sulan.webshell.R;
import com.sulan.webshell.activities.MainActivity;
import com.sulan.webshell.base.BaseFragment;
import com.sulan.webshell.js.interfaces.JavaScriptInterface;
import com.sulan.webshell.js.interfaces.LoginInfo;
import com.sulan.webshell.js.interfaces.SystemInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebFrameFragment extends BaseFragment {

    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    private boolean loading;

    public WebFrameFragment() {
        // Required empty public constructor
    }

    public static WebFrameFragment newInstance() {
        WebFrameFragment fragment = new WebFrameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_fram, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mWebView.loadUrl("http://www.baidu.com");
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
                ((MainActivity) getActivity()).getToolbar().setLogo(new BitmapDrawable(getResources(), icon));
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                (getActivity()).setTitle(title);
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
            }
        });
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
        if (mWebView.canGoBack()) {
            ((MainActivity) getActivity()).getToolbar().setNavigationIcon(R.drawable.cube_mints_icon_top_back);
        }

        if (mListener != null) {
            mListener.onFragmentInteraction(this, Uri.parse(url));
        }
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(this, uri);
        }
    }

    public boolean isLoading() {
        return loading;
    }

    public void reload() {
        mWebView.stopLoading();
        mWebView.reload();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWebView.stopLoading();
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        ButterKnife.unbind(this);
    }

    public void goBack() {
        mWebView.stopLoading();
        mWebView.goBack();
        if (!mWebView.canGoBack()) {
            ((MainActivity) getActivity()).getToolbar().setNavigationIcon(null);
        }
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.web_frame, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            mWebView.stopLoading();
            mWebView.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
