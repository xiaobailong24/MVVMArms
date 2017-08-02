package me.xiaobailong24.mvvmarms.base.delegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by xiaobailong24 on 2017/6/16.
 * Fragment 公用接口
 */

public interface IFragment {

    /**
     * Description: 是否使用EventBus.
     * 默认使用(true){@link me.xiaobailong24.mvvmarms.base.ArmsFragment}
     *
     * @return boolean
     */
    boolean useEventBus();

    /**
     * Description: UI 初始化
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * Description: 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    void initData(Bundle savedInstanceState);

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     *
     * @param data Object
     */
    void setData(Object data);

    /**
     * Description: Fragment 是否依赖注入
     * @return true: 进行依赖注入；false:不进行依赖注入
     */
    boolean injectable();
}
