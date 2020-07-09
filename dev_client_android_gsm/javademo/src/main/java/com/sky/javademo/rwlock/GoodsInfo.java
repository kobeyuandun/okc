package com.sky.javademo.rwlock;

/**
 * @author yuandunbin782
 * @ClassName GoodsInfo
 * @Description
 * @date 2020-03-06
 */
public class GoodsInfo {
    private final String name;
    private double totalMoney;
    private int number;

    public GoodsInfo(String name, double totalMoney, int number) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.number = number;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public int getNumber() {
        return number;
    }

    public void changeNumber(int sellNumber) {
        this.totalMoney += sellNumber * 25;
        this.number -= sellNumber;
    }
}
