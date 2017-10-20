package me.xiaobailong24.mvvmarms.repository;

import me.xiaobailong24.mvvmarms.repository.di.component.RepositoryComponent;
import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryModule;

/**
 * @author xiaobailong24
 * @date 2017/9/28
 * Application 继承该接口，就可以具有 RepositoryComponent 提供的方法。
 */
public interface IRepository {
    /**
     * 获得全局 RepositoryComponent
     *
     * @return RepositoryComponent
     */
    RepositoryComponent getRepositoryComponent();

    /**
     * 获得全局 RepositoryModule 重用
     *
     * @return RepositoryModule
     */
    RepositoryModule getRepositoryModule();

}
