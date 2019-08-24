package com.sky.javademo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuandunbin
 * @date 2018/12/22
 * 擦除的思路
 */
public class ListMaker<T> {
    List<T> create(){
        return new ArrayList<T>();
    }
    public static void main(String[] args) {
        ListMaker<String> stringListMaker = new ListMaker<>();
        List<String> strings = stringListMaker.create();
    }
}
