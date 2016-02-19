package com.mapbar.obd.net.android.framework.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.mapbar.obd.net.android.R;
import com.mapbar.obd.net.android.framework.Configs;
import com.mapbar.obd.net.android.framework.control.activity.BaseActivity;
import com.mapbar.obd.net.android.framework.log.Log;
import com.mapbar.obd.net.android.framework.log.LogTag;
import com.mapbar.obd.net.android.framework.model.ActivityInterface;
import com.mapbar.obd.net.android.framework.model.AppPage;
import com.mapbar.obd.net.android.framework.model.MAnimation;
import com.mapbar.obd.net.android.framework.model.PageObject;
import com.mapbar.obd.net.android.obd.page.AddCarInfoPage;
import com.mapbar.obd.net.android.obd.page.BindingDeviceInfoPage;
import com.mapbar.obd.net.android.obd.page.BindingDevicePage;
import com.mapbar.obd.net.android.obd.page.CarInfoPage;
import com.mapbar.obd.net.android.obd.page.FillInVinPage;
import com.mapbar.obd.net.android.obd.page.LoginPage;
import com.mapbar.obd.net.android.obd.page.MainPage;
import com.mapbar.obd.net.android.obd.page.RegisterPage;
import com.mapbar.obd.net.android.obd.page.RetrievePasswordPage;
import com.mapbar.obd.net.android.obd.page.SettingPage;
import com.mapbar.obd.net.android.obd.page.TestPage;

import java.util.ArrayList;

public class PageManager {
    private static PageManager manager;
    private BaseActivity mContext;
    private ActivityInterface activityInterface;
    private LayoutInflater mInflater;
    private int currentPageIndex = -1;

    private PageManager() {

    }

    public static PageManager getInstance() {
        if (manager == null) {
            manager = new PageManager();
        }
        return manager;
    }

    public void init(BaseActivity context) {
        mContext = context;
        activityInterface = (ActivityInterface) mContext;
        mInflater = LayoutInflater.from(context);
    }

    public PageObject createPage(int index) {
        AppPage page = null;
        View view = null;
        switch (index) {
            case Configs.VIEW_POSITION_MAIN:
                view = mInflater.inflate(R.layout.page_main, null);
                page = new MainPage(mContext, view);
                break;
            case Configs.VIEW_POSITION_LOGIN:
                view = mInflater.inflate(R.layout.page_login, null);
                page = new LoginPage(mContext, view);
                break;
            case Configs.VIEW_POSITION_REGISTER:
                view = mInflater.inflate(R.layout.page_register, null);
                page = new RegisterPage(mContext, view);
                break;
            case Configs.VIEW_POSITION_RETRIEVE_PASSWORD:
                view = mInflater.inflate(R.layout.page_retrieve_password, null);
                page = new RetrievePasswordPage(mContext, view);
                break;
            case Configs.VIEW_POSITION_FILL_IN_VIN:
                view = mInflater.inflate(R.layout.page_fill_in_vin, null);
                page = new FillInVinPage(mContext, view);
                break;
            case Configs.VIEW_POSITION_TEST:
                view = mInflater.inflate(R.layout.layout_test, null);
                page = new TestPage(mContext, view);
                break;
            case Configs.VIEW_POSITION_ADD_CAR_INFO:
                view = mInflater.inflate(R.layout.layout_add_car_info, null);
                page = new AddCarInfoPage(mContext, view);
                break;
            case Configs.VIEW_POSITION_BINDING_DEVICE:
                view = mInflater.inflate(R.layout.page_binding_device, null);
                page = new BindingDevicePage(mContext, view);
                break;
            case Configs.VIEW_POSITION_CAR_INFO:
                view = mInflater.inflate(R.layout.page_car_info, null);
                page = new CarInfoPage(mContext, view);
                break;
            case Configs.VIEW_POSITION_SETTING:
                view = mInflater.inflate(R.layout.page_setting, null);
                page = new SettingPage(mContext, view);
                break;
            case Configs.VIEW_POSITION_BINDING_DEVICE_INFO:
                view = mInflater.inflate(R.layout.page_binding_device_info, null);
                page = new BindingDeviceInfoPage(mContext, view);
                break;
        }

        if (page == null || view == null)
            throw new IllegalArgumentException("the Page is null or the View is null.");
        return new PageObject(index, view, page);
    }

    /**
     * 跳转到某一Page，会打开一个全新的page
     *
     * @param index
     */
    public void goPage(int index) {
        activityInterface.showPage(currentPageIndex, index, Configs.DATA_TYPE_NONE, null, Configs.VIEW_POSITION_NONE, MAnimation.push_left_in, MAnimation.push_left_out);
        currentPageIndex = index;
    }

    public void showPrevious() {
        activityInterface.showPrevious(Configs.VIEW_POSITION_NONE, MAnimation.push_right_in, MAnimation.push_right_out);
    }

    /**
     * 返回某一特定的page
     *
     * @param index
     */
    public void goBack(int index) {
        activityInterface.showPrevious(index, MAnimation.push_right_in, MAnimation.push_right_out);
    }

    /**
     * 返回上一页
     *
     * @return true, 返回了上一页；false，没有上一页可以返回
     */
    public boolean goBack() {
        ArrayList<PageObject> pageObjects = mContext.getmPageHistorys();
        if (pageObjects.size() >= 2) {
            activityInterface.showPrevious(pageObjects.get(pageObjects.size() - 2).getIndex(), MAnimation.push_right_in, MAnimation.push_right_out);
            // 日志
            Log.d(LogTag.FRAMEWORK, " pageObjects.size -->> " + pageObjects.size());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 不支持A页面跳转到A页面，也就是自己跳自己之后，销毁前一个A页面
     * 适用场景：A跳到B，A页面移除，B返回A之前的页面，或者退出程序，次方法不会导致页面返回，返回页面调用goBack，不能和goBack同时调用
     *
     * @param index 页面跳转到下一页面前，当前页的index。
     */
    public boolean finishPage(int index) {
        ArrayList<PageObject> pageObjects = mContext.getmPageHistorys();
        final int size = pageObjects.size();
        if (size > 1) {
            if (pageObjects.get(size - 1).getIndex() == index) {
                pageObjects.remove(size - 1);
                return true;
            } else if (pageObjects.get(size - 2).getIndex() == index) {
                pageObjects.remove(size - 2);
                return true;
            } else {
                return false;
            }
        } else if (size == 1) {
            if (pageObjects.get(0).getIndex() == index) {
                pageObjects.remove(0);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public ActivityInterface getActivityInterface() {
        return activityInterface;
    }

    public Context getmContext() {
        return mContext;
    }

    public static class ManagerHolder {
        public static PageManager pageManager = getInstance();
    }


}
