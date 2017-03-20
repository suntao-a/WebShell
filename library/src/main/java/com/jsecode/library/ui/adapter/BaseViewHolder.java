package com.jsecode.library.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by huangsx on 15/10/9.
 */
public abstract class BaseViewHolder<T> {

    private View convertView;
    private T data;

    public void setParent(ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(reusableLayoutRes(), parent, false);
        ButterKnife.bind(this, convertView);
    }

    public View getConvertView() {
        return convertView;
    }

    @SuppressWarnings("unchecked")
    public <A extends Context> A getContext() {
        return (A) getConvertView().getContext();
    }

    public abstract int reusableLayoutRes();

    public void setData(T data) {
        this.data = data;
        fillData(data);
    }

    public T getData() {
        return data;
    }

    public abstract void fillData(T data);
}
