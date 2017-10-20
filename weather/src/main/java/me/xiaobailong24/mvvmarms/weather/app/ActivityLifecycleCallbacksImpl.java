package me.xiaobailong24.mvvmarms.weather.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author xiaobailong24
 * @date 2017/9/3
 * ActivityLifecycleCallbacksImpl
 * 只需重写需要的方法
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        //do something
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
