package com.sky.javademo.lambda;

import android.os.Build;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

import androidx.annotation.RequiresApi;

/**
 * @author yuandunbin782
 * @ClassName Lambda1
 * @Description
 * @date 2020/4/17
 */
public class Lambda1 {
    public void test1() {
        //匿名内部类
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        Runnable runnable1 = () -> System.out.println("hello");

    }

    public static void test2() {
        TreeSet<String> strings = new TreeSet<String>((o1, o2) -> Long.compare(o1.length(), o2.length()));
        strings.add("142412312124124");
        strings.add("123");
        strings.add("1234");
        System.out.println(strings.toString());
    }

    public static void lan1() {
        //1、无参数，无返回值
        Runnable runnable1 = () -> System.out.println("hello");
    }

    public static void lan2() {
        //2、有一个参数，并且无返回值
        Consumer consumer = (x) -> System.out.println(x);
    }

    public static void lan3() {
        //3、若只有一个参数，小括号可以省略不写
        Consumer consumer = x -> System.out.println(x);
    }

    public static void lan4() {
        Comparator<Integer> comparable = (x, z) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, z);
        };
    }

    public static void lan5() {
        //5、若只有一条语句，return和大括号可以省略不写
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void lan6(int a,int b) {
        //6、lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即"类型推断"
        Comparator<Integer> comparator = (Integer x, Integer y) -> Integer.compare(x, y);

        //生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
        stream4.forEach(System.out::println);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {
        test2();
        lan6(2,3);
    }
}
