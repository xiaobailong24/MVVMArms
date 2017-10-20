package me.xiaobailong24.mvvmarms.weather.di.module;

import android.arch.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import me.xiaobailong24.mvvmarms.di.scope.ViewModelScope;
import me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel.WeatherViewModel;

/**
 * @author xiaobailong24
 * @date 2017/7/31
 * Dagger WeatherViewModelModule
 * 包含ViewModelFactoryModule提供ViewModelProvider.Factory
 */
@Module(includes = {WeatherModule.class})
public abstract class WeatherViewModelModule {

    @Binds
    @IntoMap
    @ViewModelScope(WeatherViewModel.class)
    abstract ViewModel bindWeatherViewModel(WeatherViewModel weatherViewModel);

    //    @Binds
    //    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
