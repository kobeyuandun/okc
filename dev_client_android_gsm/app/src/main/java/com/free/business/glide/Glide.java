package com.free.business.glide;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

/**
 * @author yuandunbin782
 * @ClassName Glide
 * @Description
 * @date 2020/6/8
 */
public class Glide {
    RequestManagerRetriver retriver;

    public Glide(RequestManagerRetriver retriver) {
        this.retriver = retriver;
    }

    public static RequestManager with(FragmentActivity fragmentActivity) {
        return getRetriver(fragmentActivity).get(fragmentActivity);
    }

    public static RequestManager with(Activity activity) {
        return getRetriver(activity).get(activity);
    }

    public static RequestManager with(Context context) {
        return getRetriver(context).get(context);
    }

    private static RequestManagerRetriver getRetriver(Context context) {
        return Glide.get(context).getRetriver();
    }

    private static Glide get(Context context) {
        return new GlideBuilder(context).build();
    }

    public RequestManagerRetriver getRetriver() {
        return retriver;
    }
}
