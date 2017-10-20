package me.xiaobailong24.mvvmarms.weather.di.module;

import android.app.Activity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import me.xiaobailong24.mvvmarms.weather.di.component.WeatherActivitySubcomponent;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.activity.WeatherActivity;

/**
 * @author xiaobailong24
 * @date 2017/7/13
 * Dagger WeatherActivityModule
 * 用于 Dagger.Android 依赖注入
 * 第一种注入方式。需要 Subcomponent
 */
@Module(subcomponents = WeatherActivitySubcomponent.class)
public abstract class WeatherActivityModule {
    /**
     * 第一种注入方式。需要 Subcomponent
     * <p>
     * 第二种{@link WeatherNowFragmentModule}
     *
     * @see <a href="https://github.com/xiaobailong24/DaggerAndroid">DaggerAndroid</a>
     */
    @Binds
    @IntoMap
    @ActivityKey(WeatherActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity>
    bindWeatherActivityInjectorFactory(WeatherActivitySubcomponent.Builder builder);

    /*   //第二种注入方式
        @ActivityScope
        @ContributesAndroidInjector(modules = WeatherModule.class)
        abstract WeatherActivity contributeWeatherActivity();*/

}
