package me.xiaobailong24.mvvmarms.weather.mvvm.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;

/**
 * @author xiaobailong24
 * @date 2017/7/29
 * Room Database
 */
@Database(entities = Location.class, version = 3)
public abstract class WeatherNowDb extends RoomDatabase {
    public static final String DB_NAME = WeatherNowDb.class.getSimpleName();

    /**
     * 获取数据库
     *
     * @return WeatherNowDao
     */
    public abstract WeatherNowDao weatherNowDao();
}
