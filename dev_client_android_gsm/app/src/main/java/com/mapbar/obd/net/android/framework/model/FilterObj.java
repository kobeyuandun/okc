package com.mapbar.obd.net.android.framework.model;

import java.util.ArrayList;

public class FilterObj {
    private int mFlag;
    private Object mTag;
    private ArrayList<Object> mObjs;

    public FilterObj() {
    }

    public FilterObj(int flag) {
        this.mFlag = flag;
    }

    public ArrayList<Object> getObjs() {
        return mObjs;
    }

    public void setObjs(ArrayList<Object> objs) {
        this.mObjs = objs;
    }

    public FilterObj setFlag(int flag) {
        this.mFlag = flag;
        return this;
    }

    public int getFlag() {
        return this.mFlag;
    }

    public FilterObj setTag(Object tag) {
        this.mTag = tag;
        return this;
    }

    public Object getTag() {
        return this.mTag;
    }

    public void destroy() {
    }
}
