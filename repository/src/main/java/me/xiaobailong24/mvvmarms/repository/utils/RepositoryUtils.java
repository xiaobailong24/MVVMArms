package me.xiaobailong24.mvvmarms.repository.utils;

import android.app.Application;
import android.content.Context;

import me.xiaobailong24.mvvmarms.repository.IRepository;
import me.xiaobailong24.mvvmarms.repository.di.component.RepositoryComponent;

/**
 * @author xiaobailong24
 * @date 2017/9/28
 * RepositoryComponent 工具类
 */
public enum RepositoryUtils {
    /**
     * 单例模式的枚举实现
     */
    INSTANCE;

    /**
     * 获取 {@link RepositoryComponent}，使用 Dagger 对外暴露的方法
     *
     * @param context Context
     * @return LifecycleComponent
     */
    public RepositoryComponent obtainRepositoryComponent(Context context) {
        return obtainRepositoryComponent((Application) context.getApplicationContext());
    }

    /**
     * 获取 {@link RepositoryComponent}，使用 Dagger 对外暴露的方法
     *
     * @param application Application
     * @return LifecycleComponent
     */
    public RepositoryComponent obtainRepositoryComponent(Application application) {
        Preconditions.checkState(application instanceof IRepository,
                "%s does not implements IRepository", application.getClass().getName());
        return ((IRepository) application).getRepositoryComponent();
    }

}
