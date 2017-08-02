package me.xiaobailong24.mvvmarms.weather.di.module;

import android.app.Application;

import dagger.Module;

/**
 * Created by xiaobailong24 on 2017/7/22.
 * Dagger AppModule
 */
@Module(includes = {WeatherViewModelModule.class,
        WeatherNowViewModelModule.class})
public class AppModule {

    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

}
