package me.xiaobailong24.mvvmarms.weather.mvvm.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.xiaobailong24.mvvmarms.di.scope.AppScope;
import me.xiaobailong24.mvvmarms.mvvm.BaseViewModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherDailyModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.Api;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherDailyResponse;

/**
 * Created by xiaobailong24 on 2017/8/14.
 * MVVM WeatherDailyViewModel
 */
@AppScope
public class WeatherDailyViewModel extends BaseViewModel<WeatherDailyModel>
        implements IRetry {
    private RxErrorHandler mRxErrorHandler;
    private MutableLiveData<List<WeatherDailyResponse.DailyResult.Daily>> mDailyData;
    private MutableLiveData<String> mLocationName;

    //数据请求状态
    public final ObservableField<Api.Status> mStatus = new ObservableField<>();

    @Inject
    public WeatherDailyViewModel(Application application, WeatherDailyModel weatherDailyModel,
                                 RxErrorHandler rxErrorHandler) {
        super(application, weatherDailyModel);
        this.mRxErrorHandler = rxErrorHandler;
    }


    public LiveData<List<WeatherDailyResponse.DailyResult.Daily>> getWeatherDaily(String locationName) {
        if (mDailyData == null)
            mDailyData = new MutableLiveData<>();

        if (mLocationName == null) {
            mLocationName = new MutableLiveData<>();
        }
        if (mLocationName.getValue() == null)
            mLocationName.setValue("");

        if (!mLocationName.getValue().toLowerCase().equals(locationName)) {
            mLocationName.setValue(locationName);
            loadWeatherDaily(locationName);
        }
        return mDailyData;
    }

    private void loadWeatherDaily(String locationName) {
        Map<String, String> request = new HashMap<>();
        request.put(Api.API_KEY_KEY, Api.API_KEY);
        request.put(Api.API_KEY_TEMP_UNIT, "c");
        request.put(Api.API_KEY_LANGUAGE, "zh-Hans");
        request.put(Api.API_KEY_LOCATION, locationName);

        mModel.getWeatherDaily(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    addDispose(disposable);
                    mStatus.set(Api.Status.LOADING);
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .doOnNext(weatherDailyResponse -> {
                    if (weatherDailyResponse.getResults().size() > 1)
                        throw new RuntimeException("WeatherDailyResponse get MORE than one DailyResult");
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(weatherDailyResponse -> weatherDailyResponse.getResults().get(0).getDaily())
                .subscribe(new ErrorHandleSubscriber<List<WeatherDailyResponse.DailyResult.Daily>>(mRxErrorHandler) {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        //添加订阅
                        addDispose(d);
                    }

                    @Override
                    public void onNext(@NonNull List<WeatherDailyResponse.DailyResult.Daily> dailies) {
                        mStatus.set(Api.Status.SUCCESS);
                        mDailyData.setValue(dailies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        mStatus.set(Api.Status.ERROR);
                    }
                });
    }

    @Override
    public void retry() {
        if (mLocationName != null && mLocationName.getValue() != null)
            loadWeatherDaily(mLocationName.getValue());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.mDailyData = null;
        this.mLocationName = null;
    }
}
