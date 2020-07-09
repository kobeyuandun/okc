package com.sky.javademo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import androidx.annotation.Nullable;

/**
 * @author yuandunbin782
 * @ClassName UseThreadLocal
 * @Description
 * @date 2020-03-05
 */
public class UseThreadLocal {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Nullable
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    /**
     * 运行3个线程
     */
    private void startThread(){
        Thread[] threads = new Thread[3];
        for (int i= 0; i<threads.length; i++){
            threads[i] = new Thread(new TestThread(i));
        }
        for (int i= 0; i<threads.length; i++){
            threads[i].start();
        }
    }

    /**
     * 测试线程，线程的工作是将ThreadLocal变量的值变化，并写回，看看线程之间是否会相互影响
     */
    private static class TestThread implements Runnable{
        int id;

        public TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" :start");
            int s = threadLocal.get();
            s = s + id;
            threadLocal.set(s);
            System.out.println(Thread.currentThread().getName()+" :"+threadLocal.get());
            // threadLocal.remove();
        }
    }

    private int count = 0;
    //添加true就是公平锁了,事实上是非公平锁更好使
    private Lock lock = new ReentrantLock(true);


    public void incr(){
        //可以进行线程的等待和唤醒
        // Condition condition = lock.newCondition();
        // condition.signal();  唤醒
        // condition.await();   等待

        lock.lock();
        try {
            count++;
            System.out.println("值是啥："+count);
        }finally {
            lock.unlock();
        }
    }

    public synchronized void incr2(){
        count++;
        System.out.println("值是啥："+count);
        incr2();
    }

    public static void main(String[] args){
        UseThreadLocal useThreadLocal = new UseThreadLocal();
        useThreadLocal.startThread();

        useThreadLocal.incr();


        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
