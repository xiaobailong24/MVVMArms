package me.xiaobailong24.mvvmarms.base.delegate;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;
import me.xiaobailong24.mvvmarms.di.component.DaggerArmsComponent;
import me.xiaobailong24.mvvmarms.di.module.ArmsModule;
import me.xiaobailong24.mvvmarms.http.imageloader.glide.ImageConfigImpl;
import me.xiaobailong24.mvvmarms.base.ConfigLifecycle;
import me.xiaobailong24.mvvmarms.repository.IRepository;
import me.xiaobailong24.mvvmarms.repository.RepositoryInjector;
import me.xiaobailong24.mvvmarms.repository.di.component.RepositoryComponent;
import me.xiaobailong24.mvvmarms.utils.ArmsUtils;
import me.xiaobailong24.mvvmarms.utils.ManifestParser;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * Activity 生命周期代理接口实现类
 */
public class AppDelegate implements App, AppLifecycles, IRepository {
    private Application mApplication;
    private ArmsComponent mArmsComponent;
    @Inject
    protected ActivityLifecycle mActivityLifecycle;
    private List<ConfigLifecycle> mLifecycles;
    private List<AppLifecycles> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();
    private ComponentCallbacks2 mComponentCallback;

    private RepositoryInjector mRepositoryInjector;//Repository

    public AppDelegate(Context context) {
        this.mLifecycles = new ManifestParser(context).parse();
        for (ConfigLifecycle lifecycle : mLifecycles) {
            lifecycle.injectAppLifecycle(context, mAppLifecycles);
            lifecycle.injectActivityLifecycle(context, mActivityLifecycles);
        }
    }

    @Override
    public void attachBaseContext(Context base) {
        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;

        //Repository inject
        this.mRepositoryInjector = new RepositoryInjector(mApplication);//Repository
        mRepositoryInjector.initialize(mApplication);

        mArmsComponent = DaggerArmsComponent
                .builder()
                .repositoryModule(mRepositoryInjector.getRepositoryModule())
                .armsModule(new ArmsModule(mApplication))//提供application
                .build();
        mArmsComponent.inject(this);

        mArmsComponent.extras().put(ConfigLifecycle.class.getName(), mLifecycles);

        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        this.mLifecycles = null;

        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }

        mComponentCallback = new AppComponentCallbacks(mApplication);

        mApplication.registerComponentCallbacks(mComponentCallback);

        for (AppLifecycles lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }
    }

    @Override
    public void onTerminate(Application application) {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mComponentCallback != null) {
            mApplication.unregisterComponentCallbacks(mComponentCallback);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (AppLifecycles lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mRepositoryInjector = null;
        this.mArmsComponent = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycles = null;
        this.mComponentCallback = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }


    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    @Override
    public ArmsComponent getArmsComponent() {
        return this.mArmsComponent;
    }

    @Override
    public RepositoryComponent getRepositoryComponent() {
        return mRepositoryInjector.getRepositoryComponent();
    }


    private static class AppComponentCallbacks implements ComponentCallbacks2 {
        private Application mApplication;

        public AppComponentCallbacks(Application application) {
            this.mApplication = application;
        }

        @Override
        public void onTrimMemory(int level) {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onLowMemory() {
            //内存不足时清理图片请求框架的内存缓存
            ArmsUtils.INSTANCE.obtainArmsComponent(mApplication)
                    .imageLoader()
                    .clear(mApplication, ImageConfigImpl.builder().isClearMemory(true).build());
        }
    }
}
