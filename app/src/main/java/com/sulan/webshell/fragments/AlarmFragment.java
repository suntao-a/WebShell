package com.sulan.webshell.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sulan.webshell.Data;
import com.sulan.webshell.R;
import com.sulan.webshell.base.BaseFragment;
import com.sulan.webshell.entities.BaseResponse;
import com.sulan.webshell.entities.Data_Figure_RT_GetByAlarm;
import com.sulan.webshell.network.NetWorkHandler;
import com.sulan.webshell.network.ObjectResponseListener;

public class AlarmFragment extends BaseFragment {

    public AlarmFragment() {
        // Required empty public constructor
    }

    public static AlarmFragment newInstance(String param1, String param2) {
        AlarmFragment fragment = new AlarmFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(this, uri);
        }
    }

    public void loadData() {
        showProgress();
        NetWorkHandler.soapPost(new Data_Figure_RT_GetByAlarm().setToken(Data.login.getTokenString()), new ObjectResponseListener<BaseResponse>() {
            @Override
            public void onSuccess(int tag, BaseResponse response) {

            }

            @Override
            public void onFailed(int tag, BaseResponse error) {

            }

            @Override
            public void onFinished(int tag) {
                super.onFinished(tag);
                dismissProgress();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.alarm, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
