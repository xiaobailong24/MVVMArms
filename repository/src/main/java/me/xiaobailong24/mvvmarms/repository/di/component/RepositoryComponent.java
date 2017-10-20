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
 * @author xiaobailong24
 * @date 2017/9/28
 * Dagger RepositoryComponent 向外提供一些方法获取需要的对象，
 * 通过 {@link me.xiaobailong24.mvvmarms.repository.utils.RepositoryUtils} 获取
 */
@Singleton
@Component(modules = {RepositoryModule.class, ClientModule.class, RepositoryConfigModule.class})
public interface RepositoryComponent {
    /**
     * 用于管理网络请求层和数据库层
     *
     * @return RepositoryManager
     */
    IRepositoryManager repositoryManager();

    /**
     * Rxjava 错误处理管理类
     *
     * @return RxErrorHandler
     */
    RxErrorHandler rxErrorHandler();

    /**
     * 提供 OKHttpClient
     *
     * @return OkHttpClient
     */
    OkHttpClient okHttpClient();

    /**
     * 提供缓存文件
     *
     * @return File
     */
    File cacheFile();

    /**
     * 为外部使用提供 Cache,切勿大量存放大容量数据
     *
     * @return Cache
     */
    Cache<String, Object> extras();

    /**
     * Dagger 注入
     *
     * @param repositoryInjector RepositoryInjector
     */
    void inject(RepositoryInjector repositoryInjector);
}
