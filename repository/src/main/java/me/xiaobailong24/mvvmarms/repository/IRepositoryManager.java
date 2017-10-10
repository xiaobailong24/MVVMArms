package me.xiaobailong24.mvvmarms.repository;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * 数据管理层接口
 */
public interface IRepositoryManager {
    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service: Retrofit Service Class
     * @param <T>:     Retrofit Service
     * @return Retrofit Service
     */
    <T> T obtainRetrofitService(Class<T> service);

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache: Cache Service Class
     * @param <T>:   Cache Service
     * @return Cache Service
     */
    <T> T obtainCacheService(Class<T> cache);

    /**
     * 清理所有缓存
     */
    void clearAllCache();

    /**
     * 获取 Context(Application)
     */
    Context getContext();

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param database: RoomDatabase Class
     * @param <DB>:     RoomDatabase
     * @return RoomDatabase
     */
    <DB extends RoomDatabase> DB obtainRoomDatabase(Class<DB> database, String dbName);
}
