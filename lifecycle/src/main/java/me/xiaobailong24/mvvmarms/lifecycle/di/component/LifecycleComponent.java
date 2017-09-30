package me.xiaobailong24.mvvmarms.lifecycle.di.component;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import me.xiaobailong24.mvvmarms.lifecycle.LifecycleInjector;
import me.xiaobailong24.mvvmarms.lifecycle.delegate.AppManager;
import me.xiaobailong24.mvvmarms.lifecycle.di.module.LifecycleModule;

/**
 * Created by xiaobailong24 on 2017/9/30.
 * Dagger LifecycleComponent
 */
@Singleton
@Component(modules = LifecycleModule.class)
public interface LifecycleComponent {

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Map<String, Object> extras();

    //用于管理所有activity
    AppManager appManager();

    void inject(LifecycleInjector lifecycleInjector);
}
