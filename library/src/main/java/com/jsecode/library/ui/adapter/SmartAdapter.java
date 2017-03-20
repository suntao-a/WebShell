package com.jsecode.library.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by huangsx on 15/8/27.
 */
public abstract class SmartAdapter<T> extends BaseAdapter {

    private List<T> data = new ArrayList<>();

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= 0 && position < getCount())
            return data.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getData() {
        return data;
    }

    public void add(T item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void add(int location, T item) {
        data.add(location, item);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends T> collection) {
        data.addAll(collection);
        notifyDataSetChanged();
    }

    public void addAll(T... array) {
        addAll(Arrays.asList(array));
    }

    public void addAll(int location, Collection<? extends T> collection) {
        data.addAll(location, collection);
        notifyDataSetChanged();
    }

    public void addAll(int location, T... array) {
        addAll(location, Arrays.asList(array));
    }

    public T remove(int location) {
        T item = data.remove(location);
        notifyDataSetChanged();
        return item;
    }

    public boolean remove(T item) {
        boolean result = data.remove(item);
        if (result)
            notifyDataSetChanged();
        return result;
    }

    public boolean removeAll(Collection<? extends T> collection) {
        boolean result = data.removeAll(collection);
        if (result)
            notifyDataSetChanged();
        return result;
    }

    public boolean removeAll(T... array) {
        return removeAll(Arrays.asList(array));
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public int indexOf(Object obj) {
        return data.indexOf(obj);
    }

    public int lastIndexOf(Object obj) {
        return data.lastIndexOf(obj);
    }

    public void setData(List<T> data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data.clear();
        }
        notifyDataSetChanged();
    }

    @SuppressWarnings(value = {"unchecked"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder<T> holder;
        if (convertView == null) {
            holder = createViewHolder();
            holder.setParent(parent);
            convertView = holder.getConvertView();
            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder<T>) convertView.getTag();
        }
        holder.setData(getItem(position));
        return convertView;
    }

    public abstract BaseViewHolder<T> createViewHolder();
}
