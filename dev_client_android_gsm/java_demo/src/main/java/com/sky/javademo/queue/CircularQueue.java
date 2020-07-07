package com.sky.javademo.queue;

/**
 * @author yuandunbin782
 * @ClassName CircularQueue
 * @Description 循环队列 基于数组实现
 * @date 2020/6/24
 */
public class CircularQueue {
    private String[] items;
    private int n = 0;
    private int head = 0;
    private int tail = 0;

    public CircularQueue(int capacity) {
        items = new String[capacity];
        this.n = capacity;
    }

    public boolean enqueue(String item) {
        if ((tail + 1) % n == head) {
            return false;
        }
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    public String dequeue() {
        if (head == tail) {
            return null;
        }
        String ret = items[head];
        head = (head + 1) % n;
        return ret;
    }

    public void printAll() {
        if (0 == n) {
            return;
        }
        for (int i = head; i % n != tail; i = (i + 1) % n) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        CircularQueue circularQueue = new CircularQueue(5);
        circularQueue.enqueue("1");
        circularQueue.enqueue("2");
        circularQueue.enqueue("3");
        circularQueue.enqueue("4");
        circularQueue.dequeue();
        circularQueue.enqueue("5");
        circularQueue.enqueue("6");
        circularQueue.enqueue("7");
        circularQueue.enqueue("8");
        circularQueue.printAll();
    }
}
