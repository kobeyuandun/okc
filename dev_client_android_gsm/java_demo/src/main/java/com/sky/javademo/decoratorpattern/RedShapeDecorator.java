package com.sky.javademo.decoratorpattern;

/**
 * @author yuandunbin782
 * @ClassName RedShapeDecoratoe
 * @Description 创建扩展了ShapeDecorator类的实体装饰类
 * @date 2020/4/29
 */
public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape decoratorShpe) {
        super(decoratorShpe);
    }

    @Override
    public void draw() {
        decoratorShpe.draw();
        setRedBorder(decoratorShpe);
    }

    private void setRedBorder(Shape decoratorShpe) {
        System.out.println("Border Color: Red");
    }
}
