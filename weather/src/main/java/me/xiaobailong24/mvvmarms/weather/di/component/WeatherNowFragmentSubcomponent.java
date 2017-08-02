package me.xiaobailong24.mvvmarms.weather.di.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.weather.di.module.WeatherNowFragmentModule;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment.WeatherNowFragment;

/**
 * Created by xiaobailong24 on 2017/7/15.
 * Dagger WeatherNowFragmentSubcomponent
 */
@FragmentScope
@Subcomponent(modules = WeatherNowFragmentModule.class)
public interface WeatherNowFragmentSubcomponent extends AndroidInjector<WeatherNowFragment> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<WeatherNowFragment> {
    }
}
