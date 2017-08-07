package me.xiaobailong24.mvvmarms.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.xiaobailong24.mvvmarms.base.delegate.IFragment;
import me.xiaobailong24.mvvmarms.mvvm.IViewModel;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * MVVM ArmsFragment
 */
public abstract class ArmsFragment<DB extends ViewDataBinding, VM extends IViewModel>
        extends Fragment implements IFragment, LifecycleRegistryOwner {
    protected final String TAG = this.getClass().getName();

    //LifecycleOwner
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    //DataBinding
    protected DB mBinding;

    //MVVM ViewModel
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    protected VM mViewModel;//instance in subclass; 自动销毁


    public ArmsFragment() {
        setArguments(new Bundle());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        if (mViewModel != null)
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
        return view;
    }


    @Override
    public boolean injectable() {
        return true;//默认进行依赖注入
    }


    public boolean useEventBus() {
        return true;
    }


    @Override
    public LifecycleRegistry getLifecycle() {
        return this.mLifecycleRegistry;//LifecycleOwner
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        this.mViewModelFactory = null;
        if (mLifecycleRegistry != null && mViewModel != null)//移除LifecycleObserver
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        this.mLifecycleRegistry = null;
        this.mViewModel = null;
    }
}
