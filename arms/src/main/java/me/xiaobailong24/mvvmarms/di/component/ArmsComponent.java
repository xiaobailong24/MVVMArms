package me.xiaobailong24.mvvmarms.di.component;

import android.app.Application;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.xiaobailong24.mvvmarms.base.delegate.AppDelegate;
import me.xiaobailong24.mvvmarms.di.module.ArmsModule;
import me.xiaobailong24.mvvmarms.di.module.ClientModule;
import me.xiaobailong24.mvvmarms.di.module.GlobalConfigModule;
import me.xiaobailong24.mvvmarms.di.module.ViewModelFactoryModule;
import me.xiaobailong24.mvvmarms.repository.IRepositoryManager;
import okhttp3.OkHttpClient;

/**
 * Created by xiaobailong24 on 2017/7/13.
 * Dagger ArmsComponent
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, ViewModelFactoryModule.class,
        ArmsModule.class, ClientModule.class, GlobalConfigModule.class})
public interface ArmsComponent {
    Application application();

    IRepositoryManager repositoryManager();

    //Rxjava错误处理管理类
    RxErrorHandler rxErrorHandler();

    OkHttpClient okHttpClient();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Map<String, Object> extras();

    void inject(AppDelegate appDelegate);
}
