package com.sky.javademo.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author yuandunbin782
 * @ClassName TimingInvocationHandler
 * @Description
 * @date 2020-03-02
 */
public class TimingInvocationHandler implements InvocationHandler {
    private Object target;

    public TimingInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    public TimingInvocationHandler() {
        super();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        Object invoke = method.invoke(target, args);
        System.out.println(method.getName() + " cost time is : " + (System.currentTimeMillis() - start));
        return invoke;
    }
}
