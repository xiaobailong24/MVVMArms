package me.xiaobailong24.mvvmarms.weather.mvvm.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.xiaobailong24.mvvmarms.di.scope.AppScope;
import me.xiaobailong24.mvvmarms.mvvm.BaseViewModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherNowResponse;
import timber.log.Timber;

/**
 * Created by xiaobailong24 on 2017/7/31.
 * MVVM WeatherViewModel
 */
@AppScope
public class WeatherViewModel extends BaseViewModel<WeatherModel> {
    private RxErrorHandler mRxErrorHandler;
    private MutableLiveData<List<String>> mLocationPaths;

    @Inject
    public WeatherViewModel(Application application, WeatherModel model,
                            RxErrorHandler rxErrorHandler) {
        super(application, model);
        this.mRxErrorHandler = rxErrorHandler;
    }

    //获取储存的位置记录
    public LiveData<List<String>> getHistoryLocations() {
        if (mLocationPaths == null)
            mLocationPaths = new MutableLiveData<>();
        loadLocationPaths();
        return mLocationPaths;
    }

    private void loadLocationPaths() {
        Observable.create((ObservableOnSubscribe<List<WeatherNowResponse.NowResult.Location>>) e -> e.onNext(mModel.getAllLocations()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(locations -> {
                    List<String> locationPaths = new ArrayList<>();
                    for (WeatherNowResponse.NowResult.Location location : locations) {
                        Timber.d("loadLocationPaths: " + location.getPath());
                        locationPaths.add(location.getPath());
                    }
                    return locationPaths;
                })
                .subscribe(new ErrorHandleSubscriber<List<String>>(mRxErrorHandler) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        addDispose(d);
                    }

                    @Override
                    public void onNext(@NonNull List<String> locationPaths) {
                        mLocationPaths.setValue(locationPaths);
                    }
                });
    }

    @Override
    public void onCleared() {
        super.onCleared();
        this.mLocationPaths = null;
    }

}
