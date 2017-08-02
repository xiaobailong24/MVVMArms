package me.xiaobailong24.mvvmarms.weather.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.xiaobailong24.mvvmarms.di.scope.ActivityScope;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.activity.WeatherActivity;

/**
 * Created by xiaobailong24 on 2017/7/13.
 * Dagger WeatherActivityModule
 * 用于 Dagger.Android 依赖注入
 */
@Module
public abstract class WeatherActivityModule {

/*    @Binds
    @IntoMap
    @ActivityKey(WeatherActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity>
    bindMainActivityInjectorFactory(WeatherActivitySubcomponent.Builder builder);*/

    @ActivityScope
    @ContributesAndroidInjector(modules = WeatherModule.class)
    abstract WeatherActivity contributeWeatherActivity();

}
