package me.xiaobailong24.mvvmarms.repository;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import me.xiaobailong24.mvvmarms.repository.di.module.DBModule;
import me.xiaobailong24.mvvmarms.repository.utils.Preconditions;
import retrofit2.Retrofit;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * 数据管理层实现类
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {
    @Inject
    Application mApplication;
    @Inject
    Lazy<Retrofit> mRetrofit;
    private final Map<String, Object> mRetrofitServiceCache = new HashMap<>();
    private final Map<String, Object> mRoomDatabaseCache = new HashMap<>();
    @Inject
    DBModule.RoomConfiguration mRoomConfiguration;

    @Inject
    public RepositoryManager() {
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
        Preconditions.checkState(mRoomDatabaseCache.containsKey(database.getName()),
                "Unable to find %s,first call injectRoomDatabase(%s) in ConfigModule", database.getName(), database.getSimpleName());
        return (DB) mRoomDatabaseCache.get(database.getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <DB extends RoomDatabase> void injectRoomDatabase(Class<DB> database, String dbName) {
        if (!mRoomDatabaseCache.containsKey(database.getName())) {
            DB roomDatabase;
            RoomDatabase.Builder builder = Room.databaseBuilder(mApplication, database, dbName);
            if (mRoomConfiguration != null)//自定义 Room 配置
                mRoomConfiguration.configRoom(mApplication, builder);
            roomDatabase = (DB) builder.build();
            mRoomDatabaseCache.put(database.getName(), roomDatabase);
        }
    }

}
