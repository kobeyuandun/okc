package com.sky.javademo;

public class MyClass {
    static int i;
    public static void main(String[] args) {
        final MyClass myt2 = new MyClass();
        Thread test1 = new Thread(  new Runnable() {  public void run() {  myt2.test1();  }  }, "test1"  );
        Thread test2 = new Thread(  new Runnable() {  public void run() { myt2.test2();   }  }, "test2"  );
        Thread test3 = new Thread(  new Runnable() {  public void run() { myt2.test3();   }  }, "test3"  );

        test1.start();
        test2.start();
        test3.start();

    }
    synchronized void test1(){
        i = 2;
        System.out.println("test1=="+i);
    }

    synchronized void test2(){
        i = 3;
        System.out.println("test2=="+i);
    }
    static synchronized void test3(){
        i = 4;
        System.out.println("test3=="+i);
    }
}
