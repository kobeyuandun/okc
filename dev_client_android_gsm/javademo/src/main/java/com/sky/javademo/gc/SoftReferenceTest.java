package com.sky.javademo.gc;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yuandunbin782
 * @ClassName SoftReferenceTest
 * @Description 软引用隐藏问题
 * 静态集合保存软引用，会导致这些软引用对象本身无法被垃圾回收器回收
 * @date 2020/6/22
 */
public class SoftReferenceTest {
    public static class SoftObject {
        byte[] data = new byte[1024];//1kb
    }

    public static int cache_intital_capacity = 100 * 1024;//100M
    public static Set<SoftReference<SoftObject>> cache = new HashSet<>(cache_intital_capacity);

    public static void main(String[] args) {
        for (int i = 0; i < cache_intital_capacity; i++) {
            SoftObject softObject = new SoftObject();
            cache.add(new SoftReference<>(softObject));
            if (i % 10000 == 0) {
                System.out.println("size of cache:" + cache.size());
            }
        }
    }
}
