package com.free.business.glide;

import android.content.Context;

/**
 * @author yuandunbin782
 * @ClassName GlideBuilder
 * @Description
 * @date 2020/6/12
 */
public class GlideBuilder {
    public GlideBuilder(Context context) {
    }

    public Glide build(){
        RequestManagerRetriver requestManagerRetriver = new RequestManagerRetriver();
        Glide glide = new Glide(requestManagerRetriver);
        return glide;
    }
}
