package me.xiaobailong24.mvvmarms.weather.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment.WeatherDailyFragment;

/**
 * Created by xiaobailong24 on 2017/8/14.
 * Dagger WeatherDailyFragmentModule
 * 用于 Dagger.Android 依赖注入
 */
@Module
public abstract class WeatherDailyFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = WeatherDailyModule.class)
    abstract WeatherDailyFragment contributeWeatherDailyFragment();
}
