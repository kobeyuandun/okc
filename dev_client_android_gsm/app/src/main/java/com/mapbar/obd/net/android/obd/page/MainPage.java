package com.mapbar.obd.net.android.obd.page;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.mapbar.obd.net.android.MainActivity;
import com.mapbar.obd.net.android.R;
import com.mapbar.obd.net.android.framework.Configs;
import com.mapbar.obd.net.android.framework.inject.annotation.ViewInject;
import com.mapbar.obd.net.android.framework.model.AppPage;

public class MainPage extends AppPage implements OnClickListener {

    private final ViewGroup mView;
    private final Context mContext;
    private boolean isFinishedInit = false;

    @ViewInject(R.id.base_main)
    private FrameLayout mainLayout;

    @ViewInject(R.id.rb_route)
    private RadioButton rbRoute;

    @ViewInject(R.id.rb_vehicleCondition)
    private RadioButton rbVehicleCondition;

    @ViewInject(R.id.rb_examinations)
    private RadioButton rbExaminations;

    @ViewInject(R.id.rb_mine)
    private RadioButton rbMine;
    private TabPage tabPage;
    private View routeView;
    private View vehicleConditionView;
    private View examinatonView;
    private MinePage minePage;
    private ExaminationsPage examinationPage;
    private VehicleConditionPage vehicleConditionPage;
    private Routepage routePage;
    private View mineView;

    public MainPage(final Context context, final View view) {

        super(context, view);
        this.mContext = context;
        this.mView = (ViewGroup) view;
        initListener();

    }

    private void initListener() {
        rbRoute.setOnClickListener(this);
        rbVehicleCondition.setOnClickListener(this);
        rbExaminations.setOnClickListener(this);
        rbMine.setOnClickListener(this);
    }

    @Override
    public void onAttachedToWindow(int flag, int position) {
    }

    @Override
    public void onAnimationEnd(Animation animation, int fromFlag) {
        if (isFinishedInit)
            return;
        isFinishedInit = true;
    }

    @Override
    public void onDetachedFromWindow(int flag) {
    }

    @Override
    public int getMyViewPosition() {
        return Configs.VIEW_POSITION_MAIN;
    }



    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.rb_route:
                goRoutePage();
                break;
            case R.id.rb_vehicleCondition:
                goVehicleCoditionPage();
                break;
            case R.id.rb_examinations:
                goExaminationsPage();
                break;
            case R.id.rb_mine:
                goMinePage();
                break;
        }
    }


    private void goMinePage() {
        if (minePage == null) {
            minePage = new MinePage(mContext);
            mineView = minePage.onCreateView();
            minePage.init(mineView);
        } else if (tabPage == minePage) {
            return;
        }
        repleceView(minePage, mineView);
    }

    private void goExaminationsPage() {
        if (examinationPage == null) {
            examinationPage = new ExaminationsPage(mContext);
            examinatonView = examinationPage.onCreateView();
            examinationPage.init(examinatonView);
        } else if (tabPage == examinationPage) {
            return;
        }
        repleceView(examinationPage, examinatonView);
    }

    private void goVehicleCoditionPage() {

        if (vehicleConditionPage == null) {
            vehicleConditionPage = new VehicleConditionPage(mContext);
            vehicleConditionView = vehicleConditionPage.onCreateView();
            vehicleConditionPage.init(vehicleConditionView);
        } else if (tabPage == vehicleConditionPage) {
            return;
        }
        repleceView(vehicleConditionPage, vehicleConditionView);
    }

    private void goRoutePage() {
        if (routePage == null) {
            routePage = new Routepage(mContext);
            routeView = routePage.onCreateView();
            routePage.init(routeView);
        } else if (tabPage == routePage) {
            return;
        }
        repleceView(routePage, routeView);

    }

    private void repleceView(TabPage page, View view) {
        if (tabPage != null) {
            tabPage.onPause();
        }
        this.tabPage = page;

        mainLayout.removeAllViews();
        mainLayout.addView(view);
        if (tabPage != null) {
            tabPage.onResume();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ((MainActivity) mContext).finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
