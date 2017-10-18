package me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.mvvm.BaseViewModel;
import me.xiaobailong24.mvvmarms.repository.http.Status;
import me.xiaobailong24.mvvmarms.repository.utils.RepositoryUtils;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherNowModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.Api;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherNowResponse;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.pojo.TextContent;

/**
 * Created by xiaobailong24 on 2017/7/21.
 * MVVM WeatherNowViewModel
 */
@FragmentScope
public class WeatherNowViewModel extends BaseViewModel<WeatherNowModel> {
    private MutableLiveData<List<TextContent>> mContents;
    private MutableLiveData<String> mLocationName;

    @Inject
    public WeatherNowViewModel(Application application, WeatherNowModel model) {
        super(application, model);
    }

    @SuppressWarnings("all")
    public LiveData<List<TextContent>> getWeatherNow(String locationName) {
        if (mContents == null)
            mContents = new MutableLiveData<>();

        if (mLocationName == null) {
            mLocationName = new MutableLiveData<>();
        }
        if (mLocationName.getValue() == null)
            mLocationName.setValue("");

        if (!mLocationName.getValue().equalsIgnoreCase(locationName)) {
            mLocationName.setValue(locationName);
            loadWeather(locationName);
        }
        return mContents;
    }

    private void loadWeather(String locationName) {
        Map<String, String> request = new HashMap<>();
        request.put(Api.API_KEY_KEY, Api.API_KEY);
        request.put(Api.API_KEY_TEMP_UNIT, "c");
        request.put(Api.API_KEY_LANGUAGE, "zh-Hans");
        request.put(Api.API_KEY_LOCATION, locationName);

        mModel.getWeatherNow(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    addDispose(disposable);
                    mStatus.set(Status.LOADING);
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .doOnNext(weatherNowResponse -> {
                    if (weatherNowResponse.getResults().size() > 1)
                        throw new RuntimeException("WeatherNowResponse get MORE than one NowResult");
                    //查询数据库
                    Location location =
                            mModel.getLocationByName(weatherNowResponse.getResults().get(0).getLocation().getName());
                    //存储位置信息
                    if (location == null)
                        mModel.saveLocation(weatherNowResponse.getResults().get(0).getLocation());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(weatherNowResponse -> {
                    List<TextContent> contents = new ArrayList<>();
                    WeatherNowResponse.NowResult result = weatherNowResponse.getResults().get(0);
                    contents.add(new TextContent("地点", result.getLocation().getPath()));
                    contents.add(new TextContent("天气", result.getNow().getText()));
                    contents.add(new TextContent("温度", result.getNow().getTemperature() + "º"));
                    return contents;
                })
                .subscribe(new ErrorHandleSubscriber<List<TextContent>>
                        (RepositoryUtils.INSTANCE.obtainRepositoryComponent(getApplication()).rxErrorHandler()) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        //添加订阅
                        addDispose(d);
                    }

                    @Override
                    public void onNext(@NonNull List<TextContent> textContents) {
                        mStatus.set(Status.SUCCESS);
                        mContents.setValue(textContents);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        mStatus.set(Status.ERROR);
                    }
                });
    }

    @Override
    public void retry() {
        if (mLocationName != null && mLocationName.getValue() != null)
            loadWeather(mLocationName.getValue());
    }

    @Override
    public void onCleared() {
        super.onCleared();
        this.mContents = null;
        this.mLocationName = null;
    }
}
