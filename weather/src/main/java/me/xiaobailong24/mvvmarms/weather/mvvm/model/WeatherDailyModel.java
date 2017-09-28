package me.xiaobailong24.mvvmarms.weather.mvvm.model;

import android.app.Application;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.xiaobailong24.mvvmarms.mvvm.BaseModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.service.WeatherService;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherDailyResponse;

/**
 * Created by xiaobailong24 on 2017/8/14.
 * MVVM WeatherDailyModel
 */
public class WeatherDailyModel extends BaseModel {

    @Inject
    public WeatherDailyModel(Application application) {
        super(application);
    }

    //从网络获取未来三天天气
    public Observable<WeatherDailyResponse> getWeatherDaily(Map<String, String> request) {
        return mRepositoryManager
                .obtainRetrofitService(WeatherService.class)
                .getWeatherDaily(request);
    }
}
