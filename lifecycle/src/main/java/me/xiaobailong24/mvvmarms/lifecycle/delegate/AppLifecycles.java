package me.xiaobailong24.mvvmarms.lifecycle.delegate;

import android.app.Application;
import android.content.Context;

/**
 * @author xiaobailong24
 * @date 2017/7/25
 * 代理 Application 生命周期
 */
public interface AppLifecycles {
    /**
     * 代理 {@link Application#attachBaseContext(Context)}
     *
     * @param context Context
     */
    void attachBaseContext(Context context);

    /**
     * 代理 {@link Application#onCreate()}
     *
     * @param application Application
     */
    void onCreate(Application application);

    /**
     * 代理 {@link Application#onTerminate()}
     *
     * @param application Application
     */
    void onTerminate(Application application);
}
