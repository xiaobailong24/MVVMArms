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
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherDailyResponse;

/**
 * @author xiaobailong24
 * @date 2017/8/14
 * MVVM WeatherDailyModel
 */
public class WeatherDailyModel extends BaseModel {
    private RxErrorHandler mErrorHandler;
    private MutableLiveData<Resource<WeatherDailyResponse>> mDailyResource;

    @Inject
    public WeatherDailyModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }


    /**
     * 从网络获取未来三天天气
     *
     * @param request 请求信息
     * @return 未来三天天气结果
     */
    public MutableLiveData<Resource<WeatherDailyResponse>> getWeatherDaily(Map<String, String> request) {
        if (mDailyResource == null) {
            // TODO: 2017/11/16 Cache
            mDailyResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRetrofitService(WeatherService.class)
                .getWeatherDaily(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(weatherDailyResponse -> {
                    if (weatherDailyResponse.getResults().size() > 1) {
                        throw new RuntimeException("WeatherDailyResponse get MORE than one DailyResult");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<WeatherDailyResponse>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mDailyResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mDailyResource.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(WeatherDailyResponse response) {
                        mDailyResource.setValue(Resource.success(response));
                    }
                });
        return mDailyResource;
    }
}
