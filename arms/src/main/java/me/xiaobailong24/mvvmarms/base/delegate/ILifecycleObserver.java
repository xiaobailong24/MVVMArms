package me.xiaobailong24.mvvmarms.base.delegate;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Parcelable;

/**
 * Created by xiaobailong24 on 2017/8/7.
 * Activity/Fragment 生命周期的监听接口
 */

public interface ILifecycleObserver extends LifecycleObserver, Parcelable {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onAny(LifecycleOwner owner, Lifecycle.Event event);
}
