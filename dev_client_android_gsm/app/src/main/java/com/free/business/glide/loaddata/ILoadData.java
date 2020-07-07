package com.free.business.glide.loaddata;

import android.content.Context;

import com.free.business.glide.resource.Value;


/**
 * @author yuandunbin782
 * @ClassName ILoadData
 * @Description 加载外部资源 标准制定
 * @date 2020/6/12
 */
public interface ILoadData {
    //加载外部资源的行为
    Value loadResource(String path, ResponseListener responseListener, Context context);
}
