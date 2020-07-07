package com.sky.javademo;


import com.sky.javademo.dynamicproxy.Operate;
import com.sky.javademo.dynamicproxy.OperateImpl;
import com.sky.javademo.dynamicproxy.TimingInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author yuandunbin782
 * @ClassName TestDemo
 * @Description
 * @date 2020-03-02
 */
public class TestDemo {
    private static class UseCallable implements Callable<String>{
        //优势是能打印返回结果5w
        @Override
        public String call() throws Exception {
            System.out.println("I am implements Callable");
            return "callresult";
        }
    }

    private static class JumpQueue implements Runnable{
        private Thread thread;//用来插队的线程

        public JumpQueue(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                System.out.println(thread.getName()+" will be join before "+Thread.currentThread().getName());
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" terminted.");
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //动态代理
        TimingInvocationHandler timingInvocationHandler = new TimingInvocationHandler(new OperateImpl());
        Operate operate = (Operate) Proxy.newProxyInstance(Operate.class.getClassLoader(), new Class[]{Operate.class}, timingInvocationHandler);
        operate.operateMethod1();
        System.out.println();
        operate.operateMethod2();
        System.out.println();
        operate.operateMethod3();
        System.out.println();

        //callable :开启线程的一种方式
        UseCallable useCallable = new UseCallable();
        FutureTask<String> futureTask = new FutureTask<>(useCallable);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());

        //join 使用，让线程顺序执行
        Thread pre = Thread.currentThread();//主线程
        for (int i=0;i<10;i++){
            Thread thread = new Thread(new JumpQueue(pre), String.valueOf(i));
            // System.out.println(pre.getName()+" jump a queue the thread:"+thread.getName());
            thread.start();
            pre = thread;
        }
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName()+" terminte.");
    }
}
