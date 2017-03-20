package com.sulan.webshell.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.jsecode.library.utils.DataTransfer;
import com.jsecode.library.utils.Logger;
import com.loopj.android.http.RequestHandle;
import com.sulan.webshell.ui.MyProgressDialog;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by huangsx on 16/8/12.
 */

public abstract class BaseFragment extends Fragment {

    protected OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private Intent intent(Class<?> activity, Object... values) {
        Intent i = new Intent(getContext(), activity);
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
        getActivity().finish();
        showActivity(activity, values);
    }

    public void toast(@StringRes int res) {
        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
    }

    public void toast(CharSequence character) {
        Toast.makeText(getContext(), character, Toast.LENGTH_SHORT).show();
    }

    public void toastLong(@StringRes int res) {
        Toast.makeText(getContext(), res, Toast.LENGTH_SHORT).show();
    }

    public void toastLong(CharSequence character) {
        Toast.makeText(getContext(), character, Toast.LENGTH_SHORT).show();
    }

    public MyProgressDialog showProgress() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).showProgress();
        } else {
            Logger.e(this, "this fragment's Activity not extends BaseActivity!");
            return null;
        }
    }

    public MyProgressDialog showProgress(final RequestHandle handle) {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).showProgress(handle);
        } else {
            Logger.e(this, "this fragment's Activity not extends BaseActivity!");
            return null;
        }
    }

    public void dismissProgress() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).dismissProgress();
        } else {
            Logger.e(this, "this fragment's Activity not extends BaseActivity!");
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getTag());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getTag());
    }
}
