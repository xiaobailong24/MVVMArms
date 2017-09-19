package me.xiaobailong24.mvvmarms.weather.di.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import me.xiaobailong24.mvvmarms.di.scope.ActivityScope;
import me.xiaobailong24.mvvmarms.weather.di.module.WeatherModule;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.activity.WeatherActivity;

/**
 * Created by xiaobailong24 on 2017/7/13.
 * Dagger WeatherActivitySubcomponent
 */
@ActivityScope
@Subcomponent(modules = WeatherModule.class)///DataModule
public interface WeatherActivitySubcomponent extends AndroidInjector<WeatherActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<WeatherActivity> {
    }
}
