package me.xiaobailong24.mvvmarms.di.module;

import android.app.Application;
import android.support.v4.util.ArrayMap;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.http.imageloader.BaseImageLoaderStrategy;
import me.xiaobailong24.mvvmarms.http.imageloader.glide.GlideImageLoaderStrategy;
import me.xiaobailong24.mvvmarms.repository.IRepositoryManager;
import me.xiaobailong24.mvvmarms.repository.utils.RepositoryUtils;

/**
 * Created by xiaobailong24 on 2017/7/13.
 */
@Module
public class ArmsModule {
    private Application mApplication;

    public ArmsModule(Application application) {
        this.mApplication = application;
    }

//    @Singleton
//    @Provides
//    public Application provideApplication() {
//        return this.mApplication;
//    }


    @Singleton
    @Provides
    BaseImageLoaderStrategy provideImageLoaderStrategy() {
        //默认使用 Glide 加载图片
        // TODO: 2017/9/28
        return new GlideImageLoaderStrategy();
    }

    @Singleton
    @Provides
    public IRepositoryManager provideRepositoryManager() {
        return RepositoryUtils.INSTANCE.obtainRepositoryComponent(mApplication).repositoryManager();
    }

    @Singleton
    @Provides
    public Map<String, Object> provideExtras() {
        return new ArrayMap<>();
    }

}
