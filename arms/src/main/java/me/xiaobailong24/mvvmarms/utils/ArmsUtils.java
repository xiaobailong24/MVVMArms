package me.xiaobailong24.mvvmarms.utils;

import android.app.Application;
import android.content.Context;

import me.xiaobailong24.mvvmarms.base.delegate.App;
import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;

/**
 * Created by xiaobailong24 on 2017/8/23.
 * MVVMArms Utils
 * https://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
 * 获取 ArmsComponent 来拿到框架里的一切
 * {@link ArmsComponent}
 */

public enum ArmsUtils {
    INSTANCE;

    public ArmsComponent obtainArmsComponent(Context context) {
        Preconditions.checkState(context.getApplicationContext() instanceof App, "Application does not implements App");
        return ((App) context.getApplicationContext()).getArmsComponent();
    }

    public ArmsComponent obtainArmsComponent(Application application) {
        Preconditions.checkState(application instanceof App, "Application does not implements App");
        return ((App) application).getArmsComponent();
    }
}
