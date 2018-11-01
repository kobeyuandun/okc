package com.free.base.okhttp;

import android.content.Context;
import android.os.Build;
import android.os.StatFs;

import java.io.File;

import okhttp3.Cache;

public class OkhttpCacheUtils {

    private static final int MIN_DISK_CACHE_SIZE = 16 * 1024 * 1024;    // 16MB
    private static final int MAX_DISK_CACHE_SIZE = 32 * 1024 * 1024;    // 32MB

    private static final float MAX_AVAILABLE_SPACE_USE_FRACTION = 0.9f;
    private static final float MAX_TOTAL_SPACE_USE_FRACTION = 0.25f;


    public static Cache createCache(Context context, String dirpath) {
        File defaultCacheDir = createDefaultCacheDir(context, dirpath);
        long cacheSize = calculateDiskCacheSize(defaultCacheDir);
        return new Cache(defaultCacheDir, cacheSize);
    }

    public static File createDefaultCacheDir(Context context, String path) {
        File cacheDir = context.getApplicationContext().getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = context.getApplicationContext().getCacheDir();
        }
        File cache = new File(cacheDir, path);
        if (!cache.exists()) {
            cache.mkdir();
        }
        return cache;
    }

    public static long calculateDiskCacheSize(File dir) {
        long size = Math.min(calculateAvailableCacheSize(dir), MAX_DISK_CACHE_SIZE);
        return Math.max(size, MIN_DISK_CACHE_SIZE);
    }


    public static long calculateAvailableCacheSize(File dir) {
        long size = 0;
        try {
            StatFs statFs = new StatFs(dir.getPath());
            int sdkInt = Build.VERSION.SDK_INT;
            long availableBytes;
            long totalBytes;
            if (sdkInt < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                int blockSize = statFs.getBlockSize();
                availableBytes = ((long) statFs.getAvailableBlocks()) * blockSize;
                totalBytes = ((long) statFs.getBlockCount()) * blockSize;
            } else {
                availableBytes = statFs.getAvailableBytes();
                totalBytes = statFs.getTotalBytes();
            }
            size = (long) Math.min(availableBytes * MAX_AVAILABLE_SPACE_USE_FRACTION, totalBytes * MAX_TOTAL_SPACE_USE_FRACTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
