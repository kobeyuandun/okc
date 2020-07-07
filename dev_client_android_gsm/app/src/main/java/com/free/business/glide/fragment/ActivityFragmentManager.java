package com.free.business.glide.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;

/**
 * @author yuandunbin782
 * @ClassName ActivityFragmentManager
 * @Description     Activity 生命周期 关联 管理
 * @date 2020/6/9
 */

public class ActivityFragmentManager extends Fragment {
    private LifecycleCallback callback;

    public ActivityFragmentManager() {
    }

    @SuppressLint("ValidFragment")
    public ActivityFragmentManager(LifecycleCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callback != null) {
            callback.glideInitAction();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (callback != null) {
            callback.glideStopAction();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (callback != null) {
            callback.glideRecycleAction();
        }
    }
}
