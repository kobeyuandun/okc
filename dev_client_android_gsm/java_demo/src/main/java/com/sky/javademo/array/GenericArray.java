package com.sky.javademo.array;

import android.support.annotation.NonNull;

/**
 * @author yuandunbin782
 * @ClassName GenericArray
 * @Description
 * @date 2020/5/15
 */
public class GenericArray<T> {
    private T[] data;
    private int size;

    //根据传入容量，构造Array
    public GenericArray(int capacity) {
        data = (T[]) new Object[capacity];
        this.size = 0;
    }

    //无参构造方法，默认数组容量为10
    public GenericArray() {
        this(10);
    }

    //获取数组容量
    public int getCapacity() {
        return data.length;
    }

    //获取当前元素个数
    public int count() {
        return size;
    }

    //判断数组是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //修改index位置的元素
    public void set(int index, T e) {
        checkIndex(index);
        data[index] = e;
    }

    // 获取对应 index 位置的元素
    public T get(int index) {
        checkIndex(index);
        return data[index];
    }

    // 查看数组是否包含元素e
    public boolean contain(T e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    // 获取对应元素的下标, 未找到，返回 -1
    public int find(T e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    // 在 index 位置，插入元素e, 时间复杂度 O(m+n)
    public void add(int index, T e) {
        checkIndexForAdd(index);
        //如果当前元素个数等于数组容量，则将数组扩容为原来的两倍
        if (size == data.length) {
            resize(2 * data.length);
        }
        //将老数组index之后的每个数据向后移动一位
        for (int i = size; i > index; --i) {
            data[i] = data[i - 1];
        }
        data[index] = e;
        size++;
    }

    // 扩容方法，时间复杂度 O(n)
    private void resize(int capacity) {
        T[] newData = (T[]) new Object[capacity];
        //将原数组copy到新数组
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    // 向数组头插入元素
    public void addFirst(T e) {
        add(0, e);
    }

    // 向数组尾插入元素
    public void addLast(T e) {
        add(size, e);
    }

    // 删除 index 位置的元素，并返回
    public T remove(int index) {
        checkIndex(index);
        T ret = data[index];
        //从删除位置开始，将后面的元素向前移动一位
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null;
        //缩容
        if (size == data.length / 4 && data.length / 2 != 0) {
            resize(data.length / 2);
        }
        return ret;
    }

    // 删除第一个元素
    public T removeFirst() {
        return remove(0);
    }

    // 删除末尾元素
    public T removeLast() {
        return remove(size - 1);
    }

    // 从数组中删除指定元素
    public void removeElement(T e) {
        int i = find(e);
        if (i != -1) {
            remove(i);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Add failed! require index >= 0 and index < size.");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed! require index >= 0 and index <= size.");
        }
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Array size = %d, capacity = %d \n",size,data.length));
        builder.append('[');
        for (int i=0;i<size;i++){
            builder.append(data[i]);
            if (i!=size-1){
                builder.append(", ");
            }
        }
        builder.append(']');
        return builder.toString();
    }

    public static void main(String[] args) {
        GenericArray<String> array = new GenericArray<>();
        array.addFirst("a");
        array.add(1,"b");
        array.add(2,"c");
        array.add(2,"c");
        array.add(2,"c");
        array.add(2,"c");
        array.add(2,"c");
        array.add(2,"c");
        array.add(2,"c");
        array.add(2,"c");
        array.add(2,"c");
        System.out.println(array.toString());
    }
}
