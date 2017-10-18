package me.xiaobailong24.mvvmarms.weather.di.module;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import me.xiaobailong24.mvvmarms.di.scope.ViewModelScope;
import me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel.WeatherDailyViewModel;

/**
 * Created by xiaobailong24 on 2017/8/14.
 * MVVM WeatherDailyViewModelModule
 */
@Module
public abstract class WeatherDailyViewModelModule {

    @Binds
    @IntoMap
    @ViewModelScope(WeatherDailyViewModel.class)
    abstract ViewModel bindWeatherDailyViewModel(WeatherDailyViewModel weatherDailyViewModel);
}
