package com.sky.javademo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class ShowMethods {
    private static String usage =
            "usage:\n" +
                    "ShowMethods qualified.class.name\n" +
                    "To Show all methods in class or:\n" +
                    "ShowMethods qualified.class.name word\n" +
                    "To search for methods involving 'word'";
    private static Pattern p = Pattern.compile("\\w+\\.");

    public static void HelloWorld() {
        System.out.println("-------------------");
        System.out.println("hello world");
        System.out.println("-------------------");
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(usage);
            System.exit(1);
        }
        int lines = 0;
        try {
            Class<?> c = Class.forName(args[0]);
            Method[] methods = c.getMethods();
            /* 调用 hello world 方法*/
            Method methodHello = methods[0];
            methodHello.invoke(null);
            Constructor[] ctors = c.getConstructors();
            for (Method method : methods) {
                System.out.println(p.matcher(method.toString()).replaceAll(""));
            }
            for (Constructor ctor : ctors) {
                System.out.println(p.matcher(ctor.toString()).replaceAll(""));
            }
            lines = methods.length + ctors.length;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}