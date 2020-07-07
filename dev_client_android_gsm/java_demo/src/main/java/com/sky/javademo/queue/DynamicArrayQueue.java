package com.sky.javademo.queue;

/**
 * @author yuandunbin782
 * @ClassName DynamicArrayQueue
 * @Description
 * @date 2020/6/24
 */
public class DynamicArrayQueue {
    private String[] items;
    private int n;

    int head = 0;
    int tail = 0;

    public DynamicArrayQueue(int capacity) {
        items = new String[capacity];
        this.n = capacity;
    }

    public boolean enqueue(String item) {
        //tail == n 表示队列末尾没有空间了
        if (tail == n) {
            //head == 0 表示整个队列都占满了
            if (head == 0) {
                return false;
            }
            //数据搬移
            for (int i = head; i < tail; ++i) {
                items[i - head] = items[i];
            }
            //搬移完之后重新更新head和tail
            tail -= head;
            head = 0;
        }
        items[tail] = item;
        tail++;
        return true;
    }

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
        DynamicArrayQueue arrayQueue = new DynamicArrayQueue(5);
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
