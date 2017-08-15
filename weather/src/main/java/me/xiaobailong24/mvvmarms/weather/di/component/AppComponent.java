package me.xiaobailong24.mvvmarms.weather.di.component;

import dagger.Component;
import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;
import me.xiaobailong24.mvvmarms.di.scope.AppScope;
import me.xiaobailong24.mvvmarms.weather.app.MainApp;
import me.xiaobailong24.mvvmarms.weather.di.module.AppModule;
import me.xiaobailong24.mvvmarms.weather.di.module.WeatherActivityModule;
import me.xiaobailong24.mvvmarms.weather.di.module.WeatherDailyFragmentModule;
import me.xiaobailong24.mvvmarms.weather.di.module.WeatherNowFragmentModule;

/**
 * Created by xiaobailong24 on 2017/7/15.
 * Dagger AppComponent
 */
@AppScope
@Component(dependencies = ArmsComponent.class,
        modules = {AppModule.class,
                WeatherActivityModule.class,
                WeatherNowFragmentModule.class, WeatherDailyFragmentModule.class})
public interface AppComponent {
    void inject(MainApp mainApp);
}
