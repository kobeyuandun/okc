package com.sky.javademo;

/**
 * @author yuandunbin782
 * @ClassName StackAlloc
 * @Description
 * @date 2020/3/11
 */
/**
 *  逃逸分析
 *  栈上分配
 *  -server -Xmx10m -Xms10m -XX:-DoEscapeAnalysis  -XX:+PrintGC  （在as中找到Run按钮，点击，找到Edit Configurations后点击打开，在VM options中添加前面的值。表示关闭逃逸分析，开启打印GC日志）
 *
 *  -Xmx10m和-Xms10m：堆的大小
 * -XX:+DoEscapeAnalysis：启用逃逸分析(默认打开)
 * -XX:+PrintGC：打印GC日志
 * -XX:+EliminateAllocations：标量替换(默认打开)
 * -XX:-UseTLAB 关闭本地线程分配缓冲
 */
public class StackAlloc {

    public static class User{
        public int id = 0;
        public String name = "";
    }


    public static void alloc() {
        User u = new User();  //Object  在堆上分配的() ,有逃逸分析的技术 ，在栈中分配的
        u.id = 5;
        u.name = "King";
    }

    public static void main(String[] args) {
        long b = System.currentTimeMillis(); //开始时间
        for(int i=0;i<100000000;i++) {//一个方法运行1亿次（）
            alloc();
        }
        long e = System.currentTimeMillis(); //结束时间
        System.out.println(e-b);//打印运行时间：毫秒
    }



}
