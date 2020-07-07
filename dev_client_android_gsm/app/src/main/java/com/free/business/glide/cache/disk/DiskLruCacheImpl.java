package com.free.business.glide.cache.disk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.free.business.glide.Tool;
import com.free.business.glide.resource.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yuandunbin782
 * @ClassName DiskLruCacheImpl
 * @Description 磁盘缓存的封装
 * put 把Value存储进去
 * get 通过key 得到 Value
 * @date 2020/6/9
 */
public class DiskLruCacheImpl {
    private static final String TAG = "DiskLruCacheImpl";
    //磁盘缓存的目录
    private final String DISKLRU_CACHE_DIR = "disklru_cache_dir";
    //版本号
    private final int APP_VERSION = 1;
    private final int VALUE_COUNT = 1;
    private final long MAX_SIZE = 1024 * 1024 * 10;

    private DiskLruCache diskLruCache;

    public DiskLruCacheImpl() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + DISKLRU_CACHE_DIR);
        try {
            diskLruCache = DiskLruCache.open(file,APP_VERSION,VALUE_COUNT,MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * put 把Value存储进去
     * @param key
     * @param value
     */
    public void put(String key, Value value){
        Tool.checkNotEmpty(key);
        DiskLruCache.Editor edit = null;
        OutputStream outputStream = null;
        try {
            edit = diskLruCache.edit(key);
            //index不能大于VALUE_COUNT
            outputStream = edit.newOutputStream(0);
            Bitmap bitmap = value.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                edit.abort();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e(TAG, "put: edit.abort() e:" + e.getMessage());
            }
        }finally {
            try {
                edit.commit();
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "put: edit.commit()"+ e.getMessage());
            }
            if (outputStream !=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * get 通过key 得到 Value
     * @param key
     * @return
     */
    public Value get(String key){
        Tool.checkNotEmpty(key);
        InputStream inputStream = null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (null != snapshot){
                Value v = Value.getInstance();
                inputStream = snapshot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                v.setBitmap(bitmap);
                v.setKey(key);
                return v;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
