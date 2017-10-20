package me.xiaobailong24.mvvmarms.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import me.xiaobailong24.mvvmarms.base.ArmsInjector;
import me.xiaobailong24.mvvmarms.di.module.ArmsConfigModule;
import me.xiaobailong24.mvvmarms.di.module.ArmsModule;
import me.xiaobailong24.mvvmarms.di.module.ViewModelFactoryModule;
import me.xiaobailong24.mvvmarms.http.imageloader.ImageLoader;
import me.xiaobailong24.mvvmarms.lifecycle.di.module.LifecycleModule;
import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryModule;

/**
 * @author xiaobailong24
 * @date 2017/7/13
 * Dagger ArmsComponent 向外提供一些方法获取需要的对象，
 * 通过 {@link me.xiaobailong24.mvvmarms.utils.ArmsUtils} 获取
 */
@Singleton
@Component(modules = {ViewModelFactoryModule.class,
        RepositoryModule.class, LifecycleModule.class,
        ArmsModule.class, ArmsConfigModule.class})
public interface ArmsComponent {
    /**
     * 获取 Application
     *
     * @return Application
     */
    Application application();


    /**
     * 图片加载管理器，策略模式，默认使用 Glide
     *
     * @return ImageLoader
     */
    ImageLoader imageLoader();

    /**
     * Dagger 注入
     *
     * @param armsInjector ArmsInjector
     */
    void inject(ArmsInjector armsInjector);
}
