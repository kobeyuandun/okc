package com.sky.javademo.rwlock;

/**
 * @author yuandunbin782
 * @ClassName UseSyn
 * @Description
 * @date 2020-03-06 用内置锁实现商品服务接口
 */
public class UseSyn implements GoodsLinsener{
    private GoodsInfo goodsInfo;

    public UseSyn(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public synchronized GoodsInfo getNum() throws InterruptedException {
        Thread.sleep(5);
        return goodsInfo;
    }

    @Override
    public synchronized void setNum(int num) throws InterruptedException {
        Thread.sleep(5);
        goodsInfo.changeNumber(num);
    }
}
