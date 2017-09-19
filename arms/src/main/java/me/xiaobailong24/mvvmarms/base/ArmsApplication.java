package me.xiaobailong24.mvvmarms.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.xiaobailong24.mvvmarms.base.delegate.App;
import me.xiaobailong24.mvvmarms.base.delegate.AppDelegate;
import me.xiaobailong24.mvvmarms.base.delegate.AppLifecycles;
import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;

/**
 * Created by xiaobailong24 on 2017/7/13.
 * MVVMArms ArmsApplication
 */
public class ArmsApplication extends Application
        implements App, HasActivityInjector, HasSupportFragmentInjector {
    //Dagger.Android Activity 注入
    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;
    //Dagger.Android Fragment 注入
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    private AppLifecycles mAppDelegate;

    /**
     * 这里会在 {@link ArmsApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param context
     */
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mAppDelegate = new AppDelegate(context);
        this.mAppDelegate.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppDelegate = new AppDelegate(this);
        mAppDelegate.onCreate(this);
    }

    @Override
    public ArmsComponent getArmsComponent() {
        return ((App) mAppDelegate).getArmsComponent();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return this.mActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.mFragmentInjector;
    }
}
