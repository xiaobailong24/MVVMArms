package me.xiaobailong24.mvvmarms.weather.di.module;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.mvvm.IModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherNowModel;

/**
 * Created by xiaobailong24 on 2017/7/15.
 * Dagger WeatherNowModule
 */
@Module
public class WeatherNowModule {

    @FragmentScope
    @Provides
    public IModel provideWeatherNowModel(WeatherNowModel weatherNowModel) {
        return weatherNowModel;
    }

}
