package com.free.business.glide.cache;


import com.free.business.glide.Tool;
import com.free.business.glide.resource.Value;
import com.free.business.glide.resource.ValueCallback;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuandunbin782
 * @ClassName ActiveCache
 * @Description 活动缓存
 * @date 2020/6/8
 */
public class ActiveCache {
    private Map<String, WeakReference<Value>> mapList = new HashMap<>();
    private ReferenceQueue<Value> queue;
    private Thread thread;
    private boolean isCloseThread;
    private boolean isShoudongRemove;

    private ValueCallback valueCallback;
    public ActiveCache(ValueCallback valueCallback) {
        this.valueCallback = valueCallback;
    }

    /**
     * 添加 活动缓存
     * @param key
     * @param value
     */
    public void put(String key,Value value){
        Tool.checkNotEmpty(key);

        //绑定Value的监听
        value.setCallback(valueCallback);
        //存储
        mapList.put(key,new CustomWeakReference(value,getQueue(),key));
    }

    /**
     * 给外界获取 Value
     * @param key
     * @return
     */
    public Value get(String key){
        WeakReference<Value> valueWeakReference = mapList.get(key);
        if (null != valueWeakReference){
            return valueWeakReference.get();
        }
        return null;
    }

    /**
     * 手动 移除
     * @param key
     * @return
     */
    public Value remove(String key){
        isShoudongRemove = true; //不要去被动移除
        WeakReference<Value> remove = mapList.remove(key);
        isShoudongRemove = false; //被动移除开始生效
        if (null != remove){
            return remove.get();
        }
        return null;
    }

    /**
     * 释放 关闭 线程
     */
    public void closeThread(){
        isCloseThread = true;
        mapList.clear();
        System.gc();
    }

    /**
     * 弱引用管理器，监听什么时候被回收了
     */
    public class CustomWeakReference extends WeakReference<Value> {

        private final String key;

        public CustomWeakReference(Value referent, ReferenceQueue<? super Value> queue, String key) {
            super(referent, queue);
            this.key = key;
        }
    }

    private ReferenceQueue<Value> getQueue(){
        if (queue == null){
            queue = new ReferenceQueue<>();
            //此处感觉用线程池更合适
            thread = new Thread(){
                @Override
                public void run() {
                    super.run();

                    while (!isCloseThread){ //这个循环如何结束？
                        //手动移除，不能干活
                        if (!isShoudongRemove){
                            try {
                                Reference<? extends Value> remove = queue.remove();
                                //开始干活，被动移除
                                CustomWeakReference weakReference = (CustomWeakReference) remove;
                                if (mapList!=null && !mapList.isEmpty()){
                                    mapList.remove(weakReference.key);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            };
            thread.start();
        }
        return queue;
    }

}
