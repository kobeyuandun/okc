package com.sky.javademo;

import java.util.PriorityQueue;

/**
 * @author yuandunbin
 * @date 2019/3/20
 */
public class ToDoList extends PriorityQueue<ToDoList.ToDoItem> {
    static class ToDoItem implements Comparable<ToDoItem> {

        private final char pri;
        private final int sec;
        private final String td;

        public ToDoItem(String td, char pri, int sec) {
            this.pri = pri;
            this.sec = sec;
            this.td = td;
        }

        @Override
        public int compareTo(ToDoItem toDoItem) {
            if (pri > toDoItem.pri) {
                return +1;
            }
            if (pri == toDoItem.pri) {
                if (sec > toDoItem.sec) {
                    return +1;
                } else if (sec == toDoItem.sec) {
                    return 0;
                }
            }
            return -1;
        }

        @Override
        public String toString() {
            return Character.toString(pri) + sec + ":" + td;
        }
    }

    public void add(String td, char pri, int sec) {
        super.add(new ToDoItem(td, pri, sec));
    }
    public static void main(String[] args) {
        ToDoList toDoItems = new ToDoList();
        toDoItems.add("Empty trash",'C',4);
        toDoItems.add("Feed dog",'A',2);
        toDoItems.add("Feed bird",'B',7);
        toDoItems.add("Mow lawn",'C',3);
        toDoItems.add("Water lawn",'A',1);
        toDoItems.add("Feed cat",'B',1);
        while (!toDoItems.isEmpty()){
            System.out.println(toDoItems.remove());
        }
    }
}
