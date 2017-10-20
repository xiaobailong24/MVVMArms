package me.xiaobailong24.mvvmarms.repository.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import me.xiaobailong24.mvvmarms.repository.http.GlobalHttpHandler;
import me.xiaobailong24.mvvmarms.repository.utils.DataHelper;
import me.xiaobailong24.mvvmarms.repository.http.RequestInterceptor;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Dagger ClientModule
 */
@Module
public class ClientModule {
    private Application mApplication;
    private static final int TIME_OUT = 10;

    public ClientModule(@NonNull Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@Nullable RetrofitConfiguration configuration,
                             Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl) {
        builder
                //域名
                .baseUrl(httpUrl)
                //设置okhttp
                .client(client)
                // TODO: 2017/10/20
                //                .addCallAdapterFactory(new LiveDataCallAdapterFactory())//使用LiveData
                //使用rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //使用Gson
                .addConverterFactory(GsonConverterFactory.create());
        if (configuration != null) {
            configuration.configRetrofit(mApplication, builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(@Nullable OkhttpConfiguration configuration,
                               OkHttpClient.Builder builder, Interceptor intercept,
                               @Nullable List<Interceptor> interceptors, @Nullable final GlobalHttpHandler handler) {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);

        if (handler != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
                }
            });
        }

        //如果外部提供了interceptor的集合则遍历添加
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (configuration != null) {
            configuration.configOkhttp(mApplication, builder);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }


    @Singleton
    @Provides
    Interceptor provideInterceptor(RequestInterceptor intercept) {
        //打印请求信息的拦截器
        return intercept;
    }


    @Singleton
    @Provides
    RxErrorHandler provideRxErrorHandler(ResponseErrorListener listener) {
        return RxErrorHandler
                .builder()
                .with(mApplication)
                .responseErrorListener(listener)
                .build();
    }


    @Singleton
    @Provides
    Gson provideGson(@Nullable GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null) {
            configuration.configGson(mApplication, builder);
        }
        return builder.create();
    }


    /**
     * 提供 {@link RxCache}
     *
     * @param cacheDirectory RxCache 缓存路径
     * @return RxCache
     */
    @Singleton
    @Provides
    RxCache provideRxCache(@Nullable RxCacheConfiguration configuration, @Named("RxCacheDirectory") File cacheDirectory) {
        RxCache.Builder builder = new RxCache.Builder();
        if (configuration != null) {
            configuration.configRxCache(mApplication, builder);
        }
        return builder
                .persistence(cacheDirectory, new GsonSpeaker());
    }

    /**
     * 需要单独给 {@link RxCache} 提供缓存路径
     *
     * @param cacheDir RxCache 缓存路径
     * @return File
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    File provideRxCacheDirectory(File cacheDir) {
        File cacheDirectory = new File(cacheDir, "RxCache");
        return DataHelper.makeDirs(cacheDirectory);
    }

    public interface RetrofitConfiguration {
        /**
         * 提供接口，自定义配置 Retrofit
         *
         * @param context Context
         * @param builder Retrofit.Builder
         */
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkhttpConfiguration {
        /**
         * 提供接口，自定义配置 OkHttpClient
         *
         * @param context Context
         * @param builder OkHttpClient.Builder
         */
        void configOkhttp(Context context, OkHttpClient.Builder builder);
    }

    public interface GsonConfiguration {
        /**
         * 提供接口，自定义配置 Gson
         *
         * @param context Context
         * @param builder GsonBuilder
         */
        void configGson(Context context, GsonBuilder builder);
    }

    public interface RxCacheConfiguration {
        /**
         * 提供接口，自定义配置 RxCache
         *
         * @param context Context
         * @param builder RxCache.Builder
         */
        void configRxCache(Context context, RxCache.Builder builder);
    }
}
