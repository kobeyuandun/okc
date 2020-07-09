package com.sky.javademo.stack;

import java.util.Arrays;

/**
 * @author yuandunbin782
 * @ClassName ArrayStack
 * @Description 基于数组实现的顺序栈
 * @date 2020/6/15
 */
public class ArrayStack {
    private static String[] items;
    private int count; //栈中的元素个数
    private int n; //栈的大小

    public ArrayStack(int n) {
        this.items = new String[n];
        this.count = 0;
        this.n = n;
    }

    /**
     * 入栈
     *
     * @param item
     * @return
     */
    public boolean push(String item) {
        if (count == n) {
            return false;
        }
        //将item放到下标为count的位置，并且count加一
        items[count] = item;
        count++;
        return true;
    }

    public String pop() {
        if (count == 0) {
            return null;
        }

        //返回下标为count-1的数组元素，
        String item = items[count - 1];
        //删除最后一个元素,置为空
        items[count - 1] = null;
        // 并且栈中元素个数count减一
        count--;
        return item;
    }

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(5);
        arrayStack.push("1");
        arrayStack.push("2");
        System.out.println(Arrays.asList(items));
        arrayStack.pop();
        System.out.println(Arrays.asList(items));
        arrayStack.pop();
        System.out.println(Arrays.asList(items));
        arrayStack.push("x");
        System.out.println(Arrays.asList(items));
    }
}
