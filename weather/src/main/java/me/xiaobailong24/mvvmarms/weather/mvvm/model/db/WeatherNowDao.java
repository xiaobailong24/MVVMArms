package me.xiaobailong24.mvvmarms.weather.mvvm.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;

/**
 * Created by xiaobailong24 on 2017/7/29.
 * Room Database DAO
 */
@Dao
public interface WeatherNowDao {

    @Insert
    void insertAll(Location... locations);

    @Query("SELECT * FROM location")
    List<Location> getAll();

    @Query("SELECT * FROM location WHERE name = :name")
    Location getLocationByName(String name);

    @Update
    void updateAll(Location... locations);

    @Delete
    void deleteLocation(Location... locations);
}
