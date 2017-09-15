package me.xiaobailong24.mvvmarms.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.xiaobailong24.mvvmarms.base.delegate.IActivity;
import me.xiaobailong24.mvvmarms.mvvm.IViewModel;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * MVVM ArmsActivity
 */
public abstract class ArmsActivity<DB extends ViewDataBinding, VM extends IViewModel>
        extends AppCompatActivity implements IActivity, HasSupportFragmentInjector {
    protected final String TAG = this.getClass().getName();

    //Dagger.Android SupportFragment Inject
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    //DataBinding
    protected DB mBinding;

    //MVVM ViewModel
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    protected VM mViewModel;//instance in subclass; 自动销毁


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置DataBinding
        mBinding = DataBindingUtil.setContentView(this, initView(savedInstanceState));
        initData(savedInstanceState);
        if (mViewModel != null)
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
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
        return this.mFragmentInjector;//Dagger.Android SupportFragment Inject
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        this.mViewModelFactory = null;
        this.mFragmentInjector = null;
        if (getLifecycle() != null && mViewModel != null)//移除LifecycleObserver
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        this.mViewModel = null;
    }
}