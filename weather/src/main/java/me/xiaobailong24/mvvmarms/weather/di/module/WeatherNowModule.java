package me.xiaobailong24.mvvmarms.weather.di.module;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.mvvm.IModel;
import me.xiaobailong24.mvvmarms.weather.R;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherNowModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter.TextContentAdapter;

/**
 * @author xiaobailong24
 * @date 2017/7/15
 * Dagger WeatherNowModule
 */
@Module
public class WeatherNowModule {

    @FragmentScope
    @Provides
    IModel provideWeatherNowModel(WeatherNowModel weatherNowModel) {
        return weatherNowModel;
    }

    @FragmentScope
    @Provides
    TextContentAdapter provideAdapter() {
        return new TextContentAdapter(R.layout.super_text_item, null);
    }

}
