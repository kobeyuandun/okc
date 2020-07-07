package com.free.business.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.free.base.R;
import com.free.base.mvp.BaseMvpActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends BaseMvpActivity<MainView, MainPresent> implements MainView {
    private static final String TAG = "MainActivity";
    private boolean isyou = false;
    private int you = 0;
    private ExecutorService executorService;

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
//        if (isyou == false) {
//            return;
//        }
//        you++;
        String test = "";
        if (TextUtils.isEmpty(test)) {
            test = "hh";
            Log.e(TAG, "setupData1: " + test);
        } else {
            switch (test) {
                case "1":
                    test = "you";
                    Log.e(TAG, "setupData: " + test);
                    break;
                case "2":
                    test = "me";
                    Log.e(TAG, "setupData: " + test);
                    break;
                default:
                    test = "hh";
                    Log.e(TAG, "setupData: " + test);
                    break;
            }
        }
//        Log.e(TAG, "setupData: " + you);
        //todo 拼多多考察Handler
        executorService = Executors.newFixedThreadPool(5);
        execute(new Runnable() {
            @Override
            public void run() {
                Log.d("nihao", "run: " + Thread.currentThread().getName());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("nihao", "run1: " + Thread.currentThread().getName());
                    }
                }, 5000);
            }
        });
    }

    private Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Runnable obj = (Runnable) msg.obj;
                    executorService.execute(obj);
            }
        }
    };
    public void execute(Runnable task) {
        //方式一
//        Message message = new Message();
//        message.what=1;
//        message.obj=task;
//        handler2.sendMessage(message);


        //方式二
         MyThread myThread = new MyThread(task);
         executorService.execute(myThread);

    }
    class MyThread extends Thread{

        private final Runnable runnable;

        public MyThread(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            Looper.prepare();
            runnable.run();
            Looper.loop();
        }
    }
}
