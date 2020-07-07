package com.free.business.glide;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

/**
 * @author yuandunbin782
 * @ClassName RequestManagerRetriver
 * @Description  管理 RequestManager
 * @date 2020/6/12
 */
public class RequestManagerRetriver {
    public RequestManager get(FragmentActivity fragmentActivity){
        return new RequestManager(fragmentActivity);
    }

    public RequestManager get(Activity activity){
        return new RequestManager(activity);
    }

    public RequestManager get(Context context){
        return new RequestManager(context);
    }
}
