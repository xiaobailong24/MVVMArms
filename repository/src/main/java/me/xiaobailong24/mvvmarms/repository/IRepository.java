package me.xiaobailong24.mvvmarms.repository;

import me.xiaobailong24.mvvmarms.repository.di.component.RepositoryComponent;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * Application 继承该接口，就可以具有 RepositoryComponent 提供的方法。
 */
public interface IRepository {
    /**
     * Description: 获得全局 RepositoryComponent
     *
     * @return RepositoryComponent
     */
    RepositoryComponent getRepositoryComponent();
}
