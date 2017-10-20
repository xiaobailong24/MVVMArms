package me.xiaobailong24.mvvmarms.mvvm;

import android.app.Application;

import me.xiaobailong24.mvvmarms.repository.IRepositoryManager;
import me.xiaobailong24.mvvmarms.repository.utils.RepositoryUtils;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * MVVM BaseModel
 */
public class BaseModel implements IModel {

    protected IRepositoryManager mRepositoryManager;


    public BaseModel(Application application) {
        this.mRepositoryManager = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .repositoryManager();
    }

    @Override
    public void onDestroy() {
        this.mRepositoryManager = null;
    }
}
