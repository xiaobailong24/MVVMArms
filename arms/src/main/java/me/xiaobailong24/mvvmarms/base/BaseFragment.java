package me.xiaobailong24.mvvmarms.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.xiaobailong24.mvvmarms.base.delegate.IFragment;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * MVVM BaseFragment
 */
public abstract class BaseFragment extends Fragment
        implements IFragment {
    protected final String TAG = this.getClass().getName();
    protected boolean mVisible = false;//是否可见
    protected boolean mFirst = true;//是否第一次加载
    private View mRootView;

    public BaseFragment() {
        setArguments(new Bundle());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container, savedInstanceState);
        if (mVisible && mFirst)//可见，并且是首次加载
            onFragmentVisibleChange(true);
        return mRootView;
    }

    public boolean useEventBus() {
        return true;
    }

    @Override
    public boolean injectable() {
        return true;//默认进行依赖注入
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisible = isVisibleToUser;
        if (mRootView == null)
            return;
        //可见，并且首次加载时才调用
        onFragmentVisibleChange(mVisible & mFirst);
    }

    /**
     * 当前 Fragment 可见状态发生变化时会回调该方法。
     * 如果当前 Fragment 是第一次加载，等待 onCreateView 后才会回调该方法，
     * 其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mRootView = null;
    }
}
