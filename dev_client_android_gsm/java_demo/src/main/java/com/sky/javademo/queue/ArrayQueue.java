package com.sky.javademo.queue;

/**
 * @author yuandunbin782
 * @ClassName ArrayQueue
 * @Description 用数组实现的队列
 * @date 2020/6/22
 */
public class ArrayQueue {
    //数组 ；数组大小
    private String[] items;
    private int n;

    //head表示队头下标，tail表示队尾下标
    private int head;
    private int tail;

    public ArrayQueue(int capacity) {
        items = new String[capacity];
        this.n = capacity;
    }

    /**
     * 入队
     *
     * @param item
     * @return
     */
    public boolean enqueue(String item) {
        if (tail == n) {
            return false;
        }
        items[tail] = item;
        ++tail;
        return true;
    }

    /**
     * 出队
     *
     * @return
     */
    public String dequeue() {
        if (head == tail) {
            return null;
        }
        String ret = items[head];
        ++head;
        return ret;
    }

    public void printAll() {
        for (int i = head; i < tail; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(5);
        arrayQueue.enqueue("0");
        arrayQueue.enqueue("1");
        arrayQueue.enqueue("2");
        arrayQueue.enqueue("3");
        arrayQueue.enqueue("4");
        // arrayQueue.enqueue("5");
        arrayQueue.dequeue();
        arrayQueue.enqueue("5");
        // arrayQueue.dequeue();
        arrayQueue.printAll();
    }
}
