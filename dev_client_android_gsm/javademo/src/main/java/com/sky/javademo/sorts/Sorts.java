package com.sky.javademo.sorts;

import java.util.Arrays;

/**
 * @author yuandunbin782
 * @ClassName Sorts
 * @Description
 * @date 2020/6/28
 */
public class Sorts {
    /**
     * 冒泡排序
     *
     * @param a
     * @param n
     */
    public static void bubbleSort(int[] a, int n) {
        if (n <= 1) {
            return;
        }
        for (int i = 0; i < n; i++) {
            //提前退出冒泡排序的标志位
            boolean flag = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    // System.out.println(Arrays.toString(a));
                    flag = true;
                }
                System.out.println(Arrays.toString(a));
            }
            // System.out.println(Arrays.toString(a));
            //没有数据交换，提前退出
            if (!flag)
                break;
        }
    }

    /**
     * 冒泡排序改进：在每一轮排序后记录最后一次元素交换的位置，作为下次比较的边界，对于边界外的元素在下次循环中无需比较。
     *
     * @param a
     * @param n
     */
    public static void bubbleSort2(int[] a, int n) {
        if (n <= 1) {
            return;
        }
        //最后一次交换的位置
        int lastExchange = 0;
        //无序数据的边界，每次只需要比较到这里即可退出
        int sortBorder = n - 1;
        for (int i = 0; i < n; i++) {
            //提前退出的标志位
            boolean flag = false;
            for (int j = 0; j < sortBorder; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    //此次冒泡有数据交换
                    flag = true;
                    //更新最后一次交换的位置
                    lastExchange = j;
                }
                System.out.println(Arrays.toString(a));
            }
            sortBorder = lastExchange;
            // System.out.println(Arrays.toString(a));
            //没有数据交换，提前退出
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 插入排序
     *
     * @param a
     * @param n
     */
    public static void insertSort(int[] a, int n) {
        if (n <= 1) {
            return;
        }
        for (int i = 1; i < n; i++) {
            int value = a[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (a[j] > value) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = value;
        }
    }

    /**
     * 选择排序
     *
     * @param a
     * @param n
     */
    public static void selectSort(int[] a, int n) {
        if (n <= 1) {
            return;
        }
        for (int i = 0; i < n - 1; ++i) {
            //查找最小值
            int minIndex = i;
            for (int j = i + 1; j < n; ++j) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            //交换
            int tmp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = tmp;
        }
    }

    /**
     * 添加break,j = 0
     * 不添加break,j = -1
     */
    public static void testforBreak() {
        int j = 0;
        for (; j >= 0; --j) {
            // break;
        }
        System.out.println(j);
    }


    public static void main(String[] args) {
        int[] a = {3, 4, 2, 1, 5, 6, 7, 8};
        // bubbleSort(a, a.length);
        // bubbleSort2(a, a.length);
        insertSort(a, a.length);
        System.out.println(Arrays.toString(a));
        testforBreak();


    }
}
