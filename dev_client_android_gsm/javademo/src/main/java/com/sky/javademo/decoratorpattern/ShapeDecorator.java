package com.sky.javademo.decoratorpattern;

/**
 * @author yuandunbin782
 * @ClassName ShapeDecorator
 * @Description 创建实现了Shape接口的抽象装饰类
 * @date 2020/4/29
 */
public abstract class ShapeDecorator implements Shape {

    protected final Shape decoratorShpe;

    public ShapeDecorator(Shape decoratorShpe) {
        this.decoratorShpe = decoratorShpe;
    }

    @Override
    public void draw() {
        decoratorShpe.draw();
    }
}
