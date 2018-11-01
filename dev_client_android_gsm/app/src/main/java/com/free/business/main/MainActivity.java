package com.free.business.main;

import android.os.Bundle;

import com.free.base.R;
import com.free.base.mvp.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainView, MainPresent> implements MainView{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }
}
