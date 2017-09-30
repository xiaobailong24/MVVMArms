package me.xiaobailong24.mvvmarms.di.module;

import android.app.Application;
import android.support.v4.util.ArrayMap;

import java.util.Map;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.di.scope.ArmsScope;

/**
 * Created by xiaobailong24 on 2017/7/13.
 * Dagger ArmsModule
 */
@Module
public class ArmsModule {
    private Application mApplication;

    public ArmsModule(Application application) {
        this.mApplication = application;
    }


    @ArmsScope
    @Provides
    Map<String, Object> provideExtras() {
        return new ArrayMap<>();
    }

}
