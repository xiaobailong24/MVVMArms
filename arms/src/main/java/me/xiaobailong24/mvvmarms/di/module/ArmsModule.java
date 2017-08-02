package me.xiaobailong24.mvvmarms.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.repository.IRepositoryManager;
import me.xiaobailong24.mvvmarms.repository.RepositoryManager;

/**
 * Created by xiaobailong24 on 2017/7/13.
 */
@Module
public class ArmsModule {
    private Application mApplication;

    public ArmsModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return this.mApplication;
    }

    @Singleton
    @Provides
    public Gson provideGson(Application application, @Nullable GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null)
            configuration.configGson(application, builder);
        return builder.create();
    }


    @Singleton
    @Provides
    public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
        return repositoryManager;
    }

    @Singleton
    @Provides
    public Map<String, Object> provideExtras() {
        return new ArrayMap<>();
    }


    public interface GsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }

}
