package me.xiaobailong24.mvvmarms.weather.mvvm.model;

import android.app.Application;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.xiaobailong24.mvvmarms.mvvm.BaseModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.service.WeatherService;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.db.WeatherNowDb;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherNowResponse;

/**
 * @author xiaobailong24
 * @date 2017/7/22
 * MVVM WeatherNowModel
 */
public class WeatherNowModel extends BaseModel {

    @Inject
    public WeatherNowModel(Application application) {
        super(application);
    }


    /**
     * 从网络获取当前天气
     *
     * @param request 请求信息
     * @return 当前天气
     */
    public Observable<WeatherNowResponse> getWeatherNow(Map<String, String> request) {
        return mRepositoryManager
                .obtainRetrofitService(WeatherService.class)
                .getWeatherNow(request);
    }


    /**
     * 存储位置信息到 Room 数据库
     *
     * @param location 位置信息
     */
    public void saveLocation(Location location) {
        mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.class.getSimpleName())
                .weatherNowDao()
                .insertAll(location);
    }


    /**
     * 从 Room 数据库查询所有位置信息
     *
     * @return 所有位置信息列表
     */
    public List<Location> getAllLocations() {
        return mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.class.getSimpleName())
                .weatherNowDao()
                .getAll();
    }


    /**
     * 从 Room 数据库查询指定位置信息
     *
     * @param name 位置名称
     * @return 位置信息
     */
    public Location getLocationByName(String name) {
        return mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.class.getSimpleName())
                .weatherNowDao()
                .getLocationByName(name);
    }


    /**
     * 更新 Room 数据库位置信息
     *
     * @param location 要更新的位置信息
     */
    public void updateLocation(Location location) {
        mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.class.getSimpleName())
                .weatherNowDao()
                .updateAll(location);
    }


    /**
     * 删除Room数据库位置信息
     *
     * @param location 要删除的位置信息
     */
    public void deleteLocation(Location location) {
        mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.class.getSimpleName())
                .weatherNowDao()
                .deleteLocation(location);
    }
}
