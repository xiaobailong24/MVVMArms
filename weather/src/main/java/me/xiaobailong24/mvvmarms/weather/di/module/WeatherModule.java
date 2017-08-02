package me.xiaobailong24.mvvmarms.weather.di.module;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.di.scope.ActivityScope;
import me.xiaobailong24.mvvmarms.mvvm.IModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherModel;

/**
 * Created by xiaobailong24 on 2017/7/31.
 * Dagger WeatherModule
 */
@Module
public class WeatherModule {

    @ActivityScope
    @Provides
    public IModel provideMainModel(WeatherModel weatherModel) {
        return weatherModel;
    }
}
