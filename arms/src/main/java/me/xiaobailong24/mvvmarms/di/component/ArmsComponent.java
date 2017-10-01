package me.xiaobailong24.mvvmarms.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import me.xiaobailong24.mvvmarms.base.ArmsInjector;
import me.xiaobailong24.mvvmarms.di.module.ArmsConfigModule;
import me.xiaobailong24.mvvmarms.di.module.ArmsModule;
import me.xiaobailong24.mvvmarms.di.module.ViewModelFactoryModule;
import me.xiaobailong24.mvvmarms.http.imageloader.ImageLoader;
import me.xiaobailong24.mvvmarms.lifecycle.di.module.LifecycleModule;
import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryModule;

/**
 * Created by xiaobailong24 on 2017/7/13.
 * Dagger ArmsComponent
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, ViewModelFactoryModule.class,
        RepositoryModule.class, LifecycleModule.class,
        ArmsModule.class, ArmsConfigModule.class})
public interface ArmsComponent {
    Application application();

    //图片加载管理器，策略模式，默认使用 Glide
    ImageLoader imageLoader();

    void inject(ArmsInjector armsInjector);
}
