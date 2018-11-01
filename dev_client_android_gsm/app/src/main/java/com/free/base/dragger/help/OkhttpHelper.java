package com.free.base.dragger.help;

import android.content.Context;
import android.os.Build;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.free.base.BuildConfig;
import com.free.base.dragger.qualifier.ApplicationContext;
import com.free.base.dragger.qualifier.OkhttpCache;
import com.free.base.okhttp.OkhttpCacheUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


@Singleton
public class OkhttpHelper {

    public static PersistentCookieJar cookieJar;
    private final OkHttpClient mOkHttpClient;

    @Inject
    public OkhttpHelper(@ApplicationContext Context context, @OkhttpCache File cacheFile){
        String formatUserAgent = formatUserAgent();
        UserAgentInterceptor userAgentInterceptor = new UserAgentInterceptor(formatUserAgent);
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(OkhttpCacheUtils.createCache(context,cacheFile.getAbsolutePath()))
                .cookieJar(cookieJar)
                .addNetworkInterceptor(userAgentInterceptor);
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        mOkHttpClient = builder.build();
    }

    public class UserAgentInterceptor implements Interceptor{
        private static final String USER_AGENT_HEADER_NAME = "User-Agent";
        private final String userAgentHeaderValue;

        public UserAgentInterceptor(String userAgentHeaderValue) {
            this.userAgentHeaderValue = userAgentHeaderValue;
        }
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request requestWithUserAgent = request.newBuilder()
                    .removeHeader(USER_AGENT_HEADER_NAME)
                    .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    public static String formatUserAgent(){
        StringBuilder builder = new StringBuilder();
        builder.append("Android");
        builder.append(";");
        builder.append(Build.VERSION.RELEASE);
        builder.append(";");
        builder.append(Build.VERSION.SDK_INT);
        builder.append(";");
        builder.append(BuildConfig.VERSION_NAME);
        builder.append(";");
        builder.append(BuildConfig.VERSION_CODE);
        builder.append(";");
//        builder.append(NetUtils.getNetworkType().name());
//        builder.append(";");
        builder.append(Build.MODEL);
//        builder.append(";");
//        builder.append(MiscUtils.getChannel());
        return builder.toString().replaceAll("\\s*","");
    }

    public static List<Cookie> getHttpCookieList(String httpUrl){
      return cookieJar.loadForRequest(HttpUrl.parse(httpUrl));
    }

    public static String getHttpCookieString(String httpUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Cookie> cookieList = getHttpCookieList(httpUrl);
        for (Cookie cookie : cookieList) {
            stringBuilder.append(cookie.toString()).append(";");
        }
        if (stringBuilder.length() > 0) {
            int last = stringBuilder.lastIndexOf(";");
            if (stringBuilder.length() - 1 == last) {
                stringBuilder.deleteCharAt(last);
            }
        }

        return stringBuilder.toString();
    }

    public static void clearCookie() {
        cookieJar.clear();
    }

    public OkHttpClient getHttpClient() {
        return mOkHttpClient;
    }
}
