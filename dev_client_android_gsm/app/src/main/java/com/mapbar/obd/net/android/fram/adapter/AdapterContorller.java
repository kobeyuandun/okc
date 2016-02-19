package com.mapbar.obd.net.android.fram.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.mapbar.obd.net.android.R;
import com.mapbar.obd.net.android.fram.BaseController;

import java.util.List;

/**
 * Created by yun on 16/1/11.
 */
public abstract class AdapterContorller<M> extends DataAdapter<M> {
    protected static int TAG_KEY = R.id.list_id;

    public AdapterContorller(List<M> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseController controller;
        if (convertView == null) {
            controller = newContorller();
            convertView = newView(parent);
            convertView.setTag(TAG_KEY, controller);
        } else {
            controller = (BaseController) convertView.getTag(TAG_KEY);
        }
        doBind(convertView, controller, getItem(position));
        return convertView;
    }


    protected void doBind(View convertView, BaseController controller, M data) {
        controller.unBind();
        controller.bind(convertView, data);
    }

    protected abstract BaseController newContorller();
}
