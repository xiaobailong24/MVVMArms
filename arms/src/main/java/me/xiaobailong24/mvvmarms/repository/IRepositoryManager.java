package me.xiaobailong24.mvvmarms.repository;


import android.arch.persistence.room.RoomDatabase;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * 数据管理层接口
 */
public interface IRepositoryManager {

    //懒加载获取Retrofit Service
    <T> T obtainRetrofitService(Class<T> service);

    //获取Room数据库
    <DB extends RoomDatabase> DB obtainRoomDatabase(Class<DB> database, String dbName);

    /**
     * 由于{@link RoomDatabase.Builder}的局限性，为了自定义 Room，不再使用懒加载的方式。
     *
     * @param database RoomDatabase
     */
    <DB extends RoomDatabase> void injectRoomDatabase(Class<DB> database, String dbName);
}
