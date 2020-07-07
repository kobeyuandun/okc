package com.free.business.glide.cache;


import com.free.business.glide.resource.Value;

/**
 * @author yuandunbin782
 * @ClassName MemoryCacheCallback
 * @Description
 * @date 2020/6/8
 */
public interface MemoryCacheCallback {
    /**
     * 内存缓存中移除 key -- Value
     * @param key
     * @param OldValue
     */
    void entryRemovedMemoryCache(String key, Value OldValue);
}
