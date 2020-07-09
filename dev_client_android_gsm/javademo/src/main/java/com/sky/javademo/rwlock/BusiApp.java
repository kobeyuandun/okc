package com.sky.javademo.rwlock;

import java.util.Random;

/**
 * @author yuandunbin782
 * @ClassName BusiApp
 * @Description
 * @date 2020-03-06
 */
public class BusiApp {
    static final int readWriteRatio = 10;//读写线程的比例
    static final int minthreadCount = 3;//最少线程数

    //读操作
    private static class GetThread implements Runnable {
        private GoodsLinsener goodsLinsener;

        public GetThread(GoodsLinsener goodsLinsener) {
            this.goodsLinsener = goodsLinsener;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                try {
                    goodsLinsener.getNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "读取商品数据耗时：" + (System.currentTimeMillis() - start + "ms"));
        }
    }

    //写操作
    private static class SetThread implements Runnable {
        private GoodsLinsener goodsLinsener;

        public SetThread(GoodsLinsener goodsLinsener) {
            this.goodsLinsener = goodsLinsener;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(50);
                    goodsLinsener.setNum(random.nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "写商品数据耗时：" + (System.currentTimeMillis() - start + "ms"));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GoodsInfo goodsInfo = new GoodsInfo("BUS", 10000, 10000);
        // GoodsLinsener goodsLinsener = new UseSyn(goodsInfo);
        GoodsLinsener goodsLinsener = new UseRwLock(goodsInfo);
        for (int i = 0; i < minthreadCount; i++) {
            Thread setT = new Thread(new SetThread(goodsLinsener));
            for (int j = 0; j < readWriteRatio; j++) {
                Thread getT = new Thread(new GetThread(goodsLinsener));
                getT.start();
            }
            Thread.sleep(100);
            setT.start();
        }
    }
}
