package me.xiaobailong24.mvvmarms.utils;

import android.app.Application;
import android.content.Context;

import me.xiaobailong24.mvvmarms.base.IArms;
import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;
import me.xiaobailong24.mvvmarms.repository.utils.Preconditions;

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
        return obtainArmsComponent((Application) context.getApplicationContext());
    }

    public ArmsComponent obtainArmsComponent(Application application) {
        Preconditions.checkState(application instanceof IArms, "Application does not implements IArms");
        return ((IArms) application).getArmsComponent();
    }
}
