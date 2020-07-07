package com.free.business.glide.resource;

import android.graphics.Bitmap;
import android.util.Log;

import com.free.business.glide.Tool;


/**
 * @author yuandunbin782
 * @ClassName Value
 * @Description Bitmap的封装
 * @date 2020/6/8
 */
public class Value {
    private static final String TAG = "Value";
    private static volatile Value value;

    //位图
    private Bitmap mBitmap;
    //使用计数
    private int count;
    //监听回调
    private ValueCallback callback;
    //key标记 唯一的
    private String key;

    public static Value getInstance() {
        if (null == value) {
            synchronized (Value.class) {
                if (null == value) {
                    value = new Value();
                }
            }
        }
        return value;
    }

    /**
     * 使用一次，计数一次 +1
     */
    public void useAction(){
        Tool.checkNotEmpty(mBitmap);
        if (mBitmap.isRecycled()){
            Log.d(TAG, "useAction: 已经被回收了");
            return;
        }
        Log.d(TAG, "useAction: 加一 count:" + count);
        count ++;
    }

    /**
     * 不使用一次（使用完成）计数一次 -1
     */
    public void nonUseAction(){
        count --;
        if (count <= 0 && callback != null){
            //活动缓存管理监听  证明Value没有使用（管理回收）
            callback.valueNonUseListener(key, this);
        }
        Log.d(TAG, "nonUseAction: 减一 count:" + count);
    }

    /**
     * 释放Bitmap
     */
    public void recycleBitmap(){
        //正在使用中
        if (count > 0){
            Log.d(TAG, "recycleBitmap: 引用计数大于0，正在使用中，不能释放。");
            return;
        }
        if (mBitmap.isRecycled()){
            Log.d(TAG, "recycleBitmap: 都已经被回收了，不能释放。");
            return;
        }
        mBitmap.recycle();
        value = null;
        System.gc();
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ValueCallback getCallback() {
        return callback;
    }

    public void setCallback(ValueCallback callback) {
        this.callback = callback;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
