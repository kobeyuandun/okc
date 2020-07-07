package com.sky.javademo.rwlock;

/**
 * @author yuandunbin782
 * @ClassName GoodsLinsener
 * @Description
 * @date 2020-03-06
 */
public interface GoodsLinsener {
    GoodsInfo getNum() throws InterruptedException;//获取商品信息

    void setNum(int num) throws InterruptedException;//设置商品的数量
}
