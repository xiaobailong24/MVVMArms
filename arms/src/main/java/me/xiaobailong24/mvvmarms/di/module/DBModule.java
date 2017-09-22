package me.xiaobailong24.mvvmarms.di.module;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import dagger.Module;

/**
 * Created by xiaobailong24 on 2017/9/22.
 * Dagger RoomDatabase Module
 */
@Module
public class DBModule {

    public interface RoomConfiguration<DB extends RoomDatabase> {
        void configRoom(Context context, RoomDatabase.Builder<DB> builder);

        RoomConfiguration EMPTY = new RoomConfiguration() {
            @Override
            public void configRoom(Context context, RoomDatabase.Builder builder) {

            }
        };
    }
}
