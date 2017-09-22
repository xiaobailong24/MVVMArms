package me.xiaobailong24.mvvmarms.weather.mvvm.model;

import java.util.List;

import javax.inject.Inject;

import me.xiaobailong24.mvvmarms.mvvm.BaseModel;
import me.xiaobailong24.mvvmarms.repository.IRepositoryManager;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.db.WeatherNowDb;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;

/**
 * Created by xiaobailong24 on 2017/7/31.
 * MVVM WeatherModel
 */

public class WeatherModel extends BaseModel {

    @Inject
    public WeatherModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    //从Room数据库查询所有位置信息
    public List<Location> getAllLocations() {
        return mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.class.getSimpleName())
                .weatherNowDao()
                .getAll();
    }

}
