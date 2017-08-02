package me.xiaobailong24.mvvmarms.weather.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment.WeatherNowFragment;

/**
 * Created by xiaobailong24 on 2017/7/15.
 * Dagger WeatherNowFragmentModule
 * 用于 Dagger.Android 依赖注入
 */
@Module
public abstract class WeatherNowFragmentModule {

/*    @Binds
    @IntoMap
    @FragmentKey(WeatherNowFragment.class)
    public abstract AndroidInjector.Factory<? extends Fragment>
    bindMainFragmentInjectorFactory(WeatherNowFragmentSubcomponent.Builder builder);*/

    @FragmentScope
    @ContributesAndroidInjector(modules = WeatherNowModule.class)
    abstract WeatherNowFragment contributeWeatherNowFragment();
}
