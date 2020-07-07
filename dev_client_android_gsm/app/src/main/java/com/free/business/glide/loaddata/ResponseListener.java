package com.free.business.glide.loaddata;


import com.free.business.glide.resource.Value;

/**
 * @author yuandunbin782
 * @ClassName ResponseListener
 * @Description 加载外部资源 成功 和 失败回调
 * @date 2020/6/11
 */
public interface ResponseListener {
    void responseSuccess(Value value);

    void responseException(Exception e);
}
