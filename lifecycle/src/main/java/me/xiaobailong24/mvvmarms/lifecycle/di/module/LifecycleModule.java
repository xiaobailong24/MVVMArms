package me.xiaobailong24.mvvmarms.lifecycle.di.module;

import android.app.Application;
import android.support.v4.util.ArrayMap;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiaobailong24 on 2017/9/30.
 * Dagger LifecycleModule
 */
@Module
public class LifecycleModule {
    private Application mApplication;

    public LifecycleModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return this.mApplication;
    }

    @Singleton
    @Provides
    Map<String, Object> provideExtras() {
        return new ArrayMap<>();
    }
}
