package me.xiaobailong24.mvvmarms.repository.di.module;

import android.app.Application;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import me.xiaobailong24.mvvmarms.repository.http.BaseUrl;
import me.xiaobailong24.mvvmarms.repository.http.GlobalHttpHandler;
import me.xiaobailong24.mvvmarms.repository.utils.DataHelper;
import me.xiaobailong24.mvvmarms.repository.utils.RequestInterceptor;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * RepositoryConfigModule
 */
@Module
public class RepositoryConfigModule {
    private HttpUrl mApiUrl;
    private BaseUrl mBaseUrl;
    private File mCacheFile;
    private GlobalHttpHandler mHandler;
    private List<Interceptor> mInterceptors;
    private ResponseErrorListener mErrorListener;
    private ClientModule.RetrofitConfiguration mRetrofitConfiguration;
    private ClientModule.OkhttpConfiguration mOkhttpConfiguration;
    private ClientModule.GsonConfiguration mGsonConfiguration;
    private RequestInterceptor.Level mPrintHttpLogLevel;
    private DBModule.RoomConfiguration mRoomConfiguration;


    private RepositoryConfigModule(Builder builder) {
        this.mApiUrl = builder.apiUrl;
        this.mBaseUrl = builder.baseUrl;
        this.mHandler = builder.handler;
        this.mCacheFile = builder.cacheFile;
        this.mInterceptors = builder.interceptors;
        this.mErrorListener = builder.responseErrorListener;
        this.mRetrofitConfiguration = builder.retrofitConfiguration;
        this.mOkhttpConfiguration = builder.okhttpConfiguration;
        this.mGsonConfiguration = builder.gsonConfiguration;
        this.mPrintHttpLogLevel = builder.printHttpLogLevel;
        this.mRoomConfiguration = builder.roomConfiguration;
    }

    public static Builder builder() {
        return new Builder();
    }


//    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors() {
        return mInterceptors;
    }


//    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        if (mBaseUrl != null) {
            HttpUrl httpUrl = mBaseUrl.url();
            if (httpUrl != null) {
                return httpUrl;
            }
        }
        return mApiUrl == null ? HttpUrl.parse("https://api.github.com/") : mApiUrl;
    }

//    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        //提供缓存文件
        return mCacheFile == null ? DataHelper.getCacheFile(application) : mCacheFile;
    }

//    @Singleton
    @Provides
    @Nullable
    GlobalHttpHandler provideGlobalHttpHandler() {
        return mHandler;//处理Http请求和响应结果
    }


//    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }

//    @Singleton
    @Provides
    @Nullable
    ClientModule.RetrofitConfiguration provideRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

//    @Singleton
    @Provides
    @Nullable
    ClientModule.OkhttpConfiguration provideOkhttpConfiguration() {
        return mOkhttpConfiguration;
    }

//    @Singleton
    @Provides
    @Nullable
    ClientModule.GsonConfiguration provideGsonConfiguration() {
        return mGsonConfiguration;
    }


//    @Singleton
    @Provides
    @Nullable
    RequestInterceptor.Level providePrintHttpLogLevel() {
        return mPrintHttpLogLevel;
    }

//    @Singleton
    @Provides
    DBModule.RoomConfiguration provideRoomConfiguration() {
        return mRoomConfiguration == null ? DBModule.RoomConfiguration.EMPTY : mRoomConfiguration;
    }

    public static final class Builder {
        private HttpUrl apiUrl;
        private BaseUrl baseUrl;
        private File cacheFile;
        private GlobalHttpHandler handler;
        private List<Interceptor> interceptors;
        private ResponseErrorListener responseErrorListener;
        private ClientModule.RetrofitConfiguration retrofitConfiguration;
        private ClientModule.OkhttpConfiguration okhttpConfiguration;
        private ClientModule.GsonConfiguration gsonConfiguration;
        private RequestInterceptor.Level printHttpLogLevel;
        private DBModule.RoomConfiguration roomConfiguration;


        private Builder() {
        }

        public Builder baseUrl(String baseUrl) {//基础url
            if (TextUtils.isEmpty(baseUrl)) {
                throw new IllegalArgumentException("BaseUrl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(BaseUrl baseUrl) {
            if (baseUrl == null) {
                throw new IllegalArgumentException("BaseUrl can not be null");
            }
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public Builder globalHttpHandler(GlobalHttpHandler handler) {//用来处理http响应结果
            this.handler = handler;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
            if (interceptors == null)
                interceptors = new ArrayList<>();
            this.interceptors.add(interceptor);
            return this;
        }

        public Builder responseErrorListener(ResponseErrorListener listener) {//处理所有Rxjava的onError逻辑
            this.responseErrorListener = listener;
            return this;
        }

        public Builder retrofitConfiguration(ClientModule.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(ClientModule.OkhttpConfiguration okhttpConfiguration) {
            this.okhttpConfiguration = okhttpConfiguration;
            return this;
        }

        public Builder gsonConfiguration(ClientModule.GsonConfiguration gsonConfiguration) {
            this.gsonConfiguration = gsonConfiguration;
            return this;
        }

        public Builder printHttpLogLevel(RequestInterceptor.Level printHttpLogLevel) { //是否让框架打印 Http 的请求和响应信息
            if (printHttpLogLevel == null)
                throw new IllegalArgumentException("printHttpLogLevel == null. Use RequestInterceptor.Level.NONE instead.");
            this.printHttpLogLevel = printHttpLogLevel;
            return this;
        }

        public Builder roomConfiguration(DBModule.RoomConfiguration roomConfiguration) {
            this.roomConfiguration = roomConfiguration;
            return this;
        }


        public RepositoryConfigModule build() {
            return new RepositoryConfigModule(this);
        }

    }
}
