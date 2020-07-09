package com.sky.javademo;

import java.util.Arrays;
import java.util.List;

/**
 * @author yuandunbin
 * @date 2018/12/22
 */
class Fruit{}
class Apple extends Fruit{}
public class TestFanxing {
    public static void main(String[] args) {
        List<? extends Fruit> apples = Arrays.asList(new Apple());
        Apple apple = (Apple)apples.get(0);
        apples.contains(new Apple());     //参数是object
        apples.indexOf(new Apple());
    }

}
