package com.sky.javademo.stack;

/**
 * @author yuandunbin782
 * @ClassName BrowserStack
 * @Description 使用前后栈实现浏览器的前进后退
 * @date 2020/6/16
 */
public class BrowserStack {
    private String currentPage;
    private LinkedListBasedStack forwardStack;//前进栈
    private LinkedListBasedStack backStack;//后退栈

    public BrowserStack() {
        this.forwardStack = new LinkedListBasedStack();
        this.backStack = new LinkedListBasedStack();
    }

    public boolean canGoBack() {
        return this.backStack.size() > 0;
    }

    public boolean canGoForward() {
        return this.forwardStack.size() > 0;
    }

    public void open(String url) {
        if (this.currentPage != null) {
            this.backStack.push(currentPage);
            this.forwardStack.clear();
        }
        showUrl(url, "open");

    }

    public String goBack() {
        if (canGoBack()) {
            this.forwardStack.push(this.currentPage);
            String backUrl = this.backStack.pop();
            showUrl(backUrl, "Back");
            return backUrl;
        }
        System.out.println("* Cannot go back, no pages behind.");
        return null;
    }

    public String goForward() {
        if (this.canGoForward()) {
            this.backStack.push(this.currentPage);
            String forwardUrl = this.forwardStack.pop();
            showUrl(forwardUrl, "Foward");
            return forwardUrl;
        }

        System.out.println("** Cannot go forward, no pages ahead.");
        return null;
    }

    private void showUrl(String url, String prefix) {
        this.currentPage = url;
        System.out.println(prefix + " page == " + url);
    }

    public void checkCurrentPage() {
        System.out.println("Current page is: " + this.currentPage);
    }

    public static class Node {
        private String data;
        private Node next;

        public Node(String data) {
            this(data, null);
        }

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

    public static class LinkedListBasedStack {
        private int size;
        private Node top;

        public Node createNode(String data, Node next) {
            return new Node(data, next);
        }

        public void push(String data) {
            Node node = createNode(data, this.top);
            this.top = node;
            this.size++;
        }

        public String pop() {
            Node popNode = this.top;
            if (popNode == null) {
                System.out.println("stack is empty");
                return "";
            }
            this.top = popNode.next;
            if (this.size > 0) {
                this.size--;
            }
            return popNode.data;
        }

        public void clear() {
            this.top = null;
            this.size = 0;
        }

        public String getTopData() {
            if (this.top == null) {
                return null;
            }
            return this.top.data;
        }

        public int size() {
            return this.size;
        }

        public void print() {
            System.out.println("Print stack:");
            Node currentNode = this.top;
            while (currentNode != null) {
                String data = currentNode.getData();
                System.out.print(data + "\t");
                currentNode = currentNode.next;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        LinkedListBasedStack linkedListBasedStack = new LinkedListBasedStack();
        linkedListBasedStack.push("1");
        linkedListBasedStack.push("2");
        linkedListBasedStack.print();
        linkedListBasedStack.pop();
        linkedListBasedStack.print();

        LinkedListBasedStack stack = new LinkedListBasedStack();
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.pop();
        stack.push("D");
        stack.push("E");
        stack.pop();
        stack.push("F");
        stack.print();
        String data = stack.getTopData();
        System.out.println("Top data == " + data);


        BrowserStack browser = new BrowserStack();
        browser.open("http://www.baidu.com");
        browser.open("http://news.baidu.com/");
        browser.open("http://news.baidu.com/ent");
        browser.goBack();
        browser.goBack();
        browser.goForward();
        browser.open("http://www.qq.com");
        browser.goForward();
        browser.goBack();
        browser.goForward();
        browser.goBack();
        browser.goBack();
        browser.goBack();
        browser.goBack();
        browser.checkCurrentPage();
    }
}
