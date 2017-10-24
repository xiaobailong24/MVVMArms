package me.xiaobailong24.mvvmarms.lifecycle.delegate;


import android.os.Bundle;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Activity 公用接口
 */
public interface IActivity {

    /**
     * UI 初始化
     *
     * @param savedInstanceState Bundle
     * @return int
     */
    int initView(Bundle savedInstanceState);

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    void initData(Bundle savedInstanceState);

    /**
     * 是否使用EventBus
     * 默认使用(true)
     *
     * @return boolean
     */
    boolean useEventBus();

    /**
     * 这个 Activity 是否会使用 Fragment,框架会根据这个属性判断是否注册
     * {@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 默认使用(true)
     *
     * @return boolean
     */
    boolean useFragment();

    /**
     * Activity 是否依赖注入，如果不需要，则重写此方法，返回 false
     *
     * @return true: 进行依赖注入；false:不进行依赖注入
     */
    boolean injectable();
}