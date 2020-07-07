package com.free.business.glide;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.free.business.glide.fragment.ActivityFragmentManager;
import com.free.business.glide.fragment.FragmentActivityFragmentManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * @author yuandunbin782
 * @ClassName RequestManager
 * @Description 生命周期 管理
 * @date 2020/6/12
 */
public class RequestManager {
    private static final String TAG = "RequestManager";
    private final String FRAGMENT_ACTIVITY_NAME = "Fragment_Activity_Name";
    private final String ACTIVITY_NAME = "Activity_Name";

    private Context requestManagerContext;
    private static RequestTargetEngine requestTargetEngine;
    private final int NEXT_HANDLER_MSG = 995465;//Handler 标记

    {//构造代码块，不用再所有的构造方法里面去实例化了
        if (requestTargetEngine == null) {
            requestTargetEngine = new RequestTargetEngine();
        }
    }

    /**
     * 可以管理生命周期 -- FragmentActivity是有生命周期方法
     */
    FragmentActivity fragmentActivity;

    public RequestManager(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        requestManagerContext = fragmentActivity;
        //开始绑定操作
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        if (null == fragment) {
            fragment = new FragmentActivityFragmentManager(requestTargetEngine);
            supportFragmentManager.beginTransaction().add(fragment, FRAGMENT_ACTIVITY_NAME).commitAllowingStateLoss();
        }

        //测试下面的话，这种测试，不完全准确
        //证明是不是排队状态
        Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
        Log.d(TAG, "RequestManager: fragmentByTag" + fragmentByTag); // null ： 还在排队中，还没有消费
        //添加进去 提交之后 可能还处于 排队状态，想让马上干活 （Android 消息机制管理），快速干活，发了一次Handler
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);
    }

    /**
     * 管理生命周期
     * @param activity
     */
    public RequestManager(Activity activity) {
        this.requestManagerContext = activity;
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        android.app.Fragment fragment = fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        if (null == fragment){//如果等于null，就要去创建Fragment
            fragment = new ActivityFragmentManager(requestTargetEngine);
            //添加管理器
            fragmentManager.beginTransaction().add(fragment,ACTIVITY_NAME).commitAllowingStateLoss();
        }
        android.app.Fragment fragmentByTag = fragmentManager.findFragmentByTag(ACTIVITY_NAME);
        Log.d(TAG, "RequestManager: fragmentByTag" + fragmentByTag); // null ： 还在排队中，还没有消费
        //添加进去 提交之后 可能还处于 排队状态，想让马上干活 （Android 消息机制管理），快速干活，发了一次Handler
        mHandler.sendEmptyMessage(NEXT_HANDLER_MSG);
    }

    /**
     * 目前没有做管理
     * @param context
     */
    public RequestManager(Context context) {
        this.requestManagerContext = context;
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Fragment fragmentByTag =
                    fragmentActivity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_ACTIVITY_NAME);
            Log.d(TAG, "handleMessage: fragmentByTag:" + fragmentByTag);//有值：不在排队中，所以有值。
            return false;
        }
    });

    public RequestTargetEngine load(String path){
        mHandler.removeMessages(NEXT_HANDLER_MSG);
        //把值传递给 资源加载引擎
        requestTargetEngine.loadValueInitAction(path,requestManagerContext);
        return requestTargetEngine;
    }
}
