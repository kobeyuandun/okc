package com.free.base.dragger.help;

import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
@Singleton
public class RetrofitHelper {

    private final Retrofit mRetrofit;

    @Inject
    public RetrofitHelper() {
        mRetrofit = new Retrofit.Builder().baseUrl("https://github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
