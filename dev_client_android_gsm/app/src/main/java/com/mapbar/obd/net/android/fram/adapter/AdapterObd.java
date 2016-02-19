package com.mapbar.obd.net.android.fram.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.mapbar.obd.net.android.fram.BaseController;

import java.util.List;

/**
 * Created by yun on 16/1/7.
 */
public abstract class AdapterObd<M, V extends View> extends DataAdapter<M> {
    private BaseController mContorller;

    protected AdapterObd(List<M> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = newView(parent);
        }
        doBind((V) convertView, getItem(position));
        return convertView;
    }

    protected abstract void doBind(V view, M data);

    protected abstract V newView(ViewGroup parent);//继承自父类的怎么写


}
