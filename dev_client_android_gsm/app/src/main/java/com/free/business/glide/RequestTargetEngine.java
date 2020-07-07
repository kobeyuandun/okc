package com.free.business.glide;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.free.business.glide.cache.ActiveCache;
import com.free.business.glide.cache.MemoryCache;
import com.free.business.glide.cache.MemoryCacheCallback;
import com.free.business.glide.cache.disk.DiskLruCacheImpl;
import com.free.business.glide.fragment.LifecycleCallback;
import com.free.business.glide.loaddata.LoadDataManager;
import com.free.business.glide.loaddata.ResponseListener;
import com.free.business.glide.resource.Key;
import com.free.business.glide.resource.Value;
import com.free.business.glide.resource.ValueCallback;


/**
 * @author yuandunbin782
 * @ClassName RequestTargetEngine
 * @Description
 * @date 2020/6/11
 */
public class RequestTargetEngine implements LifecycleCallback, ValueCallback, MemoryCacheCallback, ResponseListener {
    private static final String TAG = "RequestTargetEngine";
    //活动缓存
    private ActiveCache activeCache;
    //内存缓存
    private MemoryCache memoryCache;
    //磁盘缓存
    private DiskLruCacheImpl diskLruCache;
    private final static int MEORY_MAX_SIZE = 1024 * 1024 * 10;
    private ImageView imageView;
    private String key;
    private String path;
    private Context context;

    public RequestTargetEngine() {
        if (activeCache == null) {
            //回调给外界，Value资源不再使用了，设置监听
            activeCache = new ActiveCache(this);
        }
        if (memoryCache == null) {
            memoryCache = new MemoryCache(MEORY_MAX_SIZE);
            memoryCache.setMemoryCacheCallback(this);
        }
        diskLruCache = new DiskLruCacheImpl();
    }

    public void into(ImageView imageView) {
        this.imageView = imageView;
        Tool.checkNotEmpty(imageView);
        Tool.assertMainThread();

        // 触发 缓存机制
        // TODO 加载资源 ---》缓存机制 ---》网络/sd/加载资源成功后 ---》把资源保存到缓存中
        Value value = cacheAction();
        if (null != value) {
            // null != value  ==  一定使用了一次 + 1

            // 使用完成了 - 1
            value.nonUseAction();

            imageView.setImageBitmap(value.getBitmap()); // 显示图片
        }
    }

    /**
     * 加载资源--》缓存机制 --》 网络/sd/加载资源成功后 ---》把资源保存到缓存中
     *
     * @return
     */
    private Value cacheAction() {
        //1、判断活动缓存是否有资源，如果有资源就返回    否则就继续往下找
        Value value = activeCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载的是在（活动缓存）中获取的资源>>>");
            // 返回 == 代表  使用了一次  Value
            value.useAction(); //+1
            return value;
        }
        //2、判断内存缓存是否对资源，如果有资源 剪切（内存---》活动）
        value = memoryCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载的是在（内存缓存）中获取的资源>>>");
            memoryCache.shoudongRemove(key);//移除内存缓存
            activeCache.put(key, value);//将内存缓存中的元素，加入到活动缓存中
            // 返回 == 代表  使用了一次  Value
            value.useAction(); //+1
            return value;
        }
        //3、从磁盘缓存中去找，如果找到了，把磁盘缓存中的元素，加入到活动缓存中。。。
        value = diskLruCache.get(key);
        if (null != value) {
            Log.d(TAG, "cacheAction: 本次加载的是在（磁盘缓存）中获取的资源>>>");
            //把磁盘缓存中的元素加入到活动缓存中
            activeCache.put(key, value);
            //可能会有后续扩展

            // 返回 == 代表  使用了一次  Value
            value.useAction(); //+1
            return value;
        }

        //4、去加载外部资源，去网络 、SDcard 等去找
        value = new LoadDataManager().loadResource(path, this, context);
        if (value != null) {
            return value;
        }
        return null;
    }

    /**
     * RequestManager传递过来的值
     *
     * @param path
     * @param requestManagerContext
     */
    public void loadValueInitAction(String path, Context requestManagerContext) {
        this.path = path;
        this.context = requestManagerContext;
        this.key = new Key(path).getKey();
    }

    @Override
    public void entryRemovedMemoryCache(String key, Value OldValue) {

    }

    @Override
    public void glideInitAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 已经开启了 初始化了....");
    }

    @Override
    public void glideStopAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 已经停止中 ....");
    }

    @Override
    public void glideRecycleAction() {
        Log.d(TAG, "glideInitAction: Glide生命周期之 进行释放操作 缓存策略释放操作等 >>>>>> ....");
    }

    /**
     * 活动缓存间接的调用 Value发出的
     * 专门给Value，不再使用的回调接口
     * 监听的方法（Value不再使用了）
     *
     * @param key
     * @param value
     */
    @Override
    public void valueNonUseListener(String key, Value value) {
        if (key != null && value != null) {
            memoryCache.put(key, value);//加入到内存缓存
        }
    }

    /**
     * 外置资源 成功 回调
     * @param value
     */
    @Override
    public void responseSuccess(Value value) {
        if (null != value){
            saveCache(key,value);//触发保存
            imageView.setImageBitmap(value.getBitmap());
        }

    }

    /**
     * 外置资源加载成功后，保存到磁盘缓存
     * @param key
     * @param value
     */
    private void saveCache(String key, Value value) {
        Log.d(TAG, "saveCahce: >>>>>>>>>>>>>>>>>>>>>>>>>> 加载外置资源成功后 ，保存到缓存中， key:" + key + " value:" + value);
        value.setKey(key);
        if (diskLruCache !=null){
            diskLruCache.put(key, value);//保存到磁盘缓存
        }
    }

    @Override
    public void responseException(Exception e) {
        Log.d(TAG, "responseException: 加载外部资源失败 e:" + e.getMessage());
    }
}
