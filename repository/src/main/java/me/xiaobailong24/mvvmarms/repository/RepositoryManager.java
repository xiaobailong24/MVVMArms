package me.xiaobailong24.mvvmarms.repository;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import me.xiaobailong24.mvvmarms.repository.cache.Cache;
import me.xiaobailong24.mvvmarms.repository.cache.CacheType;
import me.xiaobailong24.mvvmarms.repository.di.module.DBModule;
import me.xiaobailong24.mvvmarms.repository.utils.Preconditions;
import retrofit2.Retrofit;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * 数据管理层实现类
 */
@SuppressWarnings("all")
@Singleton
public class RepositoryManager implements IRepositoryManager {
    private Application mApplication;
    private Lazy<Retrofit> mRetrofit;
    private Lazy<RxCache> mRxCache;
    private Cache<String, Object> mRetrofitServiceCache;
    private Cache<String, Object> mCacheServiceCache;
    private Cache<String, Object> mRoomDatabaseCache;
    private final Cache.Factory mCacheFactory;
    private DBModule.RoomConfiguration mRoomConfiguration;

    @Inject
    public RepositoryManager(Application application, Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache,
                             Cache.Factory cacheFactory, DBModule.RoomConfiguration roomConfiguration) {
        this.mApplication = application;
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
        this.mCacheFactory = cacheFactory;
        this.mRoomConfiguration = roomConfiguration;
    }

    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        if (mRetrofitServiceCache == null)
            mRetrofitServiceCache = mCacheFactory.build(CacheType.RETROFIT_SERVICE_CACHE_TYPE);
        Preconditions.checkNotNull(mRetrofitServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService;
        synchronized (mRetrofitServiceCache) {
            retrofitService = (T) mRetrofitServiceCache.get(service.getName());
            if (retrofitService == null) {
                retrofitService = mRetrofit.get().create(service);
                mRetrofitServiceCache.put(service.getName(), retrofitService);
            }
        }
        return retrofitService;
    }


    @Override
    public <T> T obtainCacheService(Class<T> cache) {
        if (mCacheServiceCache == null)
            mCacheServiceCache = mCacheFactory.build(CacheType.CACHE_SERVICE_CACHE_TYPE);
        Preconditions.checkNotNull(mCacheServiceCache, "Cannot return null from a Cache.Factory#build(int) method");
        T cacheService;
        synchronized (mCacheServiceCache) {
            cacheService = (T) mCacheServiceCache.get(cache.getName());
            if (cacheService == null) {
                cacheService = mRxCache.get().using(cache);
                mCacheServiceCache.put(cache.getName(), cacheService);
            }
        }
        return cacheService;
    }

    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll();
    }

    @Override
    public Context getContext() {
        return this.mApplication;
    }

    @Override
    public <DB extends RoomDatabase> DB obtainRoomDatabase(Class<DB> database, String dbName) {
        if (mRoomDatabaseCache == null)
            mRoomDatabaseCache = mCacheFactory.build(CacheType.ROOM_DATABASE_CACHE_TYPE);
        Preconditions.checkNotNull(mRoomDatabaseCache, "Cannot return null from a Cache.Factory#build(int) method");
        DB roomDatabase;
        synchronized (mRoomDatabaseCache) {
            roomDatabase = (DB) mRoomDatabaseCache.get(database.getName());
            if (roomDatabase == null) {
                RoomDatabase.Builder builder = Room.databaseBuilder(mApplication, database, dbName);
                if (mRoomConfiguration != null)//自定义 Room 配置
                    mRoomConfiguration.configRoom(mApplication, builder);
                roomDatabase = (DB) builder.build();
                mRoomDatabaseCache.put(database.getName(), roomDatabase);
            }
        }
        return roomDatabase;
    }
}
