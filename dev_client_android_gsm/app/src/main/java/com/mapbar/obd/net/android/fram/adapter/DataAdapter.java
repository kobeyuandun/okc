package com.mapbar.obd.net.android.fram.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yun on 16/1/7.
 */
public abstract class DataAdapter<T> extends BaseAdapter {
    List<T> mData;

    protected DataAdapter(List<T> data) {
        this.mData = data;// TODO: 16/1/11
    }

    public List<T> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    protected abstract View newView(ViewGroup parent);
}
