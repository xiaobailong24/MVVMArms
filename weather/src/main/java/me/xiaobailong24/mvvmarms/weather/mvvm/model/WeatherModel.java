package me.xiaobailong24.mvvmarms.weather.mvvm.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;
import me.xiaobailong24.mvvmarms.mvvm.BaseModel;
import me.xiaobailong24.mvvmarms.repository.http.Resource;
import me.xiaobailong24.mvvmarms.repository.utils.RepositoryUtils;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.db.WeatherNowDb;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;
import timber.log.Timber;

/**
 * @author xiaobailong24
 * @date 2017/7/31
 * MVVM WeatherModel
 */
public class WeatherModel extends BaseModel {
    private RxErrorHandler mErrorHandler;
    private MutableLiveData<Resource<List<String>>> mLocations;

    @Inject
    public WeatherModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }


    /**
     * 从Room数据库查询所有位置信息
     *
     * @return 所有位置信息列表
     */
    public MutableLiveData<Resource<List<String>>> getAllLocations() {
        if (mLocations != null) {
            // TODO: 2017/11/16 Memory Cache
            return mLocations;
        } else {
            mLocations = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRoomDatabase(WeatherNowDb.class, WeatherNowDb.DB_NAME)
                .weatherNowDao()
                .getAll()
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriberOfFlowable<List<Location>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mLocations.postValue(Resource.loading(null));
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mLocations.postValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(List<Location> locations) {
                        List<String> paths = new ArrayList<>(locations.size());
                        if (locations.size() == 0) {
                            paths.add("北京");
                        } else {
                            for (Location l : locations) {
                                paths.add(l.getPath());
                            }
                        }
                        Timber.d("loadLocationPaths: %s", Arrays.toString(paths.toArray()));
                        mLocations.postValue(Resource.success(paths));
                    }
                });
        return mLocations;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocations = null;
    }
}
