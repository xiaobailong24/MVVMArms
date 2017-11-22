package me.xiaobailong24.mvvmarms.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import org.simple.eventbus.EventBus;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * MVVM BaseViewModel (ViewModel 不再持有 View，而是 store and manage UI-related data)
 * ViewModel objects are scoped to the Lifecycle passed to the ViewModelProvider when getting the ViewModel.
 * The ViewModel stays in memory until the Lifecycle it’s scoped to goes away permanently
 * —in the case of an activity, when it finishes;
 * in the case of a fragment, when it’s detached.
 * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html">ViewModel</a>
 */
public class BaseViewModel<M extends IModel> extends AndroidViewModel
        implements IViewModel, LifecycleObserver {

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
        if (useEventBus()) {
            //注册eventbus
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 是否使用 EventBus
     *
     * @return True if use
     */
    protected boolean useEventBus() {
        return true;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (useEventBus()) {
            //解除注册eventbus
            EventBus.getDefault().unregister(this);
        }
        // TODO: 2017/8/2
    }

}
