package me.xiaobailong24.mvvmarms.weather.di.module;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.mvvm.IModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherDailyModel;

/**
 * Created by xiaobailong24 on 2017/8/14.
 * Dagger WeatherDailyModule
 */
@Module
public class WeatherDailyModule {

    @FragmentScope
    @Provides
    public IModel provideWeatherDailyModel(WeatherDailyModel weatherDailyModel) {
        return weatherDailyModel;
    }
}
