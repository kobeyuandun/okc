package com.sky.javademo.threadpool;

import android.os.Handler;
import android.os.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import androidx.annotation.NonNull;

/**
 * @author yuandunbin782
 * @ClassName MyThreadPool
 * @Description
 * @date 2020-03-09
 */
public class MyThreadPool {
    //线程池中默认线程的个数为5
    private static int WORK_NUM = 5;
    //队列默认任务个数为100
    private static int TASK_COUNT = 100;

    //工作线程组
    private WorkThread[] workThreads;

    //任务队列，作为一个缓冲
    private final BlockingQueue<Runnable> taskQueue;

    //用户在构造这个池，希望启动的线程数
    private final int worker_num;

    //创建具有默认线程个数的线程池
    public MyThreadPool() {
        this(WORK_NUM, TASK_COUNT);
    }

    //创建线程池，worker_num为线程池中工作线程的个数
    public MyThreadPool(int worker_num, int taskcount) {
        if (worker_num <= 0) {
            worker_num = WORK_NUM;
        }
        if (taskcount <= 0) {
            taskcount = TASK_COUNT;
        }
        this.worker_num = worker_num;
        taskQueue = new ArrayBlockingQueue<>(taskcount);
        workThreads = new WorkThread[worker_num];
        for (int i = 0; i < worker_num; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("可用进程资源 " + i);
    }

    //执行任务，其实只是把任务加入任务队列，什么时候执行由线程池管理器决定
    public void execute(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // Handler handler = new Handler(Looper.getMainLooper());
    private Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Runnable obj = (Runnable) msg.obj;
                    execute(obj);
            }
        }
    };

    public void execu(Runnable task) {
        Message message = new Message();
        message.what=1;
        message.obj=task;
        handler2.sendMessage(message);

    }

    // Message message = Message.obtain(handler);
    // handler.dispatchMessage(message);
    // message.what = 1;
    // Runnable callback = message.getCallback();
    // if (callback == null){
    //     handler.handleMessage(message);
    // }
    // task = callback;
    // execute(task);
    // handler.sendMessage(message);

    // Message obtain = handler.obtainMessage();
    // // Message obtain = Message.obtain();
    // Runnable callback = obtain.getCallback();
    // task = callback;
    // execute(task);

    //销毁线程池，该方法保证在所有任务都完成的情况下才销毁所有线程，否则等任务完成才销毁。
    public void destroy() {
        System.out.println("ready close pool  .....");
        for (int i = 0; i < worker_num; i++) {
            workThreads[i].stopWorker();
            workThreads[i] = null;
        }
        taskQueue.clear();
    }

    //覆盖toString方法，返回线程池信息：工作线程个数和已完成的任务个数
    @NonNull
    @Override
    public String toString() {
        return "WorkThread number:" + worker_num + " wait task number:" + taskQueue.size();
    }

    /**
     * 内部类，工作线程
     */
    private class WorkThread extends Thread {
        @Override
        public void run() {
            Runnable r = null;
            try {
                while (!isInterrupted()) {
                    r = taskQueue.take();
                    if (r != null) {
                        System.out.println(getId() + " ready exec :" + r);
                        r.run();
                    }
                    r = null;
                }
            } catch (Exception e) {

            }

        }

        public void stopWorker() {
            interrupt();
        }
    }
}

