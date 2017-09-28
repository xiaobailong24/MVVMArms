package me.xiaobailong24.mvvmarms.repository;

import android.content.Context;

import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryConfigModule;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * Repository 配置接口
 */
public interface ConfigRepository {
    /**
     * 使用{@link RepositoryConfigModule.Builder}给框架配置一些配置参数
     *
     * @param context: Context
     * @param builder: GlobalConfigModule.Builder
     */
    void applyOptions(Context context, RepositoryConfigModule.Builder builder);

    /**
     * 使用{@link IRepositoryManager}给框架注入一些数据管理服务
     *
     * @param context:           Context
     * @param repositoryManager: IRepositoryManager
     */
    void registerComponents(Context context, IRepositoryManager repositoryManager);
}
