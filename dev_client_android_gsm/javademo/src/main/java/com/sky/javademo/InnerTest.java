package com.sky.javademo;

/**
 * @author yuandunbin782
 * @ClassName InnerTest
 * @Description
 * @date 2020/5/11
 */
public class InnerTest {
    private static int a = 0;
    private int b = 0;
    public static void main(String[] args) {
        Innter innter = new Innter();
        InnerTest innerTest = new InnerTest();
        outer();
        innter.innt();
    }
    public static void outer(){
        a = 2;
        System.out.println("out:" +a);
    }

    static class Innter{
        public Innter() {
            System.out.println("Innter:" +a);
            outer();
        }
        public static void innt(){
            a = 3;
            System.out.println("in:" +a);
        }
    }
}
