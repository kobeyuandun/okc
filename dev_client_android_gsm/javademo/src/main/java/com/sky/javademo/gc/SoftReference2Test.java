package com.sky.javademo.gc;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yuandunbin782
 * @ClassName SoftReference2Test
 * @Description
 * @date 2020/6/22
 */
public class SoftReference2Test {
    public static class MyBigObeject {
        byte[] data = new byte[1024];//1kb
    }

    public static int cache_intital_capacity = 100 * 1024;//100M
    public static Set<SoftReference<MyBigObeject>> cache = new HashSet<>(cache_intital_capacity);

    public static int removeSoftRefs = 0;
    public static ReferenceQueue<MyBigObeject> referenceQueue = new ReferenceQueue<>();
    public static void main(String[] args) {
        for (int i = 0; i < cache_intital_capacity; i++) {
            MyBigObeject softObject = new MyBigObeject();
            cache.add(new SoftReference<>(softObject,referenceQueue));
            checkUselessReference();
            if (i % 10000 == 0) {
                System.out.println("size of cache:" + cache.size());
            }
        }
    }

    private static void checkUselessReference() {
        Reference<? extends MyBigObeject> poll = referenceQueue.poll();
        while (poll!=null){
            if (cache.remove(poll)){
                removeSoftRefs++;
            }
            poll = referenceQueue.poll();
        }
    }
}
