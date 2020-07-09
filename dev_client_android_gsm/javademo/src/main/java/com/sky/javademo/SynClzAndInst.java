package com.sky.javademo;

/**
 * @author yuandunbin782
 * @ClassName SynClzAndInst
 * @Description
 * @date 2020-03-04
 */
public class SynClzAndInst {
    //使用类锁的线程
    private static class SyncClass extends Thread{
        @Override
        public void run() {
            System.out.println("TestClass is running");
            try {
                synClass();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //类锁，实际是锁类的class对象
    private static synchronized void synClass() throws Exception{
        Thread.sleep(1000);
        System.out.println("synClass going ...");
        Thread.sleep(1000);
        System.out.println("synClass end");
    }

    private static  Object object = new Object();

    private void synStaticObject() throws InterruptedException {
        synchronized (object){//类似于类锁，object在全虚拟机只有一份
            Thread.sleep(1000);
            System.out.println("synClass going ...");
            Thread.sleep(1000);
            System.out.println("synClass end");
        }
    }

    //使用对象锁
    private static class SynObject implements Runnable{
        private SynClzAndInst synClzAndInst;


        public SynObject(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("TestInstance is running..."+synClzAndInst);
            try {
                synClzAndInst.instance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //使用对象锁
    private static class SynObject2 implements Runnable{
        private SynClzAndInst synClzAndInst;


        public SynObject2(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        @Override
        public void run() {
            System.out.println("TestInstance2 is running..."+synClzAndInst);
            try {
                synClzAndInst.instance2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //锁对象
    private synchronized void instance() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("synInstance is going..."+this.toString());
        Thread.sleep(3000);
        System.out.println("synInstance ended "+this.toString());
        //制造死循环，同步里面不能这样写的
        instance();
    }

    //锁对象
    private synchronized void instance2() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("synInstance2 is going..."+this.toString());
        Thread.sleep(3000);
        System.out.println("synInstance2 ended "+this.toString());
    }

    public static void main(String[] args) throws InterruptedException {
        SynClzAndInst synClzAndInst = new SynClzAndInst();
        Thread t1 = new Thread(new SynObject(synClzAndInst));
        SynClzAndInst synClzAndInst2 = new SynClzAndInst();
        Thread t2 = new Thread(new SynObject2(synClzAndInst));
        t1.start();
        t2.start();

        SyncClass syncClass = new SyncClass();
        syncClass.start();
        Thread.sleep(1000);
    }
}
