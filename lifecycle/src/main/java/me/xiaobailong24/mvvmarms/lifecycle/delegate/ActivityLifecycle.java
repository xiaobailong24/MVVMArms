package me.xiaobailong24.mvvmarms.lifecycle.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.xiaobailong24.mvvmarms.lifecycle.ConfigLifecycle;
import timber.log.Timber;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Activity 生命周期回调。
 * 在 Activity 对应生命周期的 super() 方法中进行的。
 */
@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    private AppManager mAppManager;
    private Application mApplication;
    private Map<String, Object> mExtras;
    private FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycle;
    private List<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycles;

    @Inject
    public ActivityLifecycle(AppManager appManager, Application application, Map<String, Object> extras) {
        this.mAppManager = appManager;
        this.mApplication = application;
        this.mExtras = extras;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.w(activity + " ---> onActivityCreated");

        //如果 intent 包含了此字段,并且为 true 说明不加入到 list 进行统一管理
        boolean isNotAdd = false;
        if (activity.getIntent() != null) {
            isNotAdd = activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false);
        }

        if (!isNotAdd) {
            mAppManager.addActivity(activity);
        }

        //配置ActivityDelegate
        if (activity instanceof IActivity && activity.getIntent() != null) {
            ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
            if (activityDelegate == null) {
                activityDelegate = new ActivityDelegateImpl(activity);
                activity.getIntent().putExtra(ActivityDelegate.ACTIVITY_DELEGATE, activityDelegate);
            }
            activityDelegate.onCreate(savedInstanceState);
        }

        // 给每个Activity配置Fragment的监听,Activity可以通过 {@link IActivity#useFragment()} 设置是否使用监听
        registerFragmentCallbacks(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.w(activity + " ---> onActivityStarted");
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.w(activity + " ---> onActivityResumed");
        mAppManager.setCurrentActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.w(activity + " ---> onActivityPaused");
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.w(activity + " ---> onActivityStopped");
        if (mAppManager.getCurrentActivity() == activity) {
            mAppManager.setCurrentActivity(null);
        }

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.w(activity + " ---> onActivitySaveInstanceState");
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.w(activity + " ---> onActivityDestroyed");
        mAppManager.removeActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
            activity.getIntent().removeExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
    }


    /**
     * 给每个 Activity 的所有 Fragment 设置监听其生命周期, Activity 可以通过 {@link IActivity#useFragment()}
     * 设置是否使用监听,如果这个 Activity 返回 false 的话,这个 Activity 下面的所有 Fragment 将不能使用 {@link FragmentDelegate}
     *
     * @param activity: Activity
     */
    private void registerFragmentCallbacks(Activity activity) {
        boolean useFragment = !(activity instanceof IActivity) || ((IActivity) activity).useFragment();
        if (activity instanceof FragmentActivity && useFragment) {

            if (mFragmentLifecycle == null) {
                mFragmentLifecycle = new FragmentLifecycle();
            }

            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle, true);

            if (mFragmentLifecycles == null && mExtras.containsKey(ConfigLifecycle.class.getName())) {
                mFragmentLifecycles = new ArrayList<>();
                List<ConfigLifecycle> lifecycles = (List<ConfigLifecycle>) mExtras.get(ConfigLifecycle.class.getName());
                for (ConfigLifecycle lifecycle : lifecycles) {
                    lifecycle.injectFragmentLifecycle(mApplication, mFragmentLifecycles);
                }
                mExtras.remove(ConfigLifecycle.class.getName());
            }

            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles) {
                ((FragmentActivity) activity).getSupportFragmentManager()
                        .registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }
    }

    private ActivityDelegate fetchActivityDelegate(Activity activity) {
        ActivityDelegate activityDelegate = null;
        if (activity instanceof IActivity && activity.getIntent() != null) {
            activity.getIntent().setExtrasClassLoader(getClass().getClassLoader());
            activityDelegate = activity.getIntent().getParcelableExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
        return activityDelegate;
    }

}
