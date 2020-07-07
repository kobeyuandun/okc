package com.sky.javademo.decoratorpattern;

/**
 * @author yuandunbin782
 * @ClassName Rectangle
 * @Description
 * @date 2020/4/29
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("shape: Rectangle");
    }
}
