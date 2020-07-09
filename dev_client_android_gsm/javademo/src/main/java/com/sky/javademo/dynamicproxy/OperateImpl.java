package com.sky.javademo.dynamicproxy;

/**
 * @author yuandunbin782
 * @ClassName OperateImpl
 * @Description
 * @date 2020-03-02
 */
public class OperateImpl implements Operate {
    @Override
    public void operateMethod1() {
        System.out.println("Invoke operateMethod1");
        sleep(100);
    }

    @Override
    public void operateMethod2() {
        System.out.println("Invoke operateMethod2");
        sleep(110);
    }

    @Override
    public void operateMethod3() {
        System.out.println("Invoke operateMethod3");
        sleep(120);
    }
    private static void sleep(long m){
        try{
            Thread.sleep(m);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
