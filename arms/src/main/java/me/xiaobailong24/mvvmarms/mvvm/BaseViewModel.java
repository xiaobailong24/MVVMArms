package me.xiaobailong24.mvvmarms.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by xiaobailong24 on 2017/6/16.
 * MVVM BaseViewModel (ViewModel 不再持有 View，而是 store and manage UI-related data)
 * 生命周期：@{link https://developer.android.com/topic/libraries/architecture/viewmodel.html}
 * ViewModel objects are scoped to the Lifecycle passed to the ViewModelProvider when getting the ViewModel.
 * The ViewModel stays in memory until the Lifecycle it’s scoped to goes away permanently
 * —in the case of an activity, when it finishes;
 * in the case of a fragment, when it’s detached.
 */
public class BaseViewModel<M extends IModel> extends AndroidViewModel
        implements IViewModel, LifecycleObserver {
    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;

    public BaseViewModel(Application application) {
        super(application);
    }

    public BaseViewModel(Application application, M model) {
        super(application);
        this.mModel = model;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onStart() {
        if (useEventBus())
            EventBus.getDefault().register(this);//注册eventbus
    }

    //是否使用EventBus
    protected boolean useEventBus() {
        return true;
    }

    //RxJava添加订阅
    protected void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有disposable放入,集中处理
    }

    //RxJava自动解除订阅
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void unDispose() {
        if (mCompositeDisposable != null) {
            //保证LifecycleOwner结束时取消所有正在执行的订阅
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (useEventBus())
            EventBus.getDefault().unregister(this);//解除注册eventbus
    }

}
