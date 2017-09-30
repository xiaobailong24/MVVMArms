package me.xiaobailong24.mvvmarms.repository.di.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.repository.IRepositoryManager;
import me.xiaobailong24.mvvmarms.repository.RepositoryManager;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * Dagger RepositoryModule
 */
@Module
public class RepositoryModule {
    private Application mApplication;

    public RepositoryModule(Application application) {
        this.mApplication = application;
    }

//    @Singleton
    @Provides
    Application provideApplication() {
        return this.mApplication;
    }

//    @Singleton
    @Provides
    IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
        return repositoryManager;
    }
}
