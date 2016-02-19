package com.mapbar.obd.net.android.fram.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mapbar.obd.net.android.fram.BaseController;
import com.mapbar.obd.net.android.fram.BaseView;

import java.util.List;

/**
 * Created by yun on 16/1/12.
 */
public class RecyclerAdapterController<T> extends RecyclerDataAdapter<RecyclerAdapterController.BaseViewHolder, T> {
    public RecyclerAdapterController(List<T> data) {
        super(data);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseController controller;
        BaseView baseView;
        controller = newController(viewType);
        baseView = newView(parent, viewType);
        return new BaseViewHolder(baseView.getView(), controller);
    }

    private BaseView newView(ViewGroup parent, int viewType) {
        return null;
    }

    private BaseController newController(int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        dobind(holder.itemView, holder.mController, mDataList.get(position));
    }

    private void dobind(View itemView, BaseController mController, T data) {
        mController.bind(itemView, data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public final static class BaseViewHolder extends RecyclerView.ViewHolder {
        public final BaseController mController;

        public BaseViewHolder(View itemView, BaseController controller) {
            super(itemView);
            this.mController = controller;
        }
    }
}
