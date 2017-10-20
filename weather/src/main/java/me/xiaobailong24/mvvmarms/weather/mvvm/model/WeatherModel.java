package me.xiaobailong24.mvvmarms.weather.mvvm.model;

import android.app.Application;

import java.util.List;

import javax.inject.Inject;

import me.xiaobailong24.mvvmarms.mvvm.BaseModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.db.WeatherNowDb;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;

/**
 * @author xiaobailong24
 * @date 2017/7/31
 * MVVM WeatherModel
 */
public class WeatherModel extends BaseModel {

    @Inject
    public WeatherModel(Application application) {
        super(application);
    }


    /**
     * 从Room数据库查询所有位置信息
     *
     * @return 所有位置信息列表
     */
    public List<Location> getAllLocations() {
        return mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.class.getSimpleName())
                .weatherNowDao()
                .getAll();
    }

}
