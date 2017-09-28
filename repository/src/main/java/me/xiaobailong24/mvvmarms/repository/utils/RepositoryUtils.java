package me.xiaobailong24.mvvmarms.repository.utils;

import android.app.Application;
import android.content.Context;

import me.xiaobailong24.mvvmarms.repository.IRepository;
import me.xiaobailong24.mvvmarms.repository.di.component.RepositoryComponent;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * RepositoryComponent 工具类
 */
public enum RepositoryUtils {
    INSTANCE;

    public RepositoryComponent obtainRepositoryComponent(Context context) {
        Preconditions.checkState(context.getApplicationContext() instanceof IRepository,
                "%s does not implements IRepository", context.getApplicationContext());
        return ((IRepository) context.getApplicationContext()).getRepositoryComponent();
    }

    public RepositoryComponent obtainRepositoryComponent(Application application) {
        Preconditions.checkState(application instanceof IRepository,
                "%s does not implements IRepository", application.getClass().getName());
        return ((IRepository) application).getRepositoryComponent();
    }

}
