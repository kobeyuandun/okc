package com.sky.javademo;

/**
 * @author yuandunbin
 * @date 2018/12/30
 */
public class NotifyTest {
    public synchronized void testWait() {
        System.out.println(Thread.currentThread().getName() + "start-----");
        try {
            wait(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "end-----");
    }

    public static void main(String[] args) throws InterruptedException {
        NotifyTest notifyTest = new NotifyTest();
        for (int i = 0; i < 5; i++) {
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     notifyTest.testWait();
                 }
             }).start();
        }
        synchronized (notifyTest){
            notifyTest.notify();
        }
        Thread.sleep(3000);
        System.out.println("--------分割线--------");
        synchronized (notifyTest){
            notifyTest.notifyAll();
        }
    }
}
