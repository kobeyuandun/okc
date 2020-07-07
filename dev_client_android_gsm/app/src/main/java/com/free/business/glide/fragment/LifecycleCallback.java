package com.free.business.glide.fragment;

/**
 * @author yuandunbin782
 * @ClassName LifecycleCallback
 * @Description Fragment生命周期监听
 * @date 2020/6/10
 */
public interface LifecycleCallback {
    //生命周期 开始初始化了
    void glideInitAction();

    //生命周期 停止了
    void glideStopAction();

    //生命周期 释放操作
    void glideRecycleAction();
}
