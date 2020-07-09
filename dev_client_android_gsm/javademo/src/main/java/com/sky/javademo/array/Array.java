package com.sky.javademo.array;

/**
 * @author yuandunbin782
 * @ClassName Array
 * @Description
 * @date 2020/5/15
 */
public class Array {
    //定义整型数据data保存数据
    public int data[];
    //定义数组长度
    private int n;
    //实际个数
    private int count;

    //定义数组大小
    public Array(int capacity) {
        data = new int[capacity];
        n = capacity;
        //一开始一个数都没有所以存为0
        count = 0;
    }

    //插入元素：头部插入，尾部插入
    public boolean insert(int index, int value) {
        //数组空间已满
        if (count == n) {
            System.out.println("没有可插入的位置");
            return false;
        }
        //如何count还没满，那么就可以插入数据到数组中
        //位置不合法
        if (index < 0 || index > count) {
            System.out.println("位置不合法");
            return false;
        }
        //位置合法，将老数组index之后的每个数据向后移动一位
        for (int i = count; i > index; --i) {
            data[i] = data[i - 1];
            System.out.println(i + "");
            System.out.println(data[i] + "");
        }
        data[index] = value;
        ++count;
        return true;
    }

    //删除元素
    public boolean delete(int index) {
        if (index < 0 || index >= count) {
            System.out.println("删除位置不合法");
            return false;
        }
        //从删除位置开始，将后面的元素向前移动一位
        for (int i = index + 1; i < count; ++i) {
            data[i - 1] = data[i];
        }
        --count;
        return true;
    }

    //查找某个元素
    public int find(int index){
        if (index < 0 || index >= count) {
            System.out.println("查找位置不合法");
            return -1;
        }
        return data[index];
    }

    public void printAll() {
        for (int i = 0; i < count; ++i) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Array array = new Array(5);
        array.printAll();
        array.insert(0, 1);
        array.insert(1, 2);
        array.insert(0, 3);
        array.printAll();
        array.find(2);
        array.delete(1);
        array.printAll();
    }
}
