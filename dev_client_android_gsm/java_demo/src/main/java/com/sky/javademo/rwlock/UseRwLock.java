package com.sky.javademo.rwlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yuandunbin782
 * @ClassName UseRwLock
 * @Description
 * @date 2020-03-06
 */
public class UseRwLock implements GoodsLinsener {
    private GoodsInfo goodsInfo;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock getLock = lock.readLock();
    private final Lock setLock = lock.writeLock();

    public UseRwLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public GoodsInfo getNum() throws InterruptedException {
        getLock.lock();
        try {
            Thread.sleep(5);
            return goodsInfo;
        } finally {
            getLock.unlock();
        }
    }

    @Override
    public void setNum(int num) throws InterruptedException {
        setLock.lock();
        try {
            Thread.sleep(5);
            goodsInfo.changeNumber(num);
        } finally {
            setLock.unlock();
        }
    }
}
