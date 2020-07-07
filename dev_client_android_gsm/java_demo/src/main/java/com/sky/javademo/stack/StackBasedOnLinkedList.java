package com.sky.javademo.stack;

/**
 * @author yuandunbin782
 * @ClassName StackBasedOnLinkedList
 * @Description
 * @date 2020/6/18
 */
public class StackBasedOnLinkedList {
    class Node {
        private int data;
        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private Node top = null;

    public void push(int value) {
        Node newNode = new Node(value, null);
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
    }

    public int pop() {
        if (top == null) {
            return -1;
        }
        int value = top.data;
        top = top.next;
        return value;
    }

    public void printAll() {
        Node p = this.top;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        StackBasedOnLinkedList linkedList = new StackBasedOnLinkedList();
        linkedList.push(1);
        linkedList.push(10);
        linkedList.push(100);
        linkedList.push(1000);
        linkedList.pop();
        linkedList.printAll();
    }
}
