package me.xiaobailong24.mvvmarms.weather.app;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import me.xiaobailong24.mvvmarms.base.delegate.AppLifecycles;
import me.xiaobailong24.mvvmarms.repository.ConfigModule;
import me.xiaobailong24.mvvmarms.utils.ArmsUtils;

/**
 * Created by xiaobailong24 on 2017/7/24.
 * app的全局配置信息在此配置,需要将此实现类声明到AndroidManifest中
 */
public class GlobalConfiguration implements ConfigModule {

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //用于监听Activity的回调，可以添加一些自定义逻辑
        //        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                //Activity重建时，恢复 Fragment; onCreate() 和 onDestroy() 不会被调用
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                //这里应该是检测 Fragment 而不是 FragmentLifecycleCallbacks 的泄露。
                ((RefWatcher) (ArmsUtils.INSTANCE.obtainArmsComponent(f.getContext()))
                        .extras()
                        .get(RefWatcher.class.getName()))
                        .watch(f);
            }
        });
    }

}
