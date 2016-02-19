package com.mapbar.obd.net.android.obd.page;

import android.view.View;

/**
 * Created by yun on 16/1/18.
 */
abstract class TabPage {
    public abstract View onCreateView();

    public abstract void init(View view);

    public abstract void onResume();

    public abstract void onPause();


}
