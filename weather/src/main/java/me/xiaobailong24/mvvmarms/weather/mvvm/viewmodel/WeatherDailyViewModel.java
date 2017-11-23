package me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.xiaobailong24.mvvmarms.di.scope.FragmentScope;
import me.xiaobailong24.mvvmarms.mvvm.BaseViewModel;
import me.xiaobailong24.mvvmarms.repository.http.IRetry;
import me.xiaobailong24.mvvmarms.repository.http.Resource;
import me.xiaobailong24.mvvmarms.repository.http.Status;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.WeatherDailyModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.Api;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherDailyResponse;
import timber.log.Timber;

/**
 * @author xiaobailong24
 * @date 2017/8/14
 * MVVM WeatherDailyViewModel
 */
@FragmentScope
public class WeatherDailyViewModel extends BaseViewModel<WeatherDailyModel>
        implements IRetry {
    private MediatorLiveData<List<WeatherDailyResponse.DailyResult.Daily>> mDailies
            = new MediatorLiveData<>();
    private MutableLiveData<Resource<WeatherDailyResponse>> mDailyResponse;
    private String mLocationName = "";

    @Inject
    public WeatherDailyViewModel(Application application, WeatherDailyModel weatherDailyModel) {
        super(application, weatherDailyModel);
    }

    public void loadWeatherDaily(String locationName) {
        mLocationName = locationName;
        Map<String, String> request = new HashMap<>(4);
        request.put(Api.API_KEY_KEY, Api.API_KEY);
        request.put(Api.API_KEY_TEMP_UNIT, "c");
        request.put(Api.API_KEY_LANGUAGE, "zh-Hans");
        request.put(Api.API_KEY_LOCATION, locationName);

        if (mDailyResponse != null) {
            mDailies.removeSource(mDailyResponse);
        }
        mDailyResponse = mModel.getWeatherDaily(request);
        mDailies.addSource(mDailyResponse, observer -> {
            mDailies.removeSource(mDailyResponse);
            mDailies.addSource(mDailyResponse, newResource -> {
                if (newResource == null) {
                    newResource = Resource.error("", null);
                }
                Timber.d("Load weather daily: %s", newResource.status);
                if (newResource.status == Status.LOADING) {
                    STATUS.set(Status.LOADING);
                } else if (newResource.status == Status.SUCCESS) {
                    mDailies.postValue(newResource.data.getResults().get(0).getDaily());
                    STATUS.set(Status.SUCCESS);
                } else if (newResource.status == Status.ERROR) {
                    STATUS.set(Status.ERROR);
                }
            });
        });
    }

    public LiveData<List<WeatherDailyResponse.DailyResult.Daily>> getWeatherDaily() {
        return mDailies;
    }

    @Override
    public void retry() {
        if (mLocationName != null) {
            loadWeatherDaily(mLocationName);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.mDailyResponse = null;
        this.mLocationName = null;
    }
}
