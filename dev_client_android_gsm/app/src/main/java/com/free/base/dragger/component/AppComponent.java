package com.free.base.dragger.component;

import android.content.Context;

import com.free.base.dragger.help.OkhttpHelper;
import com.free.base.dragger.help.RetrofitHelper;
import com.free.base.dragger.module.AppModule;
import com.free.base.dragger.qualifier.ApplicationContext;
import com.free.base.dragger.qualifier.GlideCache;
import com.free.base.dragger.qualifier.OkhttpCache;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    @ApplicationContext
    Context getContext();

    File getCacheDir();

    @OkhttpCache
    File getOkhttpCacheDir();

    @GlideCache
    File getGlideCacheDir();

    OkhttpHelper okhttphelper();

    RetrofitHelper retrofitHelper();
}
