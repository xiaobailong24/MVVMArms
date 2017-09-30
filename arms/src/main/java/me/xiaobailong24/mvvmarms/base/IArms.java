package me.xiaobailong24.mvvmarms.base;

import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;
import me.xiaobailong24.mvvmarms.di.module.ArmsModule;

/**
 * Created by xiaobailong24 on 2017/9/30.
 * Application 继承该接口，就可以具有 ArmsComponent 提供的方法。
 */
public interface IArms {
    /**
     * Description: 获得全局 ArmsComponent
     *
     * @return ArmsComponent
     */
    ArmsComponent getArmsComponent();


    /**
     * Description: 获得全局 ArmsModule 重用
     *
     * @return ArmsModule
     */
    ArmsModule getArmsModule();
}
