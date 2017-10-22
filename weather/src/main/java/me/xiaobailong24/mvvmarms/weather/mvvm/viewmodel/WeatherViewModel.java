package me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel;

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
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.xiaobailong24.mvvmarms.di.scope.ActivityScope;
import me.xiaobailong24.mvvmarms.mvvm.BaseViewModel;
import me.xiaobailong24.mvvmarms.repository.utils.RepositoryUtils;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;
import timber.log.Timber;

/**
 * @author xiaobailong24
 * @date 2017/7/31
 * MVVM WeatherViewModel
 */
@ActivityScope
public class WeatherViewModel extends BaseViewModel<WeatherModel> {
    private MutableLiveData<List<String>> mLocationPaths;
    /**
     * 可以与 Fragment 共享此数据
     */
    private MutableLiveData<String> mLocation;

    @Inject
    public WeatherViewModel(Application application, WeatherModel model) {
        super(application, model);
    }

    /**
     * 获取储存的位置记录
     *
     * @return 历史位置记录列表
     */
    public LiveData<List<String>> getHistoryLocations() {
        if (mLocationPaths == null) {
            mLocationPaths = new MutableLiveData<>();
        }
        loadLocationPaths();
        return mLocationPaths;
    }

    private void loadLocationPaths() {
        Observable.create((ObservableOnSubscribe<List<Location>>) e -> e.onNext(mModel.getAllLocations()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(locations -> {
                    List<String> locationPaths = new ArrayList<>();
                    for (Location location : locations) {
                        Timber.d("loadLocationPaths: " + location.getPath());
                        locationPaths.add(location.getPath());
                    }
                    return locationPaths;
                })
                .subscribe(new ErrorHandleSubscriber<List<String>>
                        (RepositoryUtils.INSTANCE.obtainRepositoryComponent(getApplication()).rxErrorHandler()) {
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

    public MutableLiveData<String> getLocation() {
        if (mLocation == null) {
            mLocation = new MutableLiveData<>();
            mLocation.setValue("北京");
        }
        return mLocation;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        this.mLocationPaths = null;
        this.mLocation = null;
    }

}
