package com.free.business.glide.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.free.business.glide.resource.Value;


/**
 * @author yuandunbin782
 * @ClassName MemoryCache
 * @Description 内存缓存 LRU 最近最少使用
 * @date 2020/6/8
 */
public class MemoryCache extends LruCache<String, Value> {
    private MemoryCacheCallback memoryCacheCallback;
    private boolean shoudongRemove; //手动移除

    public void setMemoryCacheCallback(MemoryCacheCallback memoryCacheCallback) {
        this.memoryCacheCallback = memoryCacheCallback;
    }

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    /**
     * 手动移除
     *
     * @return
     */
    public Value shoudongRemove(String key) {
        shoudongRemove = true;//被动移除失效
        Value remove = remove(key);
        shoudongRemove = false; //恢复被动移除
        return remove;
    }

    /**
     * 代表被移除了
     * 1、重复的key
     * 2、最近最少使用的元素移除（当元素 大于 maxSize）
     *
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, Value oldValue, Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        //被动移除
        if (memoryCacheCallback != null && !shoudongRemove) {
            memoryCacheCallback.entryRemovedMemoryCache(key, oldValue);
        }
    }

    /**
     * 每一个元素的大小 == Bitmap 的大小
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    protected int sizeOf(String key, Value value) {
        Bitmap bitmap = value.getBitmap();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }
        return bitmap.getByteCount();
    }
}
