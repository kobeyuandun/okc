package com.sky.javademo.decoratorpattern;

/**
 * @author yuandunbin782
 * @ClassName DecoratorPatternDemo
 * @Description 装饰器模式
 * @date 2020/4/29
 */
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Shape circle = new Circle();
        RedShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        RedShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());
        // ShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        // ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());
        // Shape redCircle = new RedShapeDecorator(new Circle());
        // Shape redRectangle = new RedShapeDecorator(new Rectangle());
        System.out.println("Circle with normal border");
        circle.draw();

        System.out.println("\nCircle of red border");
        redCircle.draw();

        System.out.println("\nRectangle of red border");
        redRectangle.draw();
    }
}
