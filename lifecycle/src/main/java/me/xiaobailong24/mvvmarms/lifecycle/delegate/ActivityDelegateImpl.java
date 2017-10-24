package me.xiaobailong24.mvvmarms.lifecycle.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Activity 生命周期代理接口实现类
 */
public class ActivityDelegateImpl implements ActivityDelegate, HasSupportFragmentInjector {
    private Activity mActivity;
    private IActivity iActivity;
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    public ActivityDelegateImpl(Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //如果要使用eventbus请将此方法返回true
        if (iActivity.useEventBus()) {
            //注册到事件主线
            EventBus.getDefault().register(mActivity);
        }
        //Dagger.Android 依赖注入
        if (iActivity.injectable()) {
            AndroidInjection.inject(mActivity);
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroy() {
        //如果要使用eventbus请将此方法返回true
        if (iActivity.useEventBus()) {
            EventBus.getDefault().unregister(mActivity);
        }
        this.iActivity = null;
        this.mActivity = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected ActivityDelegateImpl(Parcel in) {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
        this.iActivity = in.readParcelable(IActivity.class.getClassLoader());
    }

    public static final Parcelable.Creator<ActivityDelegateImpl> CREATOR = new Parcelable.Creator<ActivityDelegateImpl>() {
        @Override
        public ActivityDelegateImpl createFromParcel(Parcel source) {
            return new ActivityDelegateImpl(source);
        }

        @Override
        public ActivityDelegateImpl[] newArray(int size) {
            return new ActivityDelegateImpl[size];
        }
    };

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.mFragmentInjector;
    }
}
