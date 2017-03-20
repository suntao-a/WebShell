package com.sulan.webshell.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sulan.webshell.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 分页列表页面基类
 * Created by huangsx on 16/8/12.
 */

public abstract class BasePagedListActivity extends BaseActivity {
    @Bind(R.id.list)
    ListView mList;
    @Bind(R.id.load_more_container)
    LoadMoreListViewContainer mLoadMoreContainer;
    @Bind(R.id.ptr_frame)
    PtrClassicFrameLayout mPtrFrame;

    protected int pageNo;
    protected int pages;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 0;
                getData(pageNo);
            }
        });

        mLoadMoreContainer.useDefaultFooter();
        mLoadMoreContainer.setAutoLoadMore(true);
        mLoadMoreContainer.setShowLoadingForFirstPage(false);
        mLoadMoreContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mLoadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                pageNo++;
                getData(pageNo);
            }
        });

    }

    protected void autoReload() {
        mPtrFrame.autoRefresh();
    }

    protected void loadComplete() {
        mPtrFrame.refreshComplete();
    }

    protected void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        loadComplete();
        mLoadMoreContainer.loadMoreFinish(emptyResult, hasMore);
    }

    protected void loadMoreError(int errorCode, String errorMessage) {
        loadComplete();
        mLoadMoreContainer.loadMoreError(errorCode, errorMessage);
    }

    protected abstract void getData(int pageNo);

    @OnItemClick(R.id.list)
    public abstract void onItemClick(AdapterView<?> parent, View view, int position, long id);
}
