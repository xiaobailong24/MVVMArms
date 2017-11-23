package me.xiaobailong24.mvvmarms.weather.db;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;
import me.xiaobailong24.mvvmarms.weather.util.TestUtil;

/**
 * @author xiaobailong24
 * @date 2017/7/30
 * Room Database Test
 * {@link me.xiaobailong24.mvvmarms.weather.mvvm.model.db.WeatherNowDao}
 */
@RunWith(AndroidJUnit4.class)
public class WeatherNowDaoTest extends DbTest {
    private static final String TAG = "WeatherNowDaoTest";

    @Test
    public void insertAndLoad() throws InterruptedException {
        //Insert
        final Location beijing = TestUtil.createBeijing("WX4FBXXFKE4F");
        final Location shanghai = TestUtil.createShanghai("HBJGASKSJFBA");

        //Query All
        db.weatherNowDao().getAll()
                .onBackpressureBuffer()
                .doOnSubscribe(subscription ->
                        db.weatherNowDao().insertAll(beijing))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Location>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<Location> locations) {
                        //数据库更新后，自动触发
                        Log.d(TAG, "Query all --->" + locations.size()
                                + Arrays.toString(locations.toArray()));
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable.just(0)
                .observeOn(Schedulers.io())
                .doOnNext(integer -> {
                    //Update
                    Log.i(TAG, "insertAndLoad: ---> Update");
                    beijing.setCountry("中国");
                    db.weatherNowDao().updateAll(beijing);
                })
                .doOnNext(integer -> {
                    //Delete
                    Log.i(TAG, "insertAndLoad: ---> Delete");
                    db.weatherNowDao().deleteLocation(beijing);
                })
                .doOnNext(integer -> {
                    //Insert
                    Log.i(TAG, "insertAndLoad: ---> Insert");
                    db.weatherNowDao().insertAll(shanghai);
                })
                .subscribe();


        //Query by Name
        db.weatherNowDao().getLocationByName("上海")
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Location>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<Location> locations) {
                        if (locations == null) {
                            Log.e(TAG, "Query: No Location named 北京");
                        } else {
                            Log.d(TAG, "Query by name --- >" + Arrays.toString(locations.toArray()));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
