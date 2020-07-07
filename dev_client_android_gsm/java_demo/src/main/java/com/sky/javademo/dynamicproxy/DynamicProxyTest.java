package com.sky.javademo.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * @author yuandunbin782
 * @ClassName DynamicProxyTest
 * @Description
 * @date 2020/6/18
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
        TimingInvocationHandler invocationHandler = new TimingInvocationHandler(new OperateImpl());
        Operate operate = (Operate) Proxy.newProxyInstance(Operate.class.getClassLoader(), new Class[]{Operate.class},
                invocationHandler);
        operate.operateMethod1();
    }
}
