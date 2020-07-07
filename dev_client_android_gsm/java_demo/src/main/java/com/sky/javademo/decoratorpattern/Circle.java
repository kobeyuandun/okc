package com.sky.javademo.decoratorpattern;

/**
 * @author yuandunbin782
 * @ClassName Circle
 * @Description
 * @date 2020/4/29
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("shape: Circle");
    }
}
