package me.xiaobailong24.mvvmarms.repository;


import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import retrofit2.Retrofit;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * 数据管理层实现类
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {
    private Application mApplication;
    private Lazy<Retrofit> mRetrofit;
    private final Map<String, Object> mRetrofitServiceCache = new HashMap<>();
    private final Map<String, Object> mRoomDatabaseServiceCache = new HashMap<>();

    @Inject
    public RepositoryManager(Application application, Lazy<Retrofit> retrofit) {
        this.mApplication = application;
        this.mRetrofit = retrofit;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T obtainRetrofitService(Class<T> service) {
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
    @SuppressWarnings("unchecked")
    public <DB extends RoomDatabase> DB obtainRoomDatabase(Class<DB> database, String dbName) {
        DB roomDatabase;
        synchronized (mRoomDatabaseServiceCache) {
            roomDatabase = (DB) mRoomDatabaseServiceCache.get(database.getName());
            if (roomDatabase == null) {
                roomDatabase = Room.databaseBuilder(mApplication,
                        database, dbName).build();
                mRoomDatabaseServiceCache.put(database.getName(), roomDatabase);
            }
        }
        return roomDatabase;
    }


}
