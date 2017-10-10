package me.xiaobailong24.mvvmarms.repository.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import me.xiaobailong24.mvvmarms.repository.IRepositoryManager;
import me.xiaobailong24.mvvmarms.repository.RepositoryManager;
import me.xiaobailong24.mvvmarms.repository.cache.Cache;
import me.xiaobailong24.mvvmarms.repository.cache.CacheType;
import retrofit2.Retrofit;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * Dagger RepositoryModule
 */
@Module
public class RepositoryModule {
    private Application mApplication;

    public RepositoryModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    IRepositoryManager provideRepositoryManager(Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache,
                                                Cache.Factory cacheFactory,
                                                DBModule.RoomConfiguration roomConfiguration) {
        return new RepositoryManager(mApplication, retrofit, rxCache, cacheFactory, roomConfiguration);
    }

    @Singleton
    @Provides
    Cache<String, Object> provideExtras(Cache.Factory cacheFactory) {
        return cacheFactory.build(CacheType.EXTRAS_CACHE_TYPE);
    }
}
