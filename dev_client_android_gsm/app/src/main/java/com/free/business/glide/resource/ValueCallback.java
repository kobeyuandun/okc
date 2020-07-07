package com.free.business.glide.resource;

/**
 * @author yuandunbin782
 * @ClassName ValueCallback
 * @Description 专门给Value, 不再使用的回调接口
 * @date 2020/6/8
 */
public interface ValueCallback {
    /**
     * 监听的方法（Value不再使用了）
     * @param key
     * @param value
     */
    void valueNonUseListener(String key, Value value);
}
