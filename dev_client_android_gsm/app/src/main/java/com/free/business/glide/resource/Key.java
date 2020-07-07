package com.free.business.glide.resource;


import com.free.business.glide.Tool;

/**
 * @author yuandunbin782
 * @ClassName Key
 * @Description key   -----> Bitmap封装 === Value
 * @date 2020/6/8
 */
public class Key {
    // 合格：唯一 加密的  ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668
    private String key;

    public Key(String path) {
        this.key = Tool.getSHA256StrJava(path);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
