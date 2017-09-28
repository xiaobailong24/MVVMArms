package me.xiaobailong24.mvvmarms.repository;

import android.app.Application;
import android.content.Context;

import java.util.List;

import me.xiaobailong24.mvvmarms.repository.di.component.DaggerRepositoryComponent;
import me.xiaobailong24.mvvmarms.repository.di.component.RepositoryComponent;
import me.xiaobailong24.mvvmarms.repository.di.module.ClientModule;
import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryConfigModule;
import me.xiaobailong24.mvvmarms.repository.di.module.RepositoryModule;
import me.xiaobailong24.mvvmarms.repository.utils.ManifestParser;
import me.xiaobailong24.mvvmarms.repository.utils.Preconditions;

/**
 * Created by xiaobailong24 on 2017/9/28.
 * RepositoryInjector，需要在 Application 初始化，注入 RepositoryComponent
 */
public class RepositoryInjector implements IRepository {
    private Application mApplication;
    private List<ConfigRepository> mConfigRepositories;
    private RepositoryComponent mRepositoryComponent;
    private RepositoryModule mRepositoryModule;

    public RepositoryInjector(Context context) {
        this.mConfigRepositories = new ManifestParser(context).parse();
    }

    public void initialize(Application application) {
        this.mApplication = application;
        mRepositoryModule = new RepositoryModule(application);
        mRepositoryComponent =
                DaggerRepositoryComponent.builder()
                        .repositoryModule(mRepositoryModule)
                        .clientModule(new ClientModule())
                        .repositoryConfigModule(getRepositoryConfigModule(application, mConfigRepositories))
                        .build();
        mRepositoryComponent.inject(this);

        // 注册数据管理层
        for (ConfigRepository repository : mConfigRepositories) {
            repository.registerComponents(mApplication, mRepositoryComponent.repositoryManager());
        }
    }

    private RepositoryConfigModule getRepositoryConfigModule(Context context, List<ConfigRepository> configRepositories) {

        RepositoryConfigModule.Builder builder = RepositoryConfigModule.builder();

        for (ConfigRepository repository : configRepositories) {
            repository.applyOptions(context, builder);
        }

        return builder.build();
    }

    public RepositoryComponent getRepositoryComponent() {
        Preconditions.checkNotNull(mRepositoryComponent,
                "%s cannot be null,first call %s#initialize(Application) in %s#onCreate()",
                RepositoryComponent.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mRepositoryComponent;
    }

    public RepositoryModule getRepositoryModule() {
        Preconditions.checkNotNull(mRepositoryComponent,
                "%s cannot be null,first call %s#initialize(Application) in %s#onCreate()",
                RepositoryModule.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return mRepositoryModule;
    }
}
