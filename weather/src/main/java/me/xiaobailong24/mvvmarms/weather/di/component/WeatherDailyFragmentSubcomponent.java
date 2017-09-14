package me.xiaobailong24.mvvmarms.weather.di.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.weather.di.module.WeatherDailyFragmentModule;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment.WeatherDailyFragment;

/**
 * Created by xiaobailong24 on 2017/7/15.
 * Dagger WeatherDailyFragmentSubcomponent
 */
@FragmentScope
//@Subcomponent
@Subcomponent(modules = WeatherDailyFragmentModule.class)
public interface WeatherDailyFragmentSubcomponent extends AndroidInjector<WeatherDailyFragment> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<WeatherDailyFragment> {
    }
}
