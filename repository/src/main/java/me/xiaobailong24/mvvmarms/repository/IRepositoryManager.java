package me.xiaobailong24.mvvmarms.repository;

import android.arch.persistence.room.RoomDatabase;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * 数据管理层接口
 */
public interface IRepositoryManager {
    //懒加载获取 Retrofit Service
    <T> T obtainRetrofitService(Class<T> service);

    //懒加载获取 Room 数据库
    <DB extends RoomDatabase> DB obtainRoomDatabase(Class<DB> database, String dbName);
}
