package me.xiaobailong24.mvvmarms.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import me.xiaobailong24.mvvmarms.lifecycle.delegate.IActivity;
import me.xiaobailong24.mvvmarms.mvvm.IViewModel;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * MVVM BaseActivity
 */
public abstract class BaseActivity<DB extends ViewDataBinding, VM extends IViewModel>
        extends AppCompatActivity implements IActivity {
    protected final String TAG = this.getClass().getName();

    /**
     * ViewDataBinding
     */
    protected DB mBinding;

    /**
     * MVVM ViewModel ViewModelProvider.Factory
     */
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    /**
     * instance in subclass; 自动销毁
     */
    protected VM mViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置DataBinding
        mBinding = DataBindingUtil.setContentView(this, initView(savedInstanceState));
        initData(savedInstanceState);
        if (mViewModel != null) {
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
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

    @SuppressWarnings("all")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /**
         * 新姿势：通过ViewModel保存数据。
         *  @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html#viewmodel_vs_savedinstancestate">ViewModel vs SavedInstanceState</a>
         */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        this.mViewModelFactory = null;
        //移除LifecycleObserver
        if (mViewModel != null) {
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        }
        this.mViewModel = null;
    }
}