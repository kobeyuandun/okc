package com.sky.javademo.io;

import java.io.File;
import java.util.HashMap;

/**
 * @author yuandunbin782
 * @ClassName IoDemo
 * @Description
 * @date 2020/4/29
 */
public class IoDemo {
    private static byte[] bytes = {0x0A, 0x0B};
    public static void main(String[] args) {
        File file = new File("/Users/yuandunbin782/Downloads/pingancode/CodeLog-AndroidSDK/app/src/main/java/com/jfyk" +
                "/codelog_android/test/io.txt");
        // BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new OutputStream(file));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1","1");
        hashMap.put("1","2");
        hashMap.put("1","3");
        hashMap.put("1","4");
        hashMap.put("1","5");
        hashMap.put(null,null);
        System.out.println(hashMap.toString());
    }
}
