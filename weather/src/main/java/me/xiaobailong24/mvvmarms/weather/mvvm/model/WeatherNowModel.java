package me.xiaobailong24.mvvmarms.weather.mvvm.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import org.reactivestreams.Subscription;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;
import me.xiaobailong24.mvvmarms.mvvm.BaseModel;
import me.xiaobailong24.mvvmarms.repository.http.Resource;
import me.xiaobailong24.mvvmarms.repository.utils.RepositoryUtils;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.service.WeatherService;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.db.WeatherNowDb;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherNowResponse;

/**
 * @author xiaobailong24
 * @date 2017/7/22
 * MVVM WeatherNowModel
 */
public class WeatherNowModel extends BaseModel {
    private RxErrorHandler mErrorHandler;
    private MutableLiveData<Resource<WeatherNowResponse>> mNowResource;

    @Inject
    public WeatherNowModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }


    /**
     * 从网络获取当前天气
     *
     * @param request 请求信息
     * @return 当前天气
     */
    public MutableLiveData<Resource<WeatherNowResponse>> getWeatherNow(Map<String, String> request) {
        if (mNowResource == null) {
            // TODO: 2017/11/16 Cache
            mNowResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRetrofitService(WeatherService.class)
                .getWeatherNow(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(weatherNowResponse -> {
                    if (weatherNowResponse.getResults().size() > 1) {
                        throw new RuntimeException("WeatherNowResponse get MORE than one NowResult");
                    }
                    saveLocation(weatherNowResponse.getResults().get(0).getLocation());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<WeatherNowResponse>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mNowResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mNowResource.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(WeatherNowResponse response) {
                        mNowResource.setValue(Resource.success(response));
                    }
                });
        return mNowResource;
    }


    /**
     * 存储位置信息到 Room 数据库
     *
     * @param location 位置信息
     */
    public void saveLocation(Location location) {
        mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.DB_NAME)
                .weatherNowDao()
                .insertAll(location);
    }


    /**
     * 更新 Room 数据库位置信息
     *
     * @param location 要更新的位置信息
     */
    public void updateLocation(Location location) {
        mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.DB_NAME)
                .weatherNowDao()
                .updateAll(location);
    }


    /**
     * 删除Room数据库位置信息
     *
     * @param location 要删除的位置信息
     */
    public void deleteLocation(Location location) {
        mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.DB_NAME)
                .weatherNowDao()
                .deleteLocation(location);
    }
}
