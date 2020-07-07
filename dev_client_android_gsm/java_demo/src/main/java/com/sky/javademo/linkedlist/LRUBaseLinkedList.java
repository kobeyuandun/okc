package com.sky.javademo.linkedlist;

/**
 * @author yuandunbin782
 * @ClassName LRUBaseLinkedList
 * @Description 基于单链表的LRU算法
 * @date 2020/5/29
 */
public class LRUBaseLinkedList<T> {
    //默认链表容量
    private final static int DEFAULT_CAPACITY = 10;

    //头结点
    private Node<T> headNode;

    //链表长度
    private int length;

    //链表容量
    private int capacity;

    public LRUBaseLinkedList() {
        this.headNode = new Node<>();
        this.capacity = DEFAULT_CAPACITY;
        this.length = 0;
    }

    public LRUBaseLinkedList(int capacity) {
        this.headNode = new Node<>();
        this.capacity = capacity;
        this.length = 0;
    }

    /**
     * 添加一个数据
     *
     * @param data
     */
    public void add(T data) {
        //找到当前数据的前一个数据
        Node preNode = findPreNode(data);
        //链表中存在，删除原数据，再插入到链表的头部
        if (preNode != null) {
            deleteElemOpim(preNode);
        } else {
            if (length >= this.capacity) {
                //删除尾结点
                deleteElemAtEnd();
            }
        }
        insertElemAtBegin(data);
    }

    /**
     * 删除尾结点
     */
    private void deleteElemAtEnd() {
        Node pre = this.headNode;
        //空链表直接返回
        if (pre.getNext() == null) {
            return;
        }
        //获取倒数第二个结点
        while (pre.getNext().getNext() != null) {
            pre = pre.getNext();
        }
        Node temp = pre.getNext();
        pre.setNext(null);
        temp = null;
        length--;
    }

    /**
     * 链表头部插入节点
     *
     * @param data
     */
    private void insertElemAtBegin(T data) {
        Node next = headNode.getNext();
        headNode.setNext(new Node(data, next));
        length ++;
    }

    /**
     * 删除preNode结点的下一个元素
     *
     * @param preNode
     */
    private void deleteElemOpim(Node preNode) {
        Node temp = preNode.getNext();
        preNode.setNext(temp.getNext());
        temp = null;
        length--;
    }

    /**
     * 获取查到元素的前一个结点
     *
     * @param data
     * @return
     */
    private Node findPreNode(T data) {
        Node<T> node = this.headNode;
        while (node.getNext() != null) {
            if (data.equals(node.getNext().getElement())) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    private void printAll() {
        Node node = headNode.getNext();
        while (node != null) {
            System.out.println(node.getElement() + ",");
            node = node.getNext();
        }
        System.out.println();
    }

    public static class Node<T> {
        private T element;
        private Node next;

        public Node(T element) {
            this.element = element;
        }

        public Node(T element, Node next) {
            this.element = element;
            this.next = next;
        }

        public Node() {
            this.next = null;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LRUBaseLinkedList lruBaseLinkedList = new LRUBaseLinkedList(5);
        // Node<Integer> node1 = new Node<Integer>(1,null);
        // Node<Integer> node2 = new Node<Integer>(2,node1);
        // Node<Integer> node3 = new Node<Integer>(2,node1);
        lruBaseLinkedList.add(1);
        lruBaseLinkedList.add(2);
        lruBaseLinkedList.add(3);
        lruBaseLinkedList.add(4);
        lruBaseLinkedList.add(5);
        lruBaseLinkedList.add(3);
        lruBaseLinkedList.printAll();
        // Scanner scanner = new Scanner(System.in);
        // while (true) {
        //     lruBaseLinkedList.add(scanner.nextInt());
        //     lruBaseLinkedList.printAll();
        // }
    }
}
