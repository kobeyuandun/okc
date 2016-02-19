package com.mapbar.obd.net.android.fram.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by yun on 16/1/12.
 */
public abstract class RecyclerDataAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {
    protected List<T> mDataList;

    protected RecyclerDataAdapter(List<T> data) {
        this.mDataList = data;
    }
}
