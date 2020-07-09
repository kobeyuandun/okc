package com.sky.javademo.algorithm;

/**
 * @author yuandunbin782
 * @ClassName FindCard
 * @Description 淘宝面试题：有54张扑克牌,我随便抽掉一张,你如何最快的找到我抽到那张牌?
 * 途虎改变后：有52张扑克牌（去除大小王）,我随便抽掉一张,你如何最快的找到我抽到那张牌?
 * <p>
 * 思路
 * <p>
 * B - >黑桃  100分
 * R - >红桃  200分
 * C - >草花  300分
 * F - >方片  400分
 * 500 - >小王500分
 * 600 - >大王600分
 * @date 2020/7/3
 */
public class FindCard {
    /**
     * 给每张牌打标记
     */
    static String[] s = new String[]{"B1", "B2", "B3", "B4", "B5", "B6",
            "B7", "B8", "B9", "B10", "B11", "B12", "B13", "R1", "R2", "R3",
            "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13",
            "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11",
            "F12", "F13", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9",
            "C10", "C11", "C12", "C13", "500", "600"};
    /**
     * 54张牌的总分
     */
    public static final int countScore = (100 + 200 + 300 + 400) * 13
            + ((1 + 13) * 13) / 2 * 4 + 500 + 600;

    /**
     * 随机抽张牌的位置
     *
     * @return
     */
    public static int rudom() {
        int o = (int) (Math.random() * 54);
        return o;
    }

    /**
     * 获取某张牌的分数
     *
     * @param card
     * @return
     */
    public static int getScore(String card) {
        if (card.substring(0, 1).equals("B")) {
            return 100 + new Integer(card.substring(1, card.length()));
        }
        if (card.substring(0, 1).equals("R")) {
            return 200 + new Integer(card.substring(1, card.length()));
        }
        if (card.substring(0, 1).equals("C")) {
            return 300 + new Integer(card.substring(1, card.length()));
        }
        if (card.substring(0, 1).equals("F")) {
            return 400 + new Integer(card.substring(1, card.length()));
        }
        return new Integer(card);
    }

    /**
     * 通过某张牌的分数查找出对应的牌
     *
     * @param score
     * @return
     */
    public static String getCard(int score) {
        if (score / 100 == 4) {
            return "F" + (score - 400);
        }
        if (score / 100 == 3) {
            return "C" + (score - 300);
        }
        if (score / 100 == 2) {
            return "R" + (score - 200);
        }
        if (score / 100 == 1) {
            return "B" + (score - 100);
        }
        return "" + score;
    }

    public static void main(String[] args) {
        //将抽出牌的位置改为"0"
        s[rudom()] = "0";

        int score = countScore;

        //遍历后，将总分数 减去 53张牌的分数和，就是抽出那张牌的分数
        for (int i = 0; i < 54; i++) {
            score = score - getScore(s[i]);
            System.out.print(s[i] + ",");
            if (i - 10 >= 0 && i % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println(getCard(score));
        System.out.println(401f / 100);
        System.out.println(401d / 100);
        System.out.println(401 / 100);
        System.out.println(2 ^ 3);
    }

}
