package com.sky.javademo.linkedlist;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author yuandunbin
 * @date 2020/5/17
 * 1）单链表的插入、删除、查找操作；
 * 2）链表中存储的是 int 类型的数据；
 */
public class SingleLinkedList {

    private Node head = null;

    public static class Node {
        private int data;
        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }
    }


    /**
     * 创建链表节点
     *
     * @param value
     * @return
     */
    public static Node createNode(int value) {
        return new Node(value, null);
    }

    /**
     * 通过存储的值来查找链表节点
     *
     * @param value
     * @return
     */
    public Node findByValue(int value) {
        Node p = this.head;
        while (p != null && p.data != value) {
            p = p.next;
        }
        return p;
    }

    /**
     * 通过固定位置来查找链表节点
     *
     * @param index
     * @return
     */
    public Node findByIndex(int index) {
        Node p = this.head;
        int pos = 0;
        if (p != null && pos != index) {
            p = p.next;
            ++pos;
        }
        return p;
    }

    /**
     * 无头结点，表头部插入，  这种操作将于输入的顺序相反，逆序
     *
     * @param value
     */
    public void insertToHead(int value) {
        Node node = new Node(value, null);
        insertToHead(node);
    }

    public void insertToHead(Node newNode) {
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    /**
     * 顺序插入,链表尾部插入   (插入的位置是最后一个结点的后面)
     *
     * @param value
     */
    public void insertTail(int value) {
        Node newNode = new Node(value, null);
        if (head == null) {
            head = newNode;
        } else {
            Node p = this.head;
            while (p.next != null) {
                p = p.next;
            }
            newNode.next = p.next;
            p.next = newNode;
        }
    }


    /**
     * 某一结点后部插入
     *
     * @param p
     * @param value
     */
    public void insertAfter(Node p, int value) {
        Node newNode = new Node(value, null);
        insertAfter(p, newNode);
    }

    public void insertAfter(Node p, Node newNode) {
        if (p == null) {
            return;
        }
        newNode.next = p.next;
        p.next = newNode;
    }

    /**
     * 某一结点前部插入
     *
     * @param p
     * @param value
     */
    public void insertBefore(Node p, int value) {
        Node newNode = new Node(value, null);
        insertBefore(p, newNode);
    }

    public void insertBefore(Node p, Node newNode) {
        if (p == null) {
            return;
        }
        if (p == head) {
            insertToHead(newNode);
            return;
        }
        Node q = this.head;
        while (q != null && q.next != p) {
            q = q.next;
        }
        if (q == null) {
            return;
        }
        newNode.next = p;
        q.next = newNode;
    }

    /**
     * 删除某个结点
     *
     * @param p
     */
    public void deleteByNode(Node p) {
        if (p == null || head == null) {
            return;
        }
        if (p == head) {
            head = head.next;
            return;
        }
        Node q = this.head;
        while (q != null && q.next != p) {
            q = q.next;
        }
        if (q == null) {
            return;
        }
        q.next = q.next.next;
    }

    public void deleteByValue(int value) {
        if (head == null) {
            return;
        }
        Node p = this.head;
        Node q = null;
        while (p != null && p.data != value) {
            q = p;
            p = p.next;
        }
        if (p == null) {
            return;
        }
        if (q == null) {
            head = head.next;
        } else {
            q.next = q.next.next;
        }
    }

    public void printAll() {
        Node p = head;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    //判断是否是回文（有一种数叫回文数，正读和反读都一样）
    public boolean isPalindrome() {
        if (head == null) {
            return false;
        } else {
            Node p = head;
            Node q = head;
            if (p.next == null) {
                return true;
            }
            while (q.next != null && q.next.next != null) {
                p = p.next;
                q = q.next.next;
            }
            System.out.println("中间节点：" + p.data);
            Node leftLink = null;
            Node rightLink = null;
            if (q.next == null) {
                //p肯定是中间节点 ,整个链表的中点
                //将p的左部分进行链表反转
                leftLink = inverseLinkList(p).next;
                rightLink = p.next;
            } else {
                leftLink = inverseLinkList(p);
                rightLink = p.next;
            }
            return TFResult(leftLink, rightLink);
        }

    }

    //判断是否对称（如："1 2 2 1"，"1 2 3 2 1"）
    private boolean TFResult(Node leftLink, Node rightLink) {
        Node l = leftLink;
        Node r = rightLink;
        boolean flag = true;
        while (l != null && r != null) {
            if (l.data == r.data) {
                l = l.next;
                r = r.next;
                continue;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    // 无头结点的链表翻转
    public Node inverseLinkList(Node p) {

        Node pre = null;
        Node r = head;
        System.out.println("z---" + r.data);
        Node next = null;
        while (r != p) {
            next = r.next;

            r.next = pre;
            pre = r;
            r = next;
        }

        r.next = pre;
        //　返回左半部分的中点之前的那个节点
        //　从此处开始同步像两边比较
        return r;

    }

    public static void main(String[] args) {
        SingleLinkedList link = new SingleLinkedList();
        System.out.println("hello");
        //int data[] = {1};
        //int data[] = {1,2};
        //int data[] = {1,2,3,1};
        //int data[] = {1,2,5};
        //int data[] = {1,2,2,1};
        // int data[] = {1,2,5,2,1};
        int data[] = {1, 2, 5, 3, 1};

        for (int i = 0; i < data.length; i++) {
            //link.insertToHead(data[i]);
            link.insertTail(data[i]);
        }
        link.printAll();
//        link.deleteByValue(3);
        link.printAll();
        Node node = link.inverseLinkList(link.findByValue(5));
//        link.printAll();
        ArrayList<Integer> integers = printListFromTailToHead(node);
        System.out.println(integers.toString());
    }

    public static ArrayList<Integer> printListFromTailToHead(Node listNode) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (listNode == null) {
            return res;
        }
        Stack<Node> nodes = new Stack<Node>();
        Node head = listNode;
        while (head != null) {
            nodes.push(head);
            head = head.next;
        }
        while (!nodes.empty()) {
            res.add(nodes.peek().data);
            nodes.pop();
        }
        return res;
    }
}
