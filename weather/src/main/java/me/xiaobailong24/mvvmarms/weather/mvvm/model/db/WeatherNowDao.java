package me.xiaobailong24.mvvmarms.weather.mvvm.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;

/**
 * @author xiaobailong24
 * @date 2017/7/29
 * Room Database DAO
 */
@Dao
public interface WeatherNowDao {


    /**
     * 插入
     *
     * @param locations 地址信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Location... locations);

    /**
     * 查询
     *
     * @return 所有地址列表
     */
    @Query("SELECT * FROM location")
    Flowable<List<Location>> getAll();

    /**
     * 查询指定地址
     *
     * @param name 地址名称
     * @return 地址信息
     */
    @Query("SELECT * FROM location WHERE name = :name")
    Flowable<List<Location>> getLocationByName(String name);

    /**
     * 更新地址信息
     *
     * @param locations 要更新的地址列表
     */
    @Update
    void updateAll(Location... locations);

    /**
     * 删除地址
     *
     * @param locations 要删除的地址列表
     */
    @Delete
    void deleteLocation(Location... locations);
}
