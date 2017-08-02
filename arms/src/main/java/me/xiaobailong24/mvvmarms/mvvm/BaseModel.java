package me.xiaobailong24.mvvmarms.mvvm;

import me.xiaobailong24.mvvmarms.repository.IRepositoryManager;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * MVVM BaseModel
 */
public class BaseModel implements IModel {

    protected IRepositoryManager mRepositoryManager;


    public BaseModel(IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }

    @Override
    public void onDestroy() {
        this.mRepositoryManager = null;
    }
}
