package com.free.base.dragger.module;

import com.free.base.config.Api;
import com.free.base.config.MyApi;
import com.free.base.dragger.help.OkhttpHelper;
import com.free.base.dragger.help.RetrofitHelper;
import com.free.base.dragger.qualifier.MyUrl;
import com.free.base.dragger.scope.MyAppScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class MyAppModule {

    @MyAppScope
    @Provides
    @MyUrl
    Retrofit provideMyUrlRetrofit(OkhttpHelper okhttpHelper, RetrofitHelper retrofitHelper){
       return createMyApiRetrofit(okhttpHelper,retrofitHelper, Api.sServerApiUrl);
    }

    private Retrofit createMyApiRetrofit(OkhttpHelper okhttpHelper, RetrofitHelper retrofitHelper, String url) {
        Retrofit.Builder builder = retrofitHelper.getRetrofit().newBuilder();
        OkHttpClient.Builder client = okhttpHelper.getHttpClient().newBuilder();
        return builder.baseUrl(url).client(client.build()).build();
    }

    @MyAppScope
    @Provides
    MyApi provideMyApi(@MyUrl Retrofit retrofit){
        return retrofit.create(MyApi.class);
    }
}
