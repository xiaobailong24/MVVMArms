package me.xiaobailong24.mvvmarms.repository.di.component;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.xiaobailong24.mvvmarms.repository.IRepositoryManager;
import me.xiaobailong24.mvvmarms.repository.RepositoryInjector;
import me.xiaobailong24.mvvmarms.repository.cache.Cache;
import me.xiaobailong24.mvvmarms.repository.di.module.ClientModule;
import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryConfigModule;
import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryModule;
import okhttp3.OkHttpClient;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * Dagger RepositoryComponent
 */
@Singleton
@Component(modules = {RepositoryModule.class, ClientModule.class, RepositoryConfigModule.class})
public interface RepositoryComponent {
    //用于管理网络请求层和数据库层
    IRepositoryManager repositoryManager();

    //Rxjava 错误处理管理类
    RxErrorHandler rxErrorHandler();

    //提供 OKHttpClient
    OkHttpClient okHttpClient();

    //提供缓存文件
    File cacheFile();

    //为外部使用提供Cache,切勿大量存放大容量数据
    Cache<String, Object> extras();

    void inject(RepositoryInjector repositoryInjector);
}
