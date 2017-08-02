package me.xiaobailong24.mvvmarms.di.module;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import me.xiaobailong24.mvvmarms.mvvm.ViewModelFactory;

/**
 * Created by xiaobailong24 on 2017/7/31.
 * Dagger ViewModelFactoryModule
 */
@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
