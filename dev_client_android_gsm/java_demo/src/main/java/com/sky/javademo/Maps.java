package com.sky.javademo;

import java.util.Map;

/**
 * @author yuandunbin
 * @date 2019/3/26
 */
public class Maps {
    public static void printKeys(Map<Integer,String> map){
        System.out.print("Size = "+map.size()+" , ");
        System.out.print("Keys: ");
        System.out.print(map.keySet());
    }

    public static void test(Map<Integer,String> map){
        System.out.print(map.getClass().getSimpleName());
//        map.putAll(new counting);
    }
}
