package me.xiaobailong24.mvvmarms.base.delegate;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Parcel;
import android.os.Parcelable;

import timber.log.Timber;

/**
 * Created by xiaobailong24 on 2017/8/7.
 * 监听所有 Activity 的生命周期
 * // TODO: 2017/8/7
 */

public class ActivityLifecycleObserver
        implements ILifecycleObserver {
    public static final String ACTIVITY_OBSERVER = "ActivityObserver";

    private Activity mActivity;
    private IActivity iActivity;

    public ActivityLifecycleObserver(Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    @Override
    public void onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onPause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @Override
    public void onStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        this.iActivity = null;
        this.mActivity = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        Timber.tag(this.getClass().getSimpleName()).w(owner.toString() + " ---> " + event.name());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected ActivityLifecycleObserver(Parcel in) {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
        this.iActivity = in.readParcelable(IActivity.class.getClassLoader());
    }

    public static final Parcelable.Creator<ActivityLifecycleObserver> CREATOR = new Parcelable.Creator<ActivityLifecycleObserver>() {
        @Override
        public ActivityLifecycleObserver createFromParcel(Parcel source) {
            return new ActivityLifecycleObserver(source);
        }

        @Override
        public ActivityLifecycleObserver[] newArray(int size) {
            return new ActivityLifecycleObserver[size];
        }
    };
}
