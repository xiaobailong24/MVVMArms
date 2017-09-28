package me.xiaobailong24.mvvmarms.di.component;

import android.app.Application;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import me.xiaobailong24.mvvmarms.base.delegate.AppDelegate;
import me.xiaobailong24.mvvmarms.base.delegate.AppManager;
import me.xiaobailong24.mvvmarms.di.module.ArmsModule;
import me.xiaobailong24.mvvmarms.di.module.ViewModelFactoryModule;
import me.xiaobailong24.mvvmarms.http.imageloader.ImageLoader;
import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryModule;

/**
 * Created by xiaobailong24 on 2017/7/13.
 * Dagger ArmsComponent
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, ViewModelFactoryModule.class, RepositoryModule.class,
        ArmsModule.class})
public interface ArmsComponent {
    Application application();

    //图片加载管理器，策略模式，默认使用 Glide
    ImageLoader imageLoader();

    //用于管理所有activity
    AppManager appManager();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Map<String, Object> extras();

    void inject(AppDelegate appDelegate);
}
