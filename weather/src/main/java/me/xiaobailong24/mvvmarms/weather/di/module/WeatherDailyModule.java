package me.xiaobailong24.mvvmarms.weather.di.module;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.mvvm.IModel;
import me.xiaobailong24.mvvmarms.weather.R;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherDailyModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter.WeatherDailyAdapter;

/**
 * @author xiaobailong24
 * @date 2017/8/14
 * Dagger WeatherDailyModule
 */
@Module
public class WeatherDailyModule {

    @FragmentScope
    @Provides
    IModel provideWeatherDailyModel(WeatherDailyModel weatherDailyModel) {
        return weatherDailyModel;
    }

    @FragmentScope
    @Provides
    WeatherDailyAdapter provideAdapter() {
        return new WeatherDailyAdapter(R.layout.super_item_daily, null);
    }
}
