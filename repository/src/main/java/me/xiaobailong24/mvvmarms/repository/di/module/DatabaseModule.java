package me.xiaobailong24.mvvmarms.repository.di.module;

import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import dagger.Module;

/**
 * @author xiaobailong24
 * @date 2017/9/22
 * Dagger RoomDatabase Module
 */
@Module
public class DatabaseModule {

    public interface RoomConfiguration<DB extends RoomDatabase> {
        /**
         * 提供接口，自定义配置 RoomDatabase
         *
         * @param context Context
         * @param builder RoomDatabase.Builder
         */
        void configRoom(Context context, RoomDatabase.Builder<DB> builder);

        RoomConfiguration EMPTY = new RoomConfiguration() {
            @Override
            public void configRoom(Context context, RoomDatabase.Builder builder) {

            }
        };
    }
}
