package com.sky.javademo;

/**
 * @author yuandunbin782
 * @ClassName TestWaitAndNotify
 * @Description
 * @date 2020-03-04
 */
public class TestWaitAndNotify {
    private static Express express = new Express(1, Express.CITY);

    private static class CheckKm extends Thread {
        @Override
        public void run() {
            try {
                express.waitKm();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class CheckSite extends Thread {
        @Override
        public void run() {
            try {
                express.waitSite();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i<3; i++){
            new CheckSite().start();
        }
        for (int i=0;i<3;i++){
            new CheckKm().start();
        }
        Thread.sleep(1000);
        express.changeSite();
    }
}
