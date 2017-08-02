package me.xiaobailong24.mvvmarms.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.xiaobailong24.mvvmarms.base.delegate.IActivity;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * MVVM BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity
        implements IActivity, LifecycleRegistryOwner, HasSupportFragmentInjector {
    protected final String TAG = this.getClass().getName();

    //LifecycleRegistryOwner
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(initView(savedInstanceState));
            initData(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }


    @Override
    public boolean useFragment() {
        return true;
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.mFragmentInjector;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mFragmentInjector = null;
        this.mLifecycleRegistry = null;
    }
}
