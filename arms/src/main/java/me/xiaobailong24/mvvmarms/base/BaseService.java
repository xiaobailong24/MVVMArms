package me.xiaobailong24.mvvmarms.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author xiaobailong24
 * @date 2017/8/22
 * MVVMArms BaseService
 */
public abstract class BaseService extends Service {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unDisposeAll();
        this.mCompositeDisposable = null;
    }


    /**
     * 添加 RxJava 订阅
     *
     * @param disposable Disposable
     */
    protected void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 当 Service 销毁时自动解除所有订阅，也可以手动执行
     */
    protected void unDisposeAll() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    /**
     * Service 初始化
     */
    protected abstract void init();
}
