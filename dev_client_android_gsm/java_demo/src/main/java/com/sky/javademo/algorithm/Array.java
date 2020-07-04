package com.sky.javademo.algorithm;

/**
 * @author yuandunbin
 * @date 2020/6/20
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
class Array {
    /**
     * 双层for循环遍历，时间复杂度为n的平方，效率低
     *
     * @param array
     * @param target
     * @return
     */
    public static boolean find(int array[][], int target) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (target == array[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 最优解思路：后来读了剑指 offer 的解题思路得知，如果我们每次的比较能排除某一列或者某一行来解决找到 target 问题，这道题
     * <p>
     * 目就很快解决。故需要选取右上方的数组元素，或者左下方的数组元素。
     * <p>
     * 这样每次比较一次 target 都会排除一行或者一列，随着比较次数的增加，我们需要查找的 target 范围越来越小。问题得到解决。
     * <p>
     * 具体实现：
     * <p>
     * （1）首先选取数组中右上角的数字：如果该数字等于要查找的数字，查找过程结束；
     * <p>
     * （2）如果该数字大于要查找的数字，则剔除这个数字所在的列；
     * <p>
     * （3）如果该数字小于要查找的数字，则剔除这个数字所在的行。
     * <p>
     * 即：如果要查找的数字不在数组的右上角，则每次都在数组的查找范围剔除一行或者一列，这样每一步都可以缩小查找的范围，直到找到要查找的数字，或者查找范围
     *
     * @param array
     * @param target
     * @return
     */
    public static boolean find2(int array[][], int target) {
        //初始化 行
        int row = 0;
        //初始化 列
        int col = array[0].length - 1;
        while (row <= array.length - 1 && col >= 0) {
            if (target == array[row][col]) {
                return true;
            } else if (target < array[row][col]) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int array[][] = new int[][]{
                {2, 3, 4},
                {3, 4, 5},
                {5, 6, 8},
                {7, 9, 11}};
        System.out.println(array.length + "");
        System.out.println(array[3].length + "");
        boolean b = find(array, 9);
        System.out.println(b + "");
        System.out.println(find2(array, 10) + "");

    }
}
