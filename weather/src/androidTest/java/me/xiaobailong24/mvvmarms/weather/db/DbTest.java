package me.xiaobailong24.mvvmarms.weather.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

import me.xiaobailong24.mvvmarms.weather.mvvm.model.db.WeatherNowDb;

/**
 * Created by xiaobailong24 on 2017/7/30.
 * Room Database Test
 */

abstract public class DbTest {
    protected WeatherNowDb db;

    @Before
    public void initDb() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                WeatherNowDb.class).build();
    }

    @After
    public void closeDb() {
        db.close();
    }
}
