package com.free.business.glide.fragment;

 import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

/**
 * @author yuandunbin782
 * @ClassName FragmentActivityFragmentManager
 * @Description FragmentActivity 生命周期 关联 管理
 * @date 2020/6/10
 */
public class FragmentActivityFragmentManager extends Fragment {
    private LifecycleCallback callback;

    public FragmentActivityFragmentManager() {
    }

    @SuppressLint("ValidFragment")
    public FragmentActivityFragmentManager(LifecycleCallback callback) {
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
