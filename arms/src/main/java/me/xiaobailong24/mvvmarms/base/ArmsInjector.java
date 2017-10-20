package me.xiaobailong24.mvvmarms.base;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import java.util.List;

import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;
import me.xiaobailong24.mvvmarms.di.component.DaggerArmsComponent;
import me.xiaobailong24.mvvmarms.di.module.ArmsConfigModule;
import me.xiaobailong24.mvvmarms.di.module.ArmsModule;
import me.xiaobailong24.mvvmarms.http.imageloader.glide.ImageConfigImpl;
import me.xiaobailong24.mvvmarms.lifecycle.utils.Preconditions;
import me.xiaobailong24.mvvmarms.utils.ArmsUtils;
import me.xiaobailong24.mvvmarms.utils.ManifestArmsParser;

/**
 * @author xiaobailong24
 * @date 2017/9/30
 * ArmsInjector，需要在 Application 初始化，注入 ArmsComponent
 */
public class ArmsInjector implements IArms {
    private Application mApplication;
    private ArmsComponent mArmsComponent;
    private ArmsModule mArmsModule;
    private List<ConfigArms> mConfigArmses;
    private ComponentCallbacks2 mComponentCallback;


    public ArmsInjector(Context context) {
        mConfigArmses = new ManifestArmsParser(context).parse();
    }


    public void onCreate(Application application) {
        this.mApplication = application;

        if (mArmsModule == null) {
            mArmsModule = new ArmsModule(mApplication);
        }
        mArmsComponent = DaggerArmsComponent.builder()
                // .lifecycleModule(((ILifecycle) mApplication).getLifecycleModule())
                // .repositoryModule(((IRepository) mApplication).getRepositoryModule())
                .armsConfigModule(getArmsConfigModule(mApplication, mConfigArmses))
                .armsModule(new ArmsModule(mApplication))
                .build();
        mArmsComponent.inject(this);

        mComponentCallback = new AppComponentCallbacks(mApplication);
        mApplication.registerComponentCallbacks(mComponentCallback);
    }


    public void onTerminate(Application application) {
        if (mComponentCallback != null) {
            mApplication.unregisterComponentCallbacks(mComponentCallback);
        }
        this.mComponentCallback = null;
    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明{@link ConfigArms}的实现类,和Glide的配置方式相似
     *
     * @return ArmsConfigModule
     */
    private ArmsConfigModule getArmsConfigModule(Context context, List<ConfigArms> configArmses) {
        ArmsConfigModule.Builder builder = ArmsConfigModule.builder();
        // 注册 Arms 自定义配置
        for (ConfigArms module : configArmses) {
            module.applyOptions(context, builder);
        }
        return builder.build();
    }

    @Override
    public ArmsComponent getArmsComponent() {
        Preconditions.checkNotNull(mArmsComponent,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                ArmsComponent.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mArmsComponent;
    }

    @Override
    public ArmsModule getArmsModule() {
        Preconditions.checkNotNull(mArmsModule,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                ArmsModule.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mArmsModule;
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
