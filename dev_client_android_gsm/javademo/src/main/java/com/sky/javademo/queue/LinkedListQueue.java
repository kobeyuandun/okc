package com.sky.javademo.queue;

/**
 * @author yuandunbin782
 * @ClassName LinkedListQueue
 * @Description
 * @date 2020/6/24
 */
public class LinkedListQueue {
    //队头
    private Node head = null;
    //队尾
    private Node tail = null;

    public void enqueue(String value) {
        if (tail == null) {
            Node newNode = new Node(value, null);
            head = newNode;
            tail = newNode;
        } else {
            tail.next = new Node(value, null);
            tail = tail.next;
        }
    }

    public String dequeue(){
        if (head == null){
            return null;
        }
        String data = head.data;
        head = head.next;
        if (head == null){
            tail = null;
        }
        return data;
    }

    public void printAll() {
        Node p = head;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkedListQueue linkedListQueue = new LinkedListQueue();
        linkedListQueue.enqueue("1");
        linkedListQueue.enqueue("2");
        linkedListQueue.enqueue("3");
        linkedListQueue.enqueue("4");
        linkedListQueue.enqueue("5");
        linkedListQueue.enqueue("6");
        linkedListQueue.enqueue("7");
        linkedListQueue.enqueue("8");
        linkedListQueue.enqueue("9");
        linkedListQueue.dequeue();
        linkedListQueue.enqueue("10");
        linkedListQueue.printAll();
    }
    public class Node {
        private String data;
        private Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
