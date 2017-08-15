package me.xiaobailong24.mvvmarms.weather.db;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;
import me.xiaobailong24.mvvmarms.weather.util.TestUtil;

/**
 * Created by xiaobailong24 on 2017/7/30.
 * Room Database Test
 * {@link me.xiaobailong24.mvvmarms.weather.mvvm.model.db.WeatherNowDao}
 */
@RunWith(AndroidJUnit4.class)
public class WeatherNowDaoTest extends DbTest {
    private static final String TAG = "WeatherNowDaoTest";

    @Test
    public void insertAndLoad() throws InterruptedException {
        //Insert
        final Location beijing = TestUtil.createLocation("WX4FBXXFKE4F");
        db.weatherNowDao().insertAll(beijing);

        //Query All
        List<Location> locations = db.weatherNowDao().getAll();
        for (Location location : locations) {
            Log.d(TAG, location.toString());
        }

        //Query by Name
        Location location = db.weatherNowDao().getLocationByName("北京");
        if (location == null) {
            Log.e(TAG, "Query: No Location named 北京");
        } else {
            Log.d(TAG, location.toString());
            //Update
            location.setCountry("中国");
            db.weatherNowDao().updateAll(location);
        }

        //Query All
        locations = db.weatherNowDao().getAll();
        for (Location l : locations) {
            Log.d(TAG, l.toString());
        }

        //Delete
        db.weatherNowDao().deleteLocation(location);

        //Query All
        locations = db.weatherNowDao().getAll();
        for (Location l : locations) {
            Log.d(TAG, l.toString());
        }

    }

}
