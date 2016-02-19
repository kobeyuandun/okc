package com.mapbar.obd.net.android.fram.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yun on 16/1/12.
 */
public abstract class RecyclerAdapter<T> extends RecyclerDataAdapter<RecyclerAdapter.BaseViewHolder, T> {
    protected RecyclerAdapter(List<T> data) {
        super(data);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = newView();
        return new BaseViewHolder(view);
    }

    protected abstract View newView();

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        doBind(holder.itemView, mDataList.get(position));

    }

    protected abstract void doBind(View itemView, T data);

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public final static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
