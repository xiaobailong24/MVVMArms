package me.xiaobailong24.mvvmarms.lifecycle.utils;

import android.app.Application;
import android.content.Context;

import me.xiaobailong24.mvvmarms.lifecycle.delegate.ILifecycle;
import me.xiaobailong24.mvvmarms.lifecycle.di.component.LifecycleComponent;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * LifecycleComponent 工具类
 */
public enum LifecycleUtils {
    INSTANCE;

    public LifecycleComponent obtainLifecycleComponent(Context context) {
        Preconditions.checkState(context.getApplicationContext() instanceof ILifecycle,
                "%s does not implements ILifecycle", context.getApplicationContext());
        return ((ILifecycle) context.getApplicationContext()).getLifecycleComponent();
    }

    public LifecycleComponent obtainLifecycleComponent(Application application) {
        Preconditions.checkState(application instanceof ILifecycle,
                "%s does not implements ILifecycle", application.getClass().getName());
        return ((ILifecycle) application).getLifecycleComponent();
    }

}
