package me.xiaobailong24.mvvmarms.weather.di.module;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import me.xiaobailong24.mvvmarms.di.scope.ViewModelScope;
import me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel.WeatherNowViewModel;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Dagger WeatherNowViewModelModule
 * 包含ViewModelFactoryModule提供ViewModelProvider.Factory
 */
@Module
public abstract class WeatherNowViewModelModule {

    @Binds
    @IntoMap
    @ViewModelScope(WeatherNowViewModel.class)
    abstract ViewModel bindWeatherNowViewModel(WeatherNowViewModel weatherNowViewModel);
}
