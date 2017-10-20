package me.xiaobailong24.mvvmarms.weather.di.component;

import dagger.Component;
import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;
import me.xiaobailong24.mvvmarms.di.scope.AppScope;
import me.xiaobailong24.mvvmarms.weather.app.MainApp;
import me.xiaobailong24.mvvmarms.weather.di.module.AppModule;

/**
 * @author xiaobailong24
 * @date 2017/7/15
 * Dagger AppComponent
 */
@AppScope
@Component(dependencies = ArmsComponent.class,
        modules = AppModule.class)
public interface AppComponent {
    /**
     * Dagger 注入
     *
     * @param mainApp MainApp
     */
    void inject(MainApp mainApp);
}
