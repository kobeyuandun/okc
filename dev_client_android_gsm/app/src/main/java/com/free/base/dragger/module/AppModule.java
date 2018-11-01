package com.free.base.dragger.module;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.free.base.dragger.qualifier.ApplicationContext;
import com.free.base.dragger.qualifier.GlideCache;
import com.free.base.dragger.qualifier.OkhttpCache;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    File provideCacheFile(@ApplicationContext Context context) {
        return getCacheFile(context);
    }

    @Singleton
    @Provides
    @OkhttpCache
    File provideOkhttpCache(File cacheDir) {
        File file = new File(cacheDir, "okhttp-cache");
        return makeDirs(file);
    }

    @Singleton
    @Provides
    @GlideCache
    File provideGlideCache(File cacheDir) {
        File file = new File(cacheDir, "glide-cache");
        return makeDirs(file);
    }

    private static File getCacheFile(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = context.getExternalCacheDir();
            if (file == null) {
                String packageName = context.getPackageName();
                file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + packageName);
                makeDirs(file);
            }
            return file;
        } else {
            return context.getCacheDir();
        }
    }

    public static File makeDirs(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
