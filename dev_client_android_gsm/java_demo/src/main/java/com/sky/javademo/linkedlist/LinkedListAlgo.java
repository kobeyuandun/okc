package com.sky.javademo.linkedlist;

/**
 * @author yuandunbin782
 * @ClassName LinkedListAlgo
 * @Description 1、单链表反转
 * 2、链表中的环检测
 * 3、两个有序的链表合并
 * 4、删除链表倒数第n个结点
 * 5、求链表的中间结点
 * @date 2020/5/29
 */
public class LinkedListAlgo {
    /**
     * 单链表反转
     *
     * @param list
     * @return
     */
    public static Node reverse(Node list) {
        Node current = list;
        Node result = null;
        while (current != null) {
            Node next = current.next;
            current.next = result;
            result = current;
            current = next;
        }
        return result;
    }

    /**
     * 链表中的环检测
     *
     * @param list
     * @return
     */
    public static boolean checkCircle(Node list) {
        //快指针
        Node fast = list.next;
        //慢指针
        Node slow = list;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 有序链表 合并
     *
     * @param la
     * @param lb
     * @return
     */
    public static Node mergeSortedLists(Node la, Node lb) {
        if (la == null || lb == null)
            return null;
        Node p = la;
        Node q = lb;
        Node head;
        if (p.data < q.data) {
            head = p;
            p = p.next;
        } else {
            head = q;
            q = q.next;
        }
        Node r = head;
        while (p != null && q != null) {
            if (p.data < q.data) {
                r.next = p;
                p = p.next;
            } else {
                r.next = q;
                q = q.next;
            }
            r = r.next;
        }
        if (p != null) {
            r.next = p;
        } else {
            r.next = q;
        }
        return head;
    }

    /**
     * 有序链表 合并的第二种方式
     * 如果是两个无序链表呢？ 思路：先将无序链表排成有序链表
     * @param la
     * @param lb
     * @return
     */
    public static Node mergeTwoLists(Node la, Node lb) {
        //利用哨兵结点简化实现难度
        Node soldier = new Node(0, null);
        Node p = soldier;
        while (la != null && lb != null) {
            if (la.data < lb.data) {
                p.next = la;
                la = la.next;
            } else {
                p.next = lb;
                lb = lb.next;
            }
            p = p.next;
        }
        if (la != null) {
            p.next = la;
        }
        if (lb != null) {
            p.next = lb;
        }
        return soldier.next;
    }


    /**
     * 删除倒数第K个结点
     *
     * @param list
     * @param k
     * @return
     */
    public static Node deleteLastKth(Node list, int k) {
        Node fast = list;
        int i = 1;
        while (fast != null && i < k) {
            fast = fast.next;
            ++i;
        }

        if (fast == null) return list;

        Node slow = list;
        Node prev = null;
        while (fast.next != null) {
            fast = fast.next;
            //prev和slow关联了，也就和list关联了
            prev = slow;
            slow = slow.next;
        }

        if (prev == null) {
            list = list.next;
        } else {
            //这一步操作能改变list
            prev.next = prev.next.next;
        }
        return list;
    }

    /**
     * 求中间结点
     *
     * @param list
     * @return
     */
    public static Node findMiddleNode(Node list) {
        if (list == null) {
            return null;
        }
        Node fast = list;
        Node slow = list;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

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

    public static void printAll(Node list) {
        Node p = list;
        while (p != null) {
            System.out.print(p.data + ",");
            p = p.next;
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(12, null);
        Node nodeq = new Node(8, node1);
        Node node2 = new Node(6, nodeq);
        Node node3 = new Node(3, node2);
        // Node node3 = new Node(36, node2);
        Node node = new Node(1, node3);
        //
        // Node node4 = new Node(7, null);
        // Node node5 = new Node(5, node4);
        // Node node6 = new Node(4, node5);
        // Node nodel = new Node(2, node6);
        // Node reverse = reverse(node);
        // Node node7 = mergeSortedLists(node, nodel);
        // Node node7 = mergeTwoLists(node, nodel);
        // printAll(node7);
        Node node8 = new Node(10, null);
        Node node9 = new Node(11, node8);
        Node node10 = deleteLastKth(node9, 1);
        // printAll(node10);
        Node middleNode = findMiddleNode(node);
        // printAll(middleNode);

        Node node20 = new Node(12, null);
        Node node21 = new Node(8, node20);
        Node reverse = reverse(node21);
        System.out.println();
        printAll(reverse);
    }

    public static Node reverseAgain(Node list) {
        Node current = list;
        Node result = null;
        while (current != null) {
            Node next = current.next;
            current.next = result;
            result = current;
            current = next;
        }
        return result;
    }

    /**
     * 链表中的环检测
     * 拿1，2举例 1-》2-》1-》2
     *
     * @param list
     * @return
     */
    public static boolean checkCircleAgain(Node list) {
        Node fast = list.next;
        Node slow = list;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
}
