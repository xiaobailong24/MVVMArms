package me.xiaobailong24.mvvmarms.base.delegate;


import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * Application 公用接口
 */

public interface App {
    /**
     * Description: 获得全局 ArmsComponent
     *
     * @return ArmsComponent
     */
    ArmsComponent getArmsComponent();
}
